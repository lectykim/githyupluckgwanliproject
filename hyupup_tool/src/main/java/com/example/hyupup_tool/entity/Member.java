package com.example.hyupup_tool.entity;

import com.example.hyupup_tool.util.AuthorityRole;
import com.example.hyupup_tool.util.PurchasePlan;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

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

    @Enumerated(EnumType.STRING)
    @Setter
    @ColumnDefault("normal_member")
    private AuthorityRole authorityRole;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;



    @OneToMany(mappedBy = "member")
    private List<MemberToRoom> memberToRoomList= new ArrayList<>();

    private Member(String email, String pw, String githubAccessToken){
        this.email=email;
        this.pw=pw;
        this.githubAccessToken = githubAccessToken;
    }

    public static Member of(String email, String pw, String githubAccessToken){
        return new Member(email,pw,githubAccessToken);
    }

}
