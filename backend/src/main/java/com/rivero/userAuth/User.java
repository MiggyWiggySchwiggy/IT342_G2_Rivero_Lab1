package com.rivero.userAuth;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data // This automatically adds getters and setters
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
}