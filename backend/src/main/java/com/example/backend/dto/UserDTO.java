package com.example.backend.dto;

public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String ageLimit;
    private String gender;
    private Boolean acceptRules;
    public UserDTO() { }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAgeLimit() {
        return ageLimit;
    }
    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Boolean getAcceptRules() {
        return acceptRules;
    }
    public void setAcceptRules(Boolean acceptRules) {
        this.acceptRules = acceptRules;
    }
}
