package com.ssf.day13workshop.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User {



    @NotBlank(message = "Name must be provided")
    @Size(min = 3, max = 64, message = "Name must be between 3 and 64 characters")
    private String name;

    @NotNull(message = "Email must be provided")
    @Email(message = "Must be a valid email")
    private String email;

    @NotBlank(message = "Phone Number must be provided")
    @Pattern(regexp = "[0-9]{7}", message = "Phone Number must contain at least 7 digits")
    private String phoneno;

    @NotNull(message = "Date of Birth must be provided")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dob;

    public User() {
        generateID(8);
    }

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    private void generateID(int i) {
        Random random = new Random();
        StringBuilder stringbuilder = new StringBuilder();

        while (stringbuilder.length() < i) {
            stringbuilder.append(Integer.toHexString(random.nextInt()));

        }
        
        this.id = stringbuilder.toString().substring(0, i);
    }

        public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public boolean isValidAge() {
        if (dob == null) {
            return false;
        }

        LocalDate now = LocalDate.now();
        int age = Period.between(dob, now).getYears();
        return age >= 10 & age <= 100;
    }

    public User(String id, String name, String email, String phoneno, LocalDate dob){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.dob = dob;
    }

    }
