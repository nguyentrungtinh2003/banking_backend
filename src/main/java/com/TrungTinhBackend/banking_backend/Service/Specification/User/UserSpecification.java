package com.TrungTinhBackend.banking_backend.Service.Specification.User;

import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Enum.Gender;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> filter(String keyword, Gender gender, LocalDate birthday) {
        return (root,query,cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            if(keyword != null && !keyword.isEmpty()) {
               predicates.add( cb.or(
                       cb.like(root.get("username"), "%" + keyword + "%"),
                       cb.like(root.get("email"), "%" + keyword + "%"),
                       cb.like(root.get("phone"), "%" + keyword + "%"),
                       cb.like(root.get("address"), "%" + keyword + "%"),
                       cb.like(root.get("citizenId"), "%" + keyword + "%")
               ));
            }

            if(gender != null) {
                predicates.add(cb.equal(root.get("gender"),gender));
            }

            if(birthday != null) {
                predicates.add(cb.equal(root.get("birthday"),birthday));
            }

            if(predicates.isEmpty()) {
               return cb.conjunction();
            }

            return cb.and(predicates.toArray(new Predicate[0]));

        };

    }
}
