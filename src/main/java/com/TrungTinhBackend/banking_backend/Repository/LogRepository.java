package com.TrungTinhBackend.banking_backend.Repository;

import com.TrungTinhBackend.banking_backend.Entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log,Long> {
}
