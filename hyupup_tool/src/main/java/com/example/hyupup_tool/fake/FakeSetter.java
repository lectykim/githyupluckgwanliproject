package com.example.hyupup_tool.fake;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class FakeSetter {
    public static void setField(Object targetObject,String filedName,Object value){
        try{
            Field field = targetObject.getClass().getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(targetObject,value);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
