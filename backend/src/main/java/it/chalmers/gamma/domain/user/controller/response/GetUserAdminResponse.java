package it.chalmers.gamma.domain.user.controller.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import it.chalmers.gamma.domain.GroupPost;
import it.chalmers.gamma.domain.user.data.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GetUserAdminResponse {

    @JsonUnwrapped
    public final UserDTO user;
    public final List<GroupPost> groups;

    public GetUserAdminResponse(UserDTO user, List<GroupPost> groups) {
        this.user = user;
        this.groups = groups;
    }
}
