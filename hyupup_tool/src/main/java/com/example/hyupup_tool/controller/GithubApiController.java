package com.example.hyupup_tool.controller;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.service.GithubApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/v1/github-api")
@RequiredArgsConstructor
public class GithubApiController {

    private final GithubApiService githubApiService;
    @GetMapping("/get-branch-list")
    public ResponseEntity<List<GetBranchListResponseDTO>> getBranchList(@RequestParam String owner,@RequestParam String repo){
        return githubApiService.gerBranchList(owner,repo);
    }

    @GetMapping("/get-branch-detail")
    public ResponseEntity<GetBranchDetailsResponseDTO> getBranchDetail(@RequestParam String owner, @RequestParam String repo, @RequestParam String branch){
        return githubApiService.getBranchDetail(owner,repo,branch);
    }

    @GetMapping("/get-commit-list")
    public ResponseEntity<List<GetCommitListResponseDTO>> getCommitList(@RequestParam String owner,@RequestParam String repo){
        return githubApiService.getCommitList(owner,repo);
    }

    @GetMapping("/get-commit-detail")
    public ResponseEntity<GetCommitDetailsResponseDTO> getCommitDetail(@RequestParam String owner,@RequestParam String repo,@RequestParam String sha){
        return githubApiService.getCommitDetail(owner,repo,sha);
    }

}
