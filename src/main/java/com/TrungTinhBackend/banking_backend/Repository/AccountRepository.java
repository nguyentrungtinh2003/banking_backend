package com.TrungTinhBackend.banking_backend.Repository;

import com.TrungTinhBackend.banking_backend.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
}
