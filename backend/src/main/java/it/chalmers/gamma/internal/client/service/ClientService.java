package it.chalmers.gamma.internal.client.service;

import it.chalmers.gamma.domain.ClientId;
import it.chalmers.gamma.domain.Name;
import it.chalmers.gamma.internal.apikey.service.ApiKeyDTO;
import it.chalmers.gamma.domain.ApiKeyId;
import it.chalmers.gamma.internal.apikey.service.ApiKeyService;
import it.chalmers.gamma.domain.ApiKeyToken;
import it.chalmers.gamma.domain.ApiKeyType;
import it.chalmers.gamma.internal.client.apikey.service.ClientApiKeyDTO;
import it.chalmers.gamma.internal.client.apikey.service.ClientApiKeyService;
import it.chalmers.gamma.internal.client.controller.ClientAdminController;
import it.chalmers.gamma.util.domain.abstraction.CreateEntity;
import it.chalmers.gamma.util.domain.abstraction.DeleteEntity;
import it.chalmers.gamma.util.domain.abstraction.exception.EntityNotFoundException;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClientService implements ClientDetailsService, CreateEntity<ClientDTO>, DeleteEntity<ClientId> {


    private final ClientRepository clientRepository;
    private final ClientFinder clientFinder;
    private final ApiKeyService apiKeyService;
    private final ClientApiKeyService clientApiKeyService;

    public ClientService(ClientRepository clientRepository,
                         ClientFinder clientFinder,
                         ApiKeyService apiKeyService,
                         ClientApiKeyService clientApiKeyService) {
        this.clientRepository = clientRepository;
        this.clientFinder = clientFinder;
        this.apiKeyService = apiKeyService;
        this.clientApiKeyService = clientApiKeyService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return this.clientRepository.findById(new ClientId(clientId))
                .map(ClientEntity::toDTO)
                .map(ClientDetailsImpl::new)
                .orElseThrow(ClientAdminController.ClientNotFoundResponse::new);
    }

    public void create(ClientDTO newClient) {
        this.clientRepository.save(new ClientEntity(newClient));
    }

    @Transactional
    public ApiKeyToken createWithApiKey(ClientDTO newClient, ApiKeyToken apiKeyToken) {
        this.create(newClient);

        ApiKeyId apiKeyId = new ApiKeyId();

        this.apiKeyService.create(
                new ApiKeyDTO(
                        apiKeyId,
                        newClient.name(),
                        newClient.description(),
                        apiKeyToken,
                        ApiKeyType.CLIENT
                )
        );

        this.clientApiKeyService.create(
                new ClientApiKeyDTO(
                        newClient.clientId(),
                        apiKeyId
                )
        );

        return apiKeyToken;
    }

    public void delete(ClientId clientId) throws EntityNotFoundException {
        if(!this.clientFinder.clientExists(clientId)) {
            throw new EntityNotFoundException();
        }

        this.clientRepository.deleteById(clientId);
    }

}
