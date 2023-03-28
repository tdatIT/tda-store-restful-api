package com.webapp.tdastore.services;

import com.webapp.tdastore.data.entities.Favourite;
import com.webapp.tdastore.data.entities.User;

import java.util.List;

public interface FavouriteService {
    List<Favourite> findAllByUser(User user);

    void insert(Favourite favourite);

    void remove(Favourite favourite);
}
