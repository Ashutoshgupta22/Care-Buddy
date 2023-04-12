package com.aspark.carebuddy.model;

public class UserModel {

    private int id;
    private String name;
    private int age;
    private double latitude;
    private double longitude;
    private String email;
    private String password;
    private static UserModel user;

    private UserModel() {

    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static UserModel getCurrentUser() {

        if(user == null)
            user = new UserModel();

        return user;
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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
