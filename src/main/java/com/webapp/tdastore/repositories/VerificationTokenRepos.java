package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.User;
import com.webapp.tdastore.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepos extends JpaRepository<VerificationToken, Long> {
    VerificationToken findVerificationTokenByToken(String token);

    VerificationToken findVerificationTokenByUser(User user);
}
