package com.example.hyupup_tool.config;

import org.springframework.http.HttpHeaders;

public class GithubHTTPHeader {

    public static HttpHeaders getHttpHeaders(String accessToken){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept","application/vnd.github+json");
        httpHeaders.set("Authorization","Bearer "+accessToken);
        httpHeaders.set("X-Github-Api-Version","2022-11-28");
        return httpHeaders;
    }

}
