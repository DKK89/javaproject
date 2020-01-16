package team.everywhere.javaproject.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {
    @SerializedName("idx")
    @Expose
    private int idx;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("age")
    @Expose
    private int age;


    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(int idx, String id, String email, String name, String sex, int age) {
        this.idx = idx;
        this.id = id;
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public User(String id, String password, String email, String name, String sex, int age) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
