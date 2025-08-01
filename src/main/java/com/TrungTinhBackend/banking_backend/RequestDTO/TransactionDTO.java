package com.TrungTinhBackend.banking_backend.RequestDTO;

import com.TrungTinhBackend.banking_backend.Enum.TransactionStatus;
import com.TrungTinhBackend.banking_backend.Enum.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;

    private TransactionType type;
    private Double amount;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long fromAccountId;
    private String formAccountNumber;
    private String formAccountName;
    private String formAccountCitizenId;
    private Long toAccountId;
    private String toAccountNumber;
    private String toAccountName;
    private String toAccountCitizenId;

    public TransactionDTO(Long id, TransactionType type, Double amount, TransactionStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, Long fromAccountId, String formAccountNumber, String formAccountName, String formAccountCitizenId, Long toAccountId, String toAccountNumber, String toAccountName, String toAccountCitizenId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.fromAccountId = fromAccountId;
        this.formAccountNumber = formAccountNumber;
        this.formAccountName = formAccountName;
        this.formAccountCitizenId = formAccountCitizenId;
        this.toAccountId = toAccountId;
        this.toAccountNumber = toAccountNumber;
        this.toAccountName = toAccountName;
        this.toAccountCitizenId = toAccountCitizenId;
    }

    public TransactionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getFormAccountNumber() {
        return formAccountNumber;
    }

    public void setFormAccountNumber(String formAccountNumber) {
        this.formAccountNumber = formAccountNumber;
    }

    public String getFormAccountName() {
        return formAccountName;
    }

    public void setFormAccountName(String formAccountName) {
        this.formAccountName = formAccountName;
    }

    public String getFormAccountCitizenId() {
        return formAccountCitizenId;
    }

    public void setFormAccountCitizenId(String formAccountCitizenId) {
        this.formAccountCitizenId = formAccountCitizenId;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    public String getToAccountCitizenId() {
        return toAccountCitizenId;
    }

    public void setToAccountCitizenId(String toAccountCitizenId) {
        this.toAccountCitizenId = toAccountCitizenId;
    }
}
