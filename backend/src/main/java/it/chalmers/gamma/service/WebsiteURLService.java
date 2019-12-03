package it.chalmers.gamma.service;

import it.chalmers.gamma.db.entity.Website;
import it.chalmers.gamma.db.entity.WebsiteURL;
import it.chalmers.gamma.db.repository.WebsiteURLRepository;

import it.chalmers.gamma.domain.dto.website.WebsiteDTO;
import it.chalmers.gamma.domain.dto.website.WebsiteURLDTO;
import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

//TODO this class might be unnecessary because of changes in how websites work
@SuppressWarnings("PMD.UnusedPrivateMethod")
@Service
public class WebsiteURLService {

    private final WebsiteURLRepository repository;
    private final WebsiteService websiteService;

    public WebsiteURLService(WebsiteURLRepository repository, WebsiteService websiteService) {
        this.repository = repository;
        this.websiteService = websiteService;
    }

    private void addWebsite(WebsiteDTO websiteDTO, String url) {
        WebsiteURL websiteURL = new WebsiteURL();
        websiteURL.setWebsite(this.websiteService.getWebsite(websiteDTO));
        websiteURL.setUrl(url);
        this.repository.save(websiteURL);
    }

    public List<WebsiteURLDTO> getAllWebsites() {
        return this.repository.findAll().stream().map(WebsiteURL::toDTO).collect(Collectors.toList());
    }

    public WebsiteURLDTO getWebsiteURLById(String id) {
        return this.repository.findById(UUID.fromString(id)).map(WebsiteURL::toDTO).orElse(null);
    }

    public void deleteWebsite(String id) {
        this.repository.deleteById(UUID.fromString(id));
    }

    public void editWebsite(WebsiteURLDTO websiteURLDTO, WebsiteDTO websiteDTO, String url) {
        WebsiteURL websiteURL = this.getWebsiteURL(websiteURLDTO);
        websiteURL.setWebsite(this.websiteService.getWebsite(websiteDTO));
        websiteURL.setUrl(url);
        this.repository.save(websiteURL);
    }

    @Transactional
    public void deleteAllWebsites(WebsiteDTO website) {
        this.repository.deleteAllByWebsite(this.websiteService.getWebsite(website));
    }

    protected WebsiteURL getWebsiteURL(WebsiteURLDTO websiteURLDTO) {
        return this.repository.findById(websiteURLDTO.getId()).orElse(null);
    }

}
