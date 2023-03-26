package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.entities.Favourite;
import com.webapp.tdastore.entities.User;
import com.webapp.tdastore.repositories.FavouriteRepos;
import com.webapp.tdastore.services.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    private FavouriteRepos favouriteRepos;

    @Override
    public List<Favourite> findAllByUser(User user) {
        return favouriteRepos.findByUser(user);
    }

    @Override
    public void insert(Favourite favourite) {
        favouriteRepos.save(favourite);
    }

    @Override
    public void remove(Favourite favourite) {
        favouriteRepos.delete(favourite);
    }
}
