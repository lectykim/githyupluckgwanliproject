package com.example.hyupup_tool.entity;

import com.example.hyupup_tool.util.PurchasePlan;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name="pw",nullable = false)
    @Setter
    private String pw;

    @Column(name="github_access_token",nullable = false)
    @Setter
    private String githubAccessToken;


    @Enumerated(EnumType.STRING)
    @ColumnDefault("basic_plan")
    @Setter
    private PurchasePlan purchasePlan;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "user")
    private List<UserToRoom> userToRoomList= new ArrayList<>();

    private User(String email,String pw,String githubAccessToken){
        this.email=email;
        this.pw=pw;
        this.githubAccessToken = githubAccessToken;
    }

    public static User of(String email,String pw,String githubAccessToken){
        return new User(email,pw,githubAccessToken);
    }

}
