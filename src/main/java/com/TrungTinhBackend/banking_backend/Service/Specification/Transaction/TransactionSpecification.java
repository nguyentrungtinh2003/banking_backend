package com.TrungTinhBackend.banking_backend.Service.Specification.Transaction;

import com.TrungTinhBackend.banking_backend.Entity.Transaction;
import com.TrungTinhBackend.banking_backend.Enum.TransactionStatus;
import com.TrungTinhBackend.banking_backend.Enum.TransactionType;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {
    public static Specification<Transaction> filterTransaction(TransactionType type, TransactionStatus status) {
        return (root,query,cb) -> cb.or(
                cb.equal(root.get("type"),type),
                cb.equal(root.get("status"),status)
        );
    }
}
