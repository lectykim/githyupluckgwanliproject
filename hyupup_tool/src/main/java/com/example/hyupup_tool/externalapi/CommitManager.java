package com.example.hyupup_tool.externalapi;

import com.example.hyupup_tool.config.GithubHTTPHeader;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
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
public class CommitManager {
    private final RestTemplate restTemplate;

    public ResponseEntity<List<GetCommitListResponseDTO>> getCommitList(String owner, String repo) {
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);

        String url = "https://api.github.com/repos/"+owner+"/"+repo+"/commits";

        ResponseEntity<List<GetCommitListResponseDTO>> response = restTemplate.exchange(url, HttpMethod.GET,entity,new ParameterizedTypeReference<List<GetCommitListResponseDTO>>(){});
        return response;
    }

    public ResponseEntity<GetCommitDetailsResponseDTO> getCommitDetail(String owner, String repo, String sha) {
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        String url = "https://api.github.com/repos/"+owner+"/"+repo+"/commits/"+sha;

        ResponseEntity<GetCommitDetailsResponseDTO> response = restTemplate.exchange(url,HttpMethod.GET,entity,GetCommitDetailsResponseDTO.class);
        return response;

    }
}
