package it.chalmers.gamma.adapter.primary.api;

import it.chalmers.gamma.app.domain.Group;
import it.chalmers.gamma.app.group.GroupService;
import it.chalmers.gamma.app.domain.Membership;
import it.chalmers.gamma.app.group.MembershipService;
import it.chalmers.gamma.app.domain.GroupWithMembers;
import it.chalmers.gamma.app.domain.UserPost;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Used by cthit/goldapps to sync Google accounts for the student division
 */
@RestController
@RequestMapping(GoldappsApiController.URI)
public class GoldappsApiController {

    public static final String URI = "/goldapps";

    private final MembershipService membershipService;
    private final GroupService groupService;

    public GoldappsApiController(MembershipService membershipService,
                                 GroupService groupService) {
        this.membershipService = membershipService;
        this.groupService = groupService;
    }

    private record Goldapps(List<GroupWithMembers> allGroups) { }

    @GetMapping
    public Goldapps get() {
        return new Goldapps(
                this.groupService.getAll()
                        .stream()
                        .map(this::toGroupWithMembers)
                        .collect(Collectors.toList())
        );
    }

    private GroupWithMembers toGroupWithMembers(Group group) {
        return new GroupWithMembers(
                group,
                toUserPosts(this.membershipService.getMembershipsByGroup(group.id()))
        );
    }

    private List<UserPost> toUserPosts(List<Membership> memberships) {
        return memberships
                .stream()
                .map(membership -> new UserPost(membership.user(), membership.post()))
                .collect(Collectors.toList());
    }

}
