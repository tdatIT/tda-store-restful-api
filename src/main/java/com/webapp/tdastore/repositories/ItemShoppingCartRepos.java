package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.ItemShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemShoppingCartRepos extends JpaRepository<ItemShoppingCart, Long> {
    @Query("select i from ItemShoppingCart i where i.user.userId = ?1")
    List<ItemShoppingCart> findAllByUserId(long userId);

    @Query("select i from ItemShoppingCart i where i.product.productCode =?1 and i.user.userId = ?2")
    ItemShoppingCart findItemShoppingCartByProductCodeAndUserId(String productCode, long userId);
}
