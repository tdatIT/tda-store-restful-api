package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepos extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

}
