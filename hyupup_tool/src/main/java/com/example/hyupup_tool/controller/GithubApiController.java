package com.example.hyupup_tool.controller;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.entity.dto.github.GithubUserInfoResponse;
import com.example.hyupup_tool.service.GithubApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/github-api")
@RequiredArgsConstructor
public class GithubApiController {

    private final GithubApiServiceImpl githubApiServiceImpl;

    @GetMapping("/user")
    public ResponseEntity<GithubUserInfoResponse> getUserInfo(){
        return githubApiServiceImpl.getUserInfo();
    }
    @GetMapping("/get-branch-list")
    public ResponseEntity<List<GetBranchListResponseDTO>> getBranchList(@RequestParam String owner,@RequestParam String repo){
        return githubApiServiceImpl.gerBranchList(owner,repo);
    }

    @GetMapping("/get-branch-detail")
    public ResponseEntity<GetBranchDetailsResponseDTO> getBranchDetail(@RequestParam String owner, @RequestParam String repo, @RequestParam String branch){
        return githubApiServiceImpl.getBranchDetail(owner,repo,branch);
    }

    @GetMapping("/get-commit-list")
    public ResponseEntity<List<GetCommitListResponseDTO>> getCommitList(@RequestParam String owner,@RequestParam String repo){
        return githubApiServiceImpl.getCommitList(owner,repo);
    }

    @GetMapping("/get-commit-detail")
    public ResponseEntity<GetCommitDetailsResponseDTO> getCommitDetail(@RequestParam String owner,@RequestParam String repo,@RequestParam String sha){
        return githubApiServiceImpl.getCommitDetail(owner,repo,sha);
    }

}
