package it.chalmers.gamma.adapter.secondary.jpa.client;

import it.chalmers.gamma.app.domain.ClientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, ClientId> { }
