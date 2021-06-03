package it.chalmers.gamma.bootstrap;

import it.chalmers.gamma.domain.ApiKeyToken;
import it.chalmers.gamma.domain.EntityName;
import it.chalmers.gamma.domain.ClientId;
import it.chalmers.gamma.domain.ClientSecret;
import it.chalmers.gamma.internal.client.service.ClientDTO;

import it.chalmers.gamma.internal.client.service.ClientService;
import it.chalmers.gamma.internal.text.service.TextDTO;
import it.chalmers.gamma.internal.user.service.UserRestrictedDTO;
import it.chalmers.gamma.internal.user.approval.service.UserApprovalDTO;
import it.chalmers.gamma.internal.user.approval.service.UserApprovalService;
import it.chalmers.gamma.internal.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@DependsOn("mockBootstrap")
@Component
public class ClientBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientBootstrap.class);

    private final String redirectUri;
    private final ClientService clientService;
    private final UserApprovalService userApprovalService;
    private final UserService userService;
    private final boolean mocking;

    public ClientBootstrap(ClientService clientService,
                           UserApprovalService userApprovalService,
                           UserService userService,
                           @Value("${application.mocking}") boolean mocking,
                           @Value("${application.default-oauth2-client.redirect-uri}") String redirectUri) {
        this.redirectUri = redirectUri;
        this.clientService = clientService;
        this.userApprovalService = userApprovalService;
        this.userService = userService;
        this.mocking = mocking;
    }

    @PostConstruct
    void runOauthClient() {
        if(!this.mocking || !this.clientService.getAll().isEmpty()) {
            return;
        }
        LOGGER.info("========== CLIENT BOOTSTRAP ==========");
        LOGGER.info("Creating test client...");


        ClientId clientId = ClientId.valueOf("test");
        ClientSecret clientSecret = ClientSecret.valueOf("secret");
        ApiKeyToken apiKeyToken = ApiKeyToken.valueOf("test-api-key-secret-token");

        this.clientService.createWithApiKey(
                new ClientDTO(
                        clientId,
                        clientSecret,
                        redirectUri,
                        true,
                        EntityName.valueOf("test-client"),
                        new TextDTO("", "")
                ),
                apiKeyToken
        );

        for (UserRestrictedDTO user : this.userService.getAll()) {
            this.userApprovalService.create(
                    new UserApprovalDTO(
                            user.id(),
                            clientId
                    )
            );
        }

        LOGGER.info("Client generated with information:");
        LOGGER.info("ClientId: " + clientId.get());
        LOGGER.info("ClientSecret: " + clientSecret.get());
        LOGGER.info("Client redirect uri: " + this.redirectUri);
        LOGGER.info("An API key was also generated with the client, it has the token: " + apiKeyToken.get());
        LOGGER.info("========== CLIENT BOOTSTRAP ==========");
    }
}
