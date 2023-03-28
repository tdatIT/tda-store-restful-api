package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {
}
