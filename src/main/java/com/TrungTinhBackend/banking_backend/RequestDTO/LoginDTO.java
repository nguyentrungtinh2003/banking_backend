package com.TrungTinhBackend.banking_backend.RequestDTO;

public class LoginDTO {
    private String citizenId;
    private String password;

    public LoginDTO(String citizenId, String password) {
        this.citizenId = citizenId;
        this.password = password;
    }

    public LoginDTO() {

    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
