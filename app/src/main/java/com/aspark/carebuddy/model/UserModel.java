package com.aspark.carebuddy.model;

public class UserModel {

    private int id;
    private String name;
    private int age;
    private String location;


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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return "Elder [id=" + id + ", name=" + name + ", age=" + age + ", location=" + location + "]";
    }

}
