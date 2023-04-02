package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepos extends JpaRepository<Voucher, String> {
    @Query("select v from Voucher v where v.voucherCode =?1 and date(v.endDate)<=CURRENT_DATE()")
    Voucher validateVoucherCode(String voucherCode);
}
