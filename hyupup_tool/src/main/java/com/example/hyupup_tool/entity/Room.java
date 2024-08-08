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
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "max_member",nullable = false)
    private Long maxmember;

    @Column(name = "title",nullable = false)
    @Setter
    private String title;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "room")
    private List<MemberToRoom> memberToRoomList = new ArrayList<>();

    public Room(Long maxmember,String title){
        this.maxmember = maxmember;
        this.title = title;
    }

    public static Room of(Long maxmember, String title){
        return new Room(maxmember,title);
    }


}
