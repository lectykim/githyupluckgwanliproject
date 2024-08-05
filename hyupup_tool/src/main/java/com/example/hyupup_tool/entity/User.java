package com.example.hyupup_tool.entity;

import com.example.hyupup_tool.util.PurchasePlan;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private Long userId;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name="pw",nullable = false)
    private String pw;

    @Column(name="github_access_token",nullable = false)
    private String githubAccessToken;


    @Enumerated(EnumType.STRING)
    @ColumnDefault("basic_plan")
    private PurchasePlan purchasePlan;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;

    private User(String email,String pw,String githubAccessToken){
        this.email=email;
        this.pw=pw;
        this.githubAccessToken = githubAccessToken;
    }

    public static User of(String email,String pw,String githubAccessToken){
        return new User(email,pw,githubAccessToken);
    }

}
