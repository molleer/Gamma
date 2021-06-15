package it.chalmers.gamma.internal.apikey.controller;

import it.chalmers.gamma.domain.EntityName;
import it.chalmers.gamma.domain.ApiKeyType;
import it.chalmers.gamma.domain.PrettyName;
import it.chalmers.gamma.domain.Text;
import it.chalmers.gamma.domain.ApiKeyId;
import it.chalmers.gamma.domain.ApiKeyToken;
import it.chalmers.gamma.domain.ApiKey;
import it.chalmers.gamma.internal.apikey.service.ApiKeyService;

import it.chalmers.gamma.util.response.ErrorResponse;
import it.chalmers.gamma.util.response.NotFoundResponse;
import it.chalmers.gamma.util.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/internal/admin/api_keys")
public class ApiKeyAdminController {

    private final ApiKeyService apiKeyService;

    public ApiKeyAdminController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    private record CreateApiKeyRequest(PrettyName prettyName, Text description, ApiKeyType keyType) { }

    @PostMapping()
    public ApiKeyToken createApiKey(@RequestBody CreateApiKeyRequest request) {
        ApiKeyToken key = ApiKeyToken.generate();

        this.apiKeyService.create(
            new ApiKey(
                    ApiKeyId.generate(),
                    request.prettyName,
                    request.description,
                    request.keyType
            ),
                key
        );

        return key;
    }

    @GetMapping()
    public List<ApiKey> getAllApiKeys() {
        return this.apiKeyService.getAll();
    }

    @DeleteMapping("/{id}")
    public ApiKeyDeletedResponse deleteApiKey(@PathVariable("id") ApiKeyId apiKeyId) {
        try {
            this.apiKeyService.delete(apiKeyId);
            return new ApiKeyDeletedResponse();
        } catch (ApiKeyService.ApiKeyNotFoundException e) {
            throw new ApiKeyNotFoundResponse();
        }
    }

    private static class ApiKeyDeletedResponse extends SuccessResponse { }

    private static class ApiKeyNotFoundResponse extends NotFoundResponse { }

}
