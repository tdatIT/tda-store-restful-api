package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepos extends JpaRepository<OrderItems,Long> {
}
