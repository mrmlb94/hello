package com.userhello.hello.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", familyName='" + familyName + '\'' +
                ", birthdate=" + birthdate +
                ", birthPlace='" + birthPlace + '\'' +
                ", currentCountry='" + currentCountry + '\'' +
                ", currentCity='" + currentCity + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", gpa=" + gpa +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    @Column(unique = true)
    private String uname;

    private String password;

    private String familyName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    private String birthPlace;

    private String currentCountry;

    private String currentCity;

    private String schoolName;

    @NotNull
    @DecimalMin("0.0")
    private Float gpa;

    private String phone;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String uname) {

        this.uname = uname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public enum Role {
        USER, ADMIN
    }

    public User(Long id, String name, String uname, String password, String familyName, Date birthdate, String birthPlace, String currentCountry, String currentCity, String schoolName, Float gpa, String phone, String email, Role role) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.password = password;
        this.familyName = familyName;
        this.birthdate = birthdate;
        this.birthPlace = birthPlace;
        this.currentCountry = currentCountry;
        this.currentCity = currentCity;
        this.schoolName = schoolName;
        this.gpa = gpa;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(String currentCountry) {
        this.currentCountry = currentCountry;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Float getGpa() {
        return gpa;
    }

    public void setGpa(Float gpa) {
        this.gpa = gpa;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
