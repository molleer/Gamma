package it.chalmers.gamma.adapter.secondary.jpa.util;

import it.chalmers.gamma.app.domain.Id;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class MutableEntity<I extends Id<?>, D extends DTO> extends AbstractEntity<I, D> {

    @Version
    @Column(name = "version")
    private int version;

    protected abstract void apply(D d);

}
