package com.example.b07_final_project;

public class User {
    private String name;
    private String email;
    private String password;

    public User(){}

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ": (" + email + ", " + password + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return this.email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }



}
