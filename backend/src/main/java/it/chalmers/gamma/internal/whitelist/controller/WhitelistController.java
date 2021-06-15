package it.chalmers.gamma.internal.whitelist.controller;

import it.chalmers.gamma.domain.ActivationCode;
import it.chalmers.gamma.domain.Cid;
import it.chalmers.gamma.internal.activationcode.service.ActivationCodeService;
import it.chalmers.gamma.internal.whitelist.service.WhitelistService;
import it.chalmers.gamma.mail.MailSenderService;

import javax.validation.Valid;

import it.chalmers.gamma.util.response.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/internal/whitelist")
public final class WhitelistController {

    private final ActivationCodeService activationCodeService;
    private final MailSenderService mailSenderService;
    private final WhitelistService whitelistService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WhitelistController.class);

    private static final String MAIL_POSTFIX = "student.chalmers.se";

    public WhitelistController(ActivationCodeService activationCodeService,
                                MailSenderService mailSenderService,
                                WhitelistService whitelistService) {
        this.activationCodeService = activationCodeService;
        this.mailSenderService = mailSenderService;
        this.whitelistService = whitelistService;
    }

    private record WhitelistCodeRequest(Cid cid) { }

    @PostMapping("/activate_cid")
    public WhitelistedCidActivatedResponse createActivationCode(@Valid @RequestBody WhitelistCodeRequest request) {
        Cid cid = request.cid;

        if (this.whitelistService.cidIsWhitelisted(cid)) {
            ActivationCode activationCode = this.activationCodeService.saveActivationCode(cid);
            sendEmail(activationCode);
        } else {
            LOGGER.warn(String.format("Non Whitelisted User: %s Tried to Create Account", cid));
        }

        //Gamma doesn't differentiate if activation of a cid was successful or not.
        return new WhitelistedCidActivatedResponse();
    }

    private void sendEmail(ActivationCode activationCode) {
        String code = activationCode.token().get();
        String to = activationCode.cid() + "@" + MAIL_POSTFIX;
        String message = "Your code to Gamma is: " + code;
        this.mailSenderService.trySendingMail(to, "Chalmers activationcode code", message);
    }

    // This will be thrown even if there was an error for security reasons.
    private static class WhitelistedCidActivatedResponse extends SuccessResponse { }

}

