package com.example.hyupup_tool.util;

import com.example.hyupup_tool.websocket.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Block;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatDataScheduler {

    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> receiveQueue = new LinkedBlockingQueue<>();
    private final RedisTemplate<String,String> redisTemplate;

    // 데이터를 큐에 추가하는 메서드 (이 메서드는 여러 스레드에서 호출될 수 있음)
    public void addToQueue(Message data) {
        try {
            if(data.getType().equals("CHAT")){
                queue.put(data); // Thread-safe 하게 데이터를 큐에 추가
            }else if(data.getType().equals("RECEIVE")){
                receiveQueue.put(data);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 스레드가 인터럽트되었을 경우 처리
        }
    }

    public int getQueueSize(){
        return queue.size();
    }

    @Scheduled(fixedRate = 5000)
    public void store(){
        while(!queue.isEmpty()){
            try{
                Message data = queue.take();
                if(data.getType().equals("CHAT")){
                    String key = "Room:"+data.getChannelId()+":Chat";
                    String value = data.getSender() + ":" + data.getData().toString();
                    redisTemplate.opsForList().rightPush(key,value);
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }

        }
        while(!receiveQueue.isEmpty()){
            try{
                Message data = receiveQueue.take();
                if(data.getType().equals("RECEIVE")){
                    String key = "Room:"+data.getChannelId()+"Member:"+data.getSender()+"ReadPos";
                    redisTemplate.opsForValue().set(key,data.getMessageNum());
                    log.info("Current Member: {} , Current ReadPos : {}",data.getSender(),data.getMessageNum());
                }
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }



}
