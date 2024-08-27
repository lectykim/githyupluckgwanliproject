package com.example.hyupup_tool.entity;

import com.example.hyupup_tool.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_to_room")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberToRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberToRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Room room;

    @Column(name = "is_master")
    @Setter
    private boolean isMaster;

    public MemberToRoom(Member member, Room room, boolean isMaster){
        this.member = member;
        this.room = room;
        this.isMaster = isMaster;
    }

    public static MemberToRoom of(Member member, Room room, boolean isMaster){
        return new MemberToRoom(member,room,isMaster);
    }



}
