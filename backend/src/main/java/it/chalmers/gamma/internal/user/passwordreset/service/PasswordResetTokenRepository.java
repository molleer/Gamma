package it.chalmers.gamma.internal.user.passwordreset.service;

import it.chalmers.gamma.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, UserId> { }
