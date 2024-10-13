package com.example.hyupup_tool.websocket;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String type;

    @Setter
    private String sender;

    private String channelId;

    private Object data;

    @Setter
    private String messageNum;

    public void newConnect(){
        this.type = "new";
    }

    public void closeConnect(){
        this.type = "close";
    }


}
