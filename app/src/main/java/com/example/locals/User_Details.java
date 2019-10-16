package com.example.locals;

public class User_Details  {

    String name, age, email, dob, userId;

    public User_Details() {
    }

    public User_Details(String name, String age, String email, String dob, String userId) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.dob = dob;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
