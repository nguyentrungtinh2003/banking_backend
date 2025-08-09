package com.TrungTinhBackend.banking_backend.Service.Specification.Account;

import com.TrungTinhBackend.banking_backend.Entity.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {
    public static Specification<Account> filterAccount(String keyword) {
        return (root,query,cb) -> cb.or(
                cb.like(root.get("accountNumber"),"%" + keyword + "%")
        );
    }
}
