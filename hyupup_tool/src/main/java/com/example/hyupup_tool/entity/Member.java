package com.example.hyupup_tool.entity;

import com.example.hyupup_tool.entity.base.BaseEntity;
import com.example.hyupup_tool.entity.dto.member.MemberDTO;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.util.AuthorityRole;
import com.example.hyupup_tool.util.PurchasePlan;
import com.example.hyupup_tool.validator.RegexValidator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email",nullable = false)
    @Setter
    private String email;

    @Column(name="pw",nullable = false)
    @Setter
    private String pw;

    @Column(name="github_access_token",nullable = false)
    @Setter
    private String githubAccessToken;


    @Enumerated(EnumType.STRING)
    @Setter
    private PurchasePlan purchasePlan;

    @Enumerated(EnumType.STRING)
    @Setter
    private AuthorityRole authorityRole;

    @Column(name = "nickname",nullable = false)
    @Setter
    private String nickname;

    public MemberDTO toDto(){
        return MemberDTO.builder()
                .email(email)
                .authorityRole(authorityRole)
                .memberId(memberId)
                .pw(pw)
                .purchasePlan(purchasePlan)
                .memberToRoomDTOList(
                        memberToRoomList.stream()
                                .map(MemberToRoom::toDto)
                                .collect(Collectors.toList())
                )
                .githubAccessToken(githubAccessToken)
                .build();
    }


    @OneToMany(mappedBy = "member")
    private List<MemberToRoom> memberToRoomList;

    private Member(String email, String pw, String githubAccessToken,AuthorityRole role,String nickname){
        this.email=email;
        this.pw=pw;
        this.githubAccessToken = githubAccessToken;
        this.authorityRole = role;
        this.nickname=nickname;
    }

    public static Member of(String email, String pw, String githubAccessToken,AuthorityRole role,String nickname){
        validate(email,pw,githubAccessToken,role,nickname);
        return new Member(email,pw,githubAccessToken,role,nickname);
    }

    private static void validate(String email,String pw,String githubAccessToken,AuthorityRole role,String nickname){
        validateEmail(email);
        validatePw(pw);
        validateGithubAccessToken(githubAccessToken);
        validateAuthorityRole(role);
    }

    public static void validateEmail(String email){
        if(email == null || RegexValidator.validateEmail(email)){
            throw new BadRequestException("Email is not valid");
        }
    }

    public static void validatePw(String pw){
        if(pw == null || RegexValidator.validatePw(pw)){
            throw new BadRequestException("PW is not valid");
        }
    }

    public static void validateGithubAccessToken(String githubAccessToken){
        if(githubAccessToken == null || githubAccessToken.isEmpty()){
            throw new BadRequestException("Github Access Token is not valid");
        }
    }

    public static void validateAuthorityRole(AuthorityRole role){
        if(role == null){
            throw new BadRequestException("Role is not valid");
        }
    }



}
