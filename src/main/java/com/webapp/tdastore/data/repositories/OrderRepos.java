package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepos extends JpaRepository<Order, Long> {

}
