package com.example.hyupup_tool.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_to_room")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userToRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Room room;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;

    @Column(name = "is_master")
    @Setter
    private boolean isMaster;

    public UserToRoom(User user, Room room, boolean isMaster){
        this.user = user;
        this.room = room;
        this.isMaster = isMaster;
    }

    public static UserToRoom of(User user, Room room, boolean isMaster){
        return new UserToRoom(user,room,isMaster);
    }



}
