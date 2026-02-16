package com.rivero.userAuth.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty; // Import this!

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    // CHANGE IS HERE: Use WRITE_ONLY instead of @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    // --- CONSTRUCTORS ---
    public User() {}

    public User(String username, String firstName, String lastName, String email, String passwordHash) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // --- GETTERS AND SETTERS ---
    public Long getUserID() { return userID; }
    public void setUserID(Long userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}