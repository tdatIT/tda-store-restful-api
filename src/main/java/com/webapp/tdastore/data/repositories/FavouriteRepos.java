package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.Favourite;
import com.webapp.tdastore.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepos extends JpaRepository<Favourite, Long> {
    List<Favourite> findByUser(User user);
}
