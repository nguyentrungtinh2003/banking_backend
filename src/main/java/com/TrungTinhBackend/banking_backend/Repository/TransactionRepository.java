package com.TrungTinhBackend.banking_backend.Repository;

import com.TrungTinhBackend.banking_backend.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
