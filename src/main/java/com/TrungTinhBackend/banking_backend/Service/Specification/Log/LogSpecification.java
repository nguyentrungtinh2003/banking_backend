package com.TrungTinhBackend.banking_backend.Service.Specification.Log;

import com.TrungTinhBackend.banking_backend.Entity.Log;
import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import org.springframework.data.jpa.domain.Specification;

public class LogSpecification {
    public static Specification<Log> filterLog(LogAction action) {
        return (root,query,cb) -> cb.or(
                cb.equal(root.get("action"),action)
        );
    }
}
