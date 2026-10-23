package com.nexo.customers.customer;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private final String email;
    private String phone;
    private String firstName, lastName;
    private final String curp;
    private final LocalDate birthDate;
    private boolean emailVerified, phoneVerified;
    private IdentityStatus identityStatus;
    private final Instant createdAt;
    
    public Customer(String email, String phone, String firstName, String lastName, String curp, LocalDate birthDate) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.curp = curp;
        this.birthDate = birthDate;
        this.emailVerified = false;
        this.phoneVerified = false;
        this.identityStatus = IdentityStatus.NOT_STARTED;
        this.createdAt = Instant.now();
    }

    public String getPhone() {
        return phone;
    }

    public void changePhone(String phone) {
        this.phone = phone;
        this.phoneVerified = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
    
    public void changeName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCurp() {
        return curp;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public IdentityStatus getIdentityStatus() {
        return identityStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    
}
