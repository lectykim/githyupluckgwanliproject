package com.example.hyupup_tool.config;

import org.springframework.http.HttpHeaders;

public class GithubHTTPHeader {

    public static HttpHeaders getHttpHeaders(){
        //TODO : ERD 완성 후 퍼스널토큰 삽입
        //String token = userService.getPersonalToken();
        String token = "";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept","application/vnd.github+json");
        httpHeaders.set("Authorization","Bearer "+token);
        httpHeaders.set("X-Github-Api-Version","2022-11-28");
        return httpHeaders;
    }

}
