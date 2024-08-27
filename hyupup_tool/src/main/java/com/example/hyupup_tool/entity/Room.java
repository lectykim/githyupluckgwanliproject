package com.example.hyupup_tool.entity;

import com.example.hyupup_tool.entity.base.BaseEntity;
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
public class Room extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "max_member",nullable = false)
    private Long maxMember;

    @Column(name = "title",nullable = false)
    @Setter
    private String title;



    @OneToMany(mappedBy = "room")
    private List<MemberToRoom> memberToRoomList = new ArrayList<>();

    public Room(Long maxMember,String title){
        this.maxMember = maxMember;
        this.title = title;
    }

    public static Room of(Long maxMember, String title){
        return new Room(maxMember,title);
    }


}
