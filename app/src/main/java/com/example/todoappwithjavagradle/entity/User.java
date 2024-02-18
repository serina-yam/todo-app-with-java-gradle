package com.example.todoappwithjavagradle.entity;

import java.sql.Timestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "login_type", nullable = false)
    private String loginType;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "oauth2_user_id")
    private String oauth2UserId;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;

    public User() {
    }

    /**
     * 
     * @param username
     * @param passwordHash
     * @param oauth2UserId
     * @param loginType
     */
    public User(String username, String passwordHash, String oauth2UserId, String loginType) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.oauth2UserId = oauth2UserId;
        this.loginType = loginType;
    }


    @PrePersist
    protected void onCreate() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.createdAt = ts;
        this.updatedAt = ts;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
