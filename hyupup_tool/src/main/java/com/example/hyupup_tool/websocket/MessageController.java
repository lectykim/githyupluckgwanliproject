package com.example.hyupup_tool.websocket;

import com.example.hyupup_tool.util.ChatDataScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {


    private final MessageService messageService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatDataScheduler chatDataScheduler;
    @MessageMapping("/chat/{channelId}")
    public void message(Message message){
        if(message.getType().equals("CHAT")){
            chatDataScheduler.addToQueue(message);
        }
        Long messageNum = messageService.getMessageNum(message.getChannelId())+chatDataScheduler.getQueueSize();
        message.setMessageNum(messageNum.toString());
        log.info("Current Member : {}, Current MessageNum : {}",message.getSender(),message.getMessageNum());
        simpMessageSendingOperations.convertAndSend("/sub/chat/"+message.getChannelId(),message);
    }

    @MessageMapping("/receive/{channelId}")
    public void receive(Message message){
        if(message.getType().equals("RECEIVE")){
            chatDataScheduler.addToQueue(message);
        }
    }
}
