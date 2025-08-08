package com.TrungTinhBackend.banking_backend.Repository;

import com.TrungTinhBackend.banking_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    User findByUsernameAndDeletedFalse(String username);
    User findByCitizenId(String citizenId);
    User findByEmail(String email);
    User findByUsername(String username);
}
