package com.TrungTinhBackend.banking_backend.RequestDTO;

public class ResetPassword {

    private String otp;
    private String email;
    private String newPassword;

    public ResetPassword() {
    }

    public ResetPassword(String otp, String email, String newPassword) {
        this.otp = otp;
        this.email = email;
        this.newPassword = newPassword;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
