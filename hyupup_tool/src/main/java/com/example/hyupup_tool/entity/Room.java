package com.example.hyupup_tool.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "max_user",nullable = false)
    private Long maxUser;

    @Column(name = "title",nullable = false)
    @Setter
    private String title;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "room")
    private List<UserToRoom> userToRoomList = new ArrayList<>();

    public Room(Long maxUser,String title){
        this.maxUser = maxUser;
        this.title = title;
    }

    public static Room of(Long maxUser, String title){
        return new Room(maxUser,title);
    }


}
