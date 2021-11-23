package com.example.b07_final_project;

public class User {
    private String name;
    private String username;
    private String password;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ": (" + username + ", " + password + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        User other = (User)obj;
        return this.username.equals(other.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
