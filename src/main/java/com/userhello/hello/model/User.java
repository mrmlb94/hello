package com.userhello.hello.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String uname;
    private String familyName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    private String birthPlace;
    private String currentCountry;
    private String currentCity;
    private String schoolName;

    @DecimalMin("0.0")
    private Float gpa;

    private String phone;
    private String email;

    public User() {
    }

    public User(String uname) {
        this.uname = uname;
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.uname = builder.uname;
        this.familyName = builder.familyName;
        this.birthdate = builder.birthdate;
        this.birthPlace = builder.birthPlace;
        this.currentCountry = builder.currentCountry;
        this.currentCity = builder.currentCity;
        this.schoolName = builder.schoolName;
        this.gpa = builder.gpa;
        this.phone = builder.phone;
        this.email = builder.email;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUname() { return uname; }
    public void setUname(String uname) { this.uname = uname; }
    public String getFamilyName() { return familyName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }
    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }
    public String getBirthPlace() { return birthPlace; }
    public void setBirthPlace(String birthPlace) { this.birthPlace = birthPlace; }
    public String getCurrentCountry() { return currentCountry; }
    public void setCurrentCountry(String currentCountry) { this.currentCountry = currentCountry; }
    public String getCurrentCity() { return currentCity; }
    public void setCurrentCity(String currentCity) { this.currentCity = currentCity; }
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public Float getGpa() { return gpa; }
    public void setGpa(Float gpa) { this.gpa = gpa; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public static class Builder {
        private Long id;
        private String name;
        private String uname;
        private String familyName;
        private Date birthdate;
        private String birthPlace;
        private String currentCountry;
        private String currentCity;
        private String schoolName;
        private Float gpa;
        private String phone;
        private String email;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUname(String uname) {
            this.uname = uname;
            return this;
        }

        public Builder setFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder setBirthdate(Date birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public Builder setBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
            return this;
        }

        public Builder setCurrentCountry(String currentCountry) {
            this.currentCountry = currentCountry;
            return this;
        }

        public Builder setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
            return this;
        }

        public Builder setSchoolName(String schoolName) {
            this.schoolName = schoolName;
            return this;
        }

        public Builder setGpa(Float gpa) {
            this.gpa = gpa;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
