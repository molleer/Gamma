package it.chalmers.gamma.response.group;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

public class GetAllAdminGroupsResponse {

    @JsonUnwrapped
    private final List<GetFKITAdminGroupResponse> groups;

    public GetAllAdminGroupsResponse(List<GetFKITAdminGroupResponse> groups) {
        this.groups = groups;
    }

    public List<GetFKITAdminGroupResponse> getGroups() {
        return this.groups;
    }

    public GetAllFKITAdminGroupsResponseObject toResponseObject() {
        return new GetAllFKITAdminGroupsResponseObject(this);
    }

    public static class GetAllFKITAdminGroupsResponseObject extends ResponseEntity<GetAllAdminGroupsResponse> {
        GetAllFKITAdminGroupsResponseObject(GetAllAdminGroupsResponse body) {
            super(body, HttpStatus.OK);
        }
    }
}
