package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.ResetPassToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResetPassTokenRepos extends JpaRepository<ResetPassToken, Long> {
    ResetPassToken findResetPassTokenReposByToken(String token);
}
