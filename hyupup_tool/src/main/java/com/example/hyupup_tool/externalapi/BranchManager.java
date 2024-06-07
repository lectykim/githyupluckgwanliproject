package com.example.hyupup_tool.externalapi;

import com.example.hyupup_tool.config.GithubHTTPHeader;
import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
public class BranchManager {

    private final RestTemplate restTemplate;

    public ResponseEntity<List<GetBranchListResponseDTO>> getBranchList(String owner, String repo) {
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);

        String url = "https://api.github.com/repos/"+owner+"/"+repo+"/branches";

        ResponseEntity<List<GetBranchListResponseDTO>> response = restTemplate.exchange(url, HttpMethod.GET,entity,new ParameterizedTypeReference<List<GetBranchListResponseDTO>>(){});
        return response;
    }

    public ResponseEntity<GetBranchDetailsResponseDTO> getBranchDetail(String owner, String repo, String branch) {
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        String url = "https://api.github.com/repose/"+owner+"/"+repo+"/branches/"+branch;

        ResponseEntity<GetBranchDetailsResponseDTO> response = restTemplate.exchange(url,HttpMethod.GET,entity, GetBranchDetailsResponseDTO.class);
        return response;
    }
}
