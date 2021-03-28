package it.chalmers.gamma.domain.dto.user;

import java.time.Year;
import java.util.Objects;
import java.util.UUID;

public class ITUserRestrictedDTO {
    private final UUID id;
    private final String cid;
    private final String nick;
    private final String firstName;
    private final String lastName;
    private final String avatarUrl;
    private final Year acceptanceYear;
    private final boolean gdpr;

    public ITUserRestrictedDTO(ITUserDTO userDTO) {
        this.id = userDTO.getId();
        this.cid = userDTO.getCid();
        this.nick = userDTO.getNick();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.avatarUrl = userDTO.getAvatarUrl();
        this.acceptanceYear = userDTO.getAcceptanceYear();
        this.gdpr = userDTO.isGdpr();
    }

    public UUID getId() {
        return this.id;
    }

    public String getCid() {
        return this.cid;
    }

    public String getNick() {
        return this.nick;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public Year getAcceptanceYear() {
        return this.acceptanceYear;
    }

    public boolean isGdpr() {
        return this.gdpr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ITUserRestrictedDTO that = (ITUserRestrictedDTO) o;
        return this.gdpr == that.gdpr
                && Objects.equals(this.id, that.id)
                && Objects.equals(this.cid, that.cid)
                && Objects.equals(this.nick, that.nick)
                && Objects.equals(this.firstName, that.firstName)
                && Objects.equals(this.lastName, that.lastName)
                && Objects.equals(this.avatarUrl, that.avatarUrl)
                && Objects.equals(this.acceptanceYear, that.acceptanceYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id,
                this.cid,
                this.nick,
                this.firstName,
                this.lastName,
                this.avatarUrl,
                this.acceptanceYear,
                this.gdpr
        );
    }

    @Override
    public String toString() {
        return "ITUserRestrictedDTO{"
                + "id=" + this.id
                + ", cid='" + this.cid + '\''
                + ", nick='" + this.nick + '\''
                + ", firstName='" + this.firstName + '\''
                + ", lastName='" + this.lastName + '\''
                + ", avatarUrl='" + this.avatarUrl + '\''
                + ", acceptanceYear=" + this.acceptanceYear
                + ", gdpr=" + this.gdpr
                + '}';
    }
}
