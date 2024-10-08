package com.example.hyupup_tool.externalapi;

import com.example.hyupup_tool.config.GithubHTTPHeader;
import com.example.hyupup_tool.entity.dto.github.GithubUserInfoRequest;
import com.example.hyupup_tool.entity.dto.github.GithubUserInfoResponse;
import com.example.hyupup_tool.util.SessionGetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserManager {

    private final RestTemplate restTemplate;

    public ResponseEntity<byte[]> getUserInfo(GithubUserInfoRequest request){
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);

        String url = "https://api.github.com/user";

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET,entity,byte[].class);

        //log.info("{}",response.getHeaders());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(response.getHeaders().getContentType());
        headers.setContentLength(response.getBody().length);



        return new ResponseEntity<>(response.getBody(),headers,response.getStatusCode());
    }

}
