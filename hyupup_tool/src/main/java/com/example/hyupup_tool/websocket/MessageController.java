package com.example.hyupup_tool.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat/{channelId}")
    public void message(Message message){
        simpMessageSendingOperations.convertAndSend("/sub/chat/"+message.getChannelId(),message);
    }
}
