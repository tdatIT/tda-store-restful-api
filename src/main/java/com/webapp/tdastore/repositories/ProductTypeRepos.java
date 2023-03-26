package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepos extends JpaRepository<ProductType,Long> {
}
