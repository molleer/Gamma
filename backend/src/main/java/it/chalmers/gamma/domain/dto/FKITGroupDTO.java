package it.chalmers.gamma.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.chalmers.gamma.db.entity.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("PMD.ExcessiveParameterList")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FKITGroupDTO {

    private final UUID id;
    private final Calendar becomesActive;
    private final Calendar becomesInactive;
    private final Text description;
    private final String email;
    private final Text function;
    private final String name;
    private final String prettyName;

    public FKITGroupDTO(UUID id,
                        Calendar becomesActive,
                        Calendar becomesInactive,
                        Text description,
                        String email,
                        Text function,
                        String name,
                        String prettyName) {
        this.id = id;
        this.becomesActive = becomesActive;
        this.becomesInactive = becomesInactive;
        this.description = description;
        this.email = email;
        this.function = function;
        this.name = name;
        this.prettyName = prettyName;
    }

    public UUID getId() {
        return this.id;
    }

    public Calendar getBecomesActive() {
        return this.becomesActive;
    }

    public Calendar getBecomesInactive() {
        return this.becomesInactive;
    }

    public Text getDescription() {
        return this.description;
    }

    public String getEmail() {
        return this.email;
    }

    public Text getFunction() {
        return this.function;
    }

    public boolean isActive() {
        Calendar now = new GregorianCalendar();
        return now.after(this.becomesActive) && now.before(this.becomesInactive);
    }

    public String getName() {
        return this.name;
    }

    public String getPrettyName() {
        return this.prettyName;
    }

    public FKITMinifiedGroupDTO toMinifiedDTO(){
        return new FKITMinifiedGroupDTO(
            this.name, this.function, this.email, this.description, this.id
        );
    }

}
