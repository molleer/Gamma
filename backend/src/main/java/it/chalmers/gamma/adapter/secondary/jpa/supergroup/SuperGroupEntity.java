package it.chalmers.gamma.adapter.secondary.jpa.supergroup;

import it.chalmers.gamma.app.domain.Name;
import it.chalmers.gamma.app.domain.PrettyName;
import it.chalmers.gamma.app.domain.SuperGroup;
import it.chalmers.gamma.app.domain.SuperGroupId;
import it.chalmers.gamma.app.domain.SuperGroupType;
import it.chalmers.gamma.app.domain.Email;
import it.chalmers.gamma.adapter.secondary.jpa.text.TextEntity;
import it.chalmers.gamma.adapter.secondary.jpa.util.MutableEntity;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "fkit_super_group")
public class SuperGroupEntity extends MutableEntity<SuperGroupId, SuperGroup> {

    @Id
    @Column(name = "fkit_super_group")
    private UUID id;

    @JoinColumn(name = "description")
    @OneToOne(cascade = CascadeType.MERGE)
    private TextEntity description;

    @Column(name = "e_name")
    private String name;

    @Column(name = "pretty_name")
    private String prettyName;

    @Column(name = "super_group_type_name")
    private String superGroupType;

    @Column(name = "e_name")
    private String email;

    public SuperGroupEntity() {}

    public SuperGroupEntity(SuperGroup sg) {
        assert(sg.id() != null);

        this.id = sg.id().value();
        this.description = new TextEntity();

        apply(sg);
    }

    @Override
    public void apply(SuperGroup sg)  {
        assert(this.id == sg.id().value());

        this.email = sg.email().value();
        this.name = sg.name().value();
        this.prettyName = sg.prettyName().value();
        this.superGroupType = sg.type().value();


        this.description.apply(sg.description());
    }

    @Override
    public SuperGroup toDTO() {
        return new SuperGroup(
                SuperGroupId.valueOf(this.id),
                new Name(this.name),
                new PrettyName(this.prettyName),
                SuperGroupType.valueOf(this.superGroupType),
                new Email(this.email),
                this.description.toDTO()
        );
    }

    @Override
    protected SuperGroupId id() {
        return SuperGroupId.valueOf(this.id);
    }
}
