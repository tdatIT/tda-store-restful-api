package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepos extends JpaRepository<Order, Long> {

}
