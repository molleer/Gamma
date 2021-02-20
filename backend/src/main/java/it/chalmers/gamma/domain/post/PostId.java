package it.chalmers.gamma.domain.post;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PostId implements Serializable {

    @JsonValue
    @Column(name = "post_id")
    private UUID value;

    protected PostId() { }

    public PostId(UUID value) {
        this.value = value;
    }

    public static PostId valueOf(String value) {
        return new PostId(UUID.fromString(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostId postId = (PostId) o;
        return Objects.equals(value, postId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "PostId: " + this.value;
    }
}
