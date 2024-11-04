package com.example.hyupup_tool.externalapi;

import com.example.hyupup_tool.config.GithubHTTPHeader;
import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.util.SessionGetter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BranchManager {

    private final RestTemplate restTemplate;

    public ResponseEntity<byte[]> getBranchList(String owner, String repo) {
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);

        String url = "https://api.github.com/repos/"+owner+"/"+repo+"/branches";

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET,entity,byte[].class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(response.getHeaders().getContentType());
        headers.setContentLength(response.getBody().length);


        return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
    }

    public List<GetBranchListResponseDTO> getBranchListWithDto(String owner, String repo) {
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);

        String url = "https://api.github.com/repos/"+owner+"/"+repo+"/branches";

        ResponseEntity<List<GetBranchListResponseDTO>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<GetBranchListResponseDTO>>() {});
        return response.getBody();
    }

    public ResponseEntity<byte[]> getBranchDetail(String owner, String repo, String branch) {
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        String url = "https://api.github.com/repos/"+owner+"/"+repo+"/branches/"+branch;

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET,entity,byte[].class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(response.getHeaders().getContentType());
        headers.setContentLength(response.getBody().length);


        return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
    }
}
