package com.example.hyupup_tool.externalapi;

import com.example.hyupup_tool.config.GithubHTTPHeader;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.util.SessionGetter;
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
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommitManager {
    private final RestTemplate restTemplate;

    public ResponseEntity<byte[]> getCommitList(String owner, String repo,String path) {
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);

        String url = "";
        if(path.isEmpty()){
            url = "https://api.github.com/repos/"+owner+"/"+repo+"/commits";
        }else{
            url ="https://api.github.com/repos/"+owner+"/"+repo+"/commits?path="+path;
        }

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET,entity,byte[].class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(response.getHeaders().getContentType());
        headers.setContentLength(response.getBody().length);
        return response;
    }

    public ResponseEntity<byte[]> getCommitDetail(String owner, String repo, String sha,String refSha) {
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        String url;
        if(refSha.isEmpty()){
             url = "https://api.github.com/repos/"+owner+"/"+repo+"/commits/"+sha;
        }
        else{
            url ="https://api.github.com/repos/"+owner+"/"+repo+"/commits?sha="+refSha;
        }

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET,entity,byte[].class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(response.getHeaders().getContentType());
        headers.setContentLength(response.getBody().length);
        return response;

    }

    public List<GetCommitDetailsResponseDTO> getCommitDetailsWithDto(String owner,String repo,String sha,String refSha){
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        String url;
        if(refSha.isEmpty()){
            url = "https://api.github.com/repos/"+owner+"/"+repo+"/commits/"+sha;
        }
        else{
            url ="https://api.github.com/repos/"+owner+"/"+repo+"/commits?sha="+refSha;
        }

        ResponseEntity<List<GetCommitDetailsResponseDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<GetCommitDetailsResponseDTO>>(){}
        );
        return response.getBody();
    }

    public String getRecentCommit(String owner,String repo){
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);

        String url = "https://api.github.com/repos/"+owner+"/"+repo+"/commits";
        ResponseEntity<List<GetCommitListResponseDTO>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<GetCommitListResponseDTO>>() {});

        return Objects.requireNonNull(response.getBody()).getFirst().getSha();
    }
    public ResponseEntity<byte[]> getFileDiff(String owner,String repo,String path,String ref){
        var memberDto = SessionGetter.getCurrentMemberDto();
        HttpHeaders httpHeaders = GithubHTTPHeader.getHttpHeaders(memberDto.getGithubAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        String url;
        if(ref.isEmpty()){
            url = "https://api.github.com/repos/"+owner+"/"+repo+"/contents"+path;
        }else{
            url = "https://api.github.com/repos/"+owner+"/"+repo+"/contents"+path+"?ref="+ref;
        }


        ResponseEntity<byte[]> response = restTemplate.exchange(url,HttpMethod.GET,entity,byte[].class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(response.getHeaders().getContentType());
        headers.setContentLength(response.getBody().length);
        return response;
    }
}
