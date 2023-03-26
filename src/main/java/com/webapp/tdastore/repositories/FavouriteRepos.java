package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.Favourite;
import com.webapp.tdastore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepos extends JpaRepository<Favourite, Long> {
    List<Favourite> findByUser(User user);
}
