package com.TrungTinhBackend.banking_backend.Service.Specification.User;

import com.TrungTinhBackend.banking_backend.Entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> searchByKeyword(String keyword) {
        return (root,query,cb) -> cb.or(
                cb.like(root.get("username"), "%" + keyword + "%"),
                cb.like(root.get("email"), "%" + keyword + "%"),
                cb.like(root.get("phone"), "%" + keyword + "%"),
                cb.like(root.get("address"), "%" + keyword + "%"),
                cb.like(root.get("citizenId"), "%" + keyword + "%")
        );

    }
}
