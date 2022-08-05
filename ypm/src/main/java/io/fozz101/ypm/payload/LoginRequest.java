package io.fozz101.ypm.payload;


import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Username cannot be black")
    private String username;
    @NotBlank(message = "Password cannot be black")
    private String password;

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
}
