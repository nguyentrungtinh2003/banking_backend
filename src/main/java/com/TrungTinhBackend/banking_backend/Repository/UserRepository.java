package com.TrungTinhBackend.banking_backend.Repository;

import com.TrungTinhBackend.banking_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndDeletedFalse(String username);
}
