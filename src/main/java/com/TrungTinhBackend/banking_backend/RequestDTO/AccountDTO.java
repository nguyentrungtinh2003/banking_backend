package com.TrungTinhBackend.banking_backend.RequestDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountDTO {
    private Long id;

    private String accountNumber;
    private Double balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String username;
    private String img;
    private String citizenId;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthday;

    private boolean deleted;

    public AccountDTO() {

    }

    public AccountDTO(Long id, String accountNumber, Double balance, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId, String username, String img, String citizenId, String email, String phone, String address, LocalDate birthday, boolean deleted) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.username = username;
        this.img = img;
        this.citizenId = citizenId;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
