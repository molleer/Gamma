package it.chalmers.gamma.internal.client.service;

import it.chalmers.gamma.domain.Client;
import it.chalmers.gamma.domain.ClientId;
import it.chalmers.gamma.domain.ClientSecret;
import it.chalmers.gamma.domain.ApiKey;
import it.chalmers.gamma.domain.ApiKeyId;
import it.chalmers.gamma.internal.apikey.service.ApiKeyService;
import it.chalmers.gamma.domain.ApiKeyToken;
import it.chalmers.gamma.domain.ApiKeyType;
import it.chalmers.gamma.domain.ClientApiKey;
import it.chalmers.gamma.internal.client.apikey.service.ClientApiKeyService;
import it.chalmers.gamma.internal.client.controller.ClientAdminController;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements ClientDetailsService {

    private final ClientRepository clientRepository;
    private final ApiKeyService apiKeyService;
    private final ClientApiKeyService clientApiKeyService;

    public ClientService(ClientRepository clientRepository,
                         ApiKeyService apiKeyService,
                         ClientApiKeyService clientApiKeyService) {
        this.clientRepository = clientRepository;
        this.apiKeyService = apiKeyService;
        this.clientApiKeyService = clientApiKeyService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return this.clientRepository.findById(new ClientId(clientId))
                .map(clientEntity -> new ClientDetailsImpl(clientEntity.toDTO(), clientEntity.getClientSecret()))
                .orElseThrow(ClientAdminController.ClientNotFoundResponse::new);
    }

    public void create(Client newClient, ClientSecret clientSecret) {
        this.clientRepository.save(new ClientEntity(newClient, clientSecret));
    }

    @Transactional
    public ApiKeyToken createWithApiKey(Client newClient, ClientSecret clientSecret, ApiKeyToken apiKeyToken) {
        this.create(newClient, clientSecret);

        ApiKeyId apiKeyId = new ApiKeyId();

        this.apiKeyService.create(
                new ApiKey(
                        apiKeyId,
                        newClient.name(),
                        newClient.description(),
                        ApiKeyType.CLIENT
                ),
                apiKeyToken
        );

        this.clientApiKeyService.create(
                new ClientApiKey(
                        newClient.clientId(),
                        apiKeyId
                )
        );

        return apiKeyToken;
    }

    public void delete(ClientId clientId) throws ClientNotFoundException {
        this.clientRepository.deleteById(clientId);
    }

    public List<Client> getAll() {
        return this.clientRepository.findAll().stream().map(ClientEntity::toDTO).collect(Collectors.toList());
    }

    public Client get(ClientId clientId) throws ClientNotFoundException {
        return this.clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new)
                .toDTO();
    }

    public static class ClientNotFoundException extends Exception { }


}
