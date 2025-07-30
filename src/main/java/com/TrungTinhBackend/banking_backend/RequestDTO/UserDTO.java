package com.TrungTinhBackend.banking_backend.RequestDTO;

import com.TrungTinhBackend.banking_backend.Enum.Gender;
import com.TrungTinhBackend.banking_backend.Enum.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String citizenId;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String address;
    private Gender gender;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean deleted;

    public UserDTO() {
    }


    public UserDTO(Long id, String username, String password, String citizenId, String email, String phone, LocalDate birthday, String address, Gender gender, Role role, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.citizenId = citizenId;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
