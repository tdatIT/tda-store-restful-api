package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepos extends JpaRepository<VerificationToken, Long> {
    VerificationToken findVerificationTokenByToken(String token);

    VerificationToken findVerificationTokenByUser(User user);
}
