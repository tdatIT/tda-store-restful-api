package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.data.entities.Wishlist;
import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.repositories.WishlistRepos;
import com.webapp.tdastore.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepos wishlistRepos;

    @Override
    public Wishlist findById(long id) {
        return wishlistRepos.findById(id).orElseThrow();
    }

    @Override
    public List<Wishlist> findAllByUser(User user) {
        return wishlistRepos.findByUser(user);
    }

    @Override
    public void insert(Wishlist wishlist) {
        wishlistRepos.save(wishlist);
    }

    @Override
    public void remove(Wishlist wishlist) {
        wishlistRepos.delete(wishlist);
    }
}
