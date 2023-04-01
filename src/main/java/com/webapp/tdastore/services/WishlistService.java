package com.webapp.tdastore.services;

import com.webapp.tdastore.data.entities.Wishlist;
import com.webapp.tdastore.data.entities.User;

import java.util.List;

public interface WishlistService {
    List<Wishlist> findAllByUser(User user);

    Wishlist findById(long id);

    void insert(Wishlist wishlist);

    void remove(Wishlist wishlist);
}
