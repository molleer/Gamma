package it.chalmers.gamma.oauth;

import it.chalmers.gamma.domain.client.ClientId;
import it.chalmers.gamma.domain.client.data.ClientDTO;
import it.chalmers.gamma.domain.client.data.ClientDetailsDTO;
import it.chalmers.gamma.domain.client.exception.ClientNotFoundException;
import it.chalmers.gamma.domain.client.service.ClientFinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(types = AuthorizationRequest.class)
public class OAuth2AuthorizeController {

    private final ClientFinder clientFinder;

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthorizeController.class);

    public OAuth2AuthorizeController(ClientFinder clientFinder) {
        this.clientFinder = clientFinder;
    }

    @GetMapping("/oauth/confirm_access")
    public String getConfirmAccess(@ModelAttribute AuthorizationRequest clientAuth, Model model) {
        try {
            ClientDTO client = this.clientFinder.getClient(new ClientId(clientAuth.getClientId()));
            model.addAttribute("clientName", client.getName());
        } catch (ClientNotFoundException e) {
            LOGGER.error("Cannot find provided client in authorize", e);
        }

        return "authorize";
    }

}

