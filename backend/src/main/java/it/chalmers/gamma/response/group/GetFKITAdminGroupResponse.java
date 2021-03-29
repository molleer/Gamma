package it.chalmers.gamma.response.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import it.chalmers.gamma.domain.dto.group.FKITGroupDTO;
import it.chalmers.gamma.domain.dto.membership.MembershipDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetFKITAdminGroupResponse {

    @JsonUnwrapped
    private final FKITGroupDTO group;
    private final List<MembershipDTO> groupMembers;

    public GetFKITAdminGroupResponse(FKITGroupDTO group, List<MembershipDTO> groupMembers) {
        this.group = group;
        this.groupMembers = groupMembers;
    }

    public FKITGroupDTO getGroup() {
        return this.group;
    }

    public List<MembershipDTO> getGroupMembers() {
        return this.groupMembers;
    }

    public GetFKITGroupResponseObject toResponseObject() {
        return new GetFKITGroupResponseObject(this);
    }

    public static class GetFKITGroupResponseObject extends ResponseEntity<GetFKITAdminGroupResponse> {
        public GetFKITGroupResponseObject(GetFKITAdminGroupResponse body) {
            super(body, HttpStatus.OK);
        }
    }
}
