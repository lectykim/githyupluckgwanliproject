package com.example.hyupup_tool.entity.dto.room;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    private String type;
    private String sender;
    private String channelId;
    private Object data;

    public static MessageDTO makeMessage(String msg, Long roomId){
        String[] strs = msg.split(":");
        return MessageDTO.builder()
                .type("CHAT")
                .sender(strs[0])
                .data(strs[1])
                .channelId(roomId.toString())
                .build();
    }
}
