package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepos extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.user.userId =?1 order by o.createDate desc ")
    List<Order> findLastOrderByUserId(long userId, Pageable pageable);
}
