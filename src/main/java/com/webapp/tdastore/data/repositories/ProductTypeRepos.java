package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepos extends JpaRepository<ProductType,Long> {
}
