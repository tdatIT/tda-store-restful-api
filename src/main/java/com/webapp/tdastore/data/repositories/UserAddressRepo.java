package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {
    @Query("select a from UserAddress a where a.user.userId = ?1 and a.isDeleted =false")
    List<UserAddress> findAllActiveAddressesByUser(long userId);
}
