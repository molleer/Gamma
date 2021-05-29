package it.chalmers.gamma.internal.group.service;

import it.chalmers.gamma.domain.Email;
import it.chalmers.gamma.domain.GroupId;
import it.chalmers.gamma.domain.EntityName;
import it.chalmers.gamma.domain.PrettyName;
import it.chalmers.gamma.internal.supergroup.service.SuperGroupDTO;
import it.chalmers.gamma.util.domain.abstraction.DTO;

public record GroupDTO(GroupId id,
                       Email email,
                       EntityName name,
                       PrettyName prettyName,
                       SuperGroupDTO superGroup)
        implements DTO { }
