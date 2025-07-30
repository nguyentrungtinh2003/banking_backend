package com.TrungTinhBackend.banking_backend.RequestDTO;

import com.TrungTinhBackend.banking_backend.Enum.Gender;
import com.TrungTinhBackend.banking_backend.Enum.Role;
import java.time.LocalDate;

public class RegisterDTO {

    private String username;
    private String password;
    private String citizenId;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String address;
    private Gender gender;
    private String img;
    private Role role;

    public RegisterDTO() {
    }

    public RegisterDTO(String username, String password, String citizenId, String email, String phone, LocalDate birthday, String address, Gender gender, String img, Role role) {
        this.username = username;
        this.password = password;
        this.citizenId = citizenId;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.img = img;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
