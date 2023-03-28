package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepos extends JpaRepository<Category, Long> {
    Page<Category> findAll(Pageable pageable);

}
