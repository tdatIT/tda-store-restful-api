package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepos extends JpaRepository<OrderItems,Long> {
}
