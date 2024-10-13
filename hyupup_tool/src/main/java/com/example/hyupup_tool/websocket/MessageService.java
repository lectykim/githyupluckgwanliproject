package com.example.hyupup_tool.websocket;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.dto.room.RoomDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final RedisTemplate<String,String> redisTemplate;
    public Long getMessageNum(String channelId){
        return redisTemplate.opsForList().size("Room:"+channelId+":Chat");
    }

    public List<RoomDTO> setNotReadMessageCounts(List<RoomDTO> roomDTOList, Member currentMember){
        for(var roomDto:roomDTOList){
            String readPosStr = redisTemplate.opsForValue().get("Room:"+roomDto.getRoomId()+"Member:"+currentMember.getEmail()+"ReadPos");
            Long readPos;
            if(readPosStr!=null){
                readPos = Long.parseLong(readPosStr);
            }else{
                readPos = 0L;
            }
            Long writePos = redisTemplate.opsForList().size("Room:"+roomDto.getRoomId()+":Chat");
            if(writePos!=null){
                roomDto.setNotReadMessageCount(writePos-readPos);
            }else{
                roomDto.setNotReadMessageCount(0L);
            }
            log.info("Room : {}, Message Count : {}",roomDto.getRoomId(),roomDto.getNotReadMessageCount());
        }
        return roomDTOList;
    }

}
