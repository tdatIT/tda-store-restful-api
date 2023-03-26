package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.ResetPassToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResetPassTokenRepos extends JpaRepository<ResetPassToken, Long> {
    ResetPassToken findResetPassTokenReposByToken(String token);
}
