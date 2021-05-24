package it.chalmers.gamma.internal.supergroup.service;

import it.chalmers.gamma.domain.Name;
import it.chalmers.gamma.domain.SuperGroupId;
import it.chalmers.gamma.util.domain.abstraction.EntityExists;
import it.chalmers.gamma.util.domain.abstraction.exception.EntityNotFoundException;
import it.chalmers.gamma.util.domain.abstraction.GetAllEntities;
import it.chalmers.gamma.util.domain.abstraction.GetEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SuperGroupFinder implements GetEntity<SuperGroupId, SuperGroupDTO>, GetAllEntities<SuperGroupDTO>, EntityExists<SuperGroupId> {

    private final SuperGroupRepository superGroupRepository;

    public SuperGroupFinder(SuperGroupRepository superGroupRepository) {
        this.superGroupRepository = superGroupRepository;
    }

    public boolean exists(SuperGroupId id) {
        return this.superGroupRepository.existsById(id);
    }

    public SuperGroupDTO getByName(Name name) throws EntityNotFoundException {
        return getEntityByName(name).toDTO();
    }

    public SuperGroupDTO get(SuperGroupId id) throws EntityNotFoundException {
        return getEntity(id).toDTO();
    }

    protected SuperGroupEntity getEntity(SuperGroupId id) throws EntityNotFoundException {
        return this.superGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    protected SuperGroupEntity getEntity(SuperGroupDTO superGroup) throws EntityNotFoundException {
        return getEntity(superGroup.id());
    }

    protected SuperGroupEntity getEntityByName(Name name) throws EntityNotFoundException {
        return this.superGroupRepository.findByName(name)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<SuperGroupDTO> getAll() {
        return Optional.of(this.superGroupRepository.findAll().stream()
                .map(SuperGroupEntity::toDTO)
                .collect(Collectors.toList())).orElseThrow();
    }

}
