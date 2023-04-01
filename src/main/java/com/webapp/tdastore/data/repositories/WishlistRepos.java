package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.Wishlist;
import com.webapp.tdastore.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepos extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
}
