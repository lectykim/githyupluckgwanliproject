package com.example.hyupup_tool.controller;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.entity.dto.github.*;
import com.example.hyupup_tool.service.GithubApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/github-api")
@RequiredArgsConstructor
public class GithubApiController {

    private final GithubApiServiceImpl githubApiServiceImpl;

    @GetMapping("/user")
    public ResponseEntity<byte[]> getUserInfo(){
        return githubApiServiceImpl.getUserInfo();
    }
    @GetMapping("/get-branch-list")
    public ResponseEntity<byte[]> getBranchList(@RequestParam String owner,@RequestParam String repo){
        return githubApiServiceImpl.gerBranchList(owner,repo);
    }

    @GetMapping("/get-branch-detail")
    public ResponseEntity<byte[]> getBranchDetail(@RequestParam String owner, @RequestParam String repo, @RequestParam String branch){
        return githubApiServiceImpl.getBranchDetail(owner,repo,branch);
    }

    @GetMapping("/get-commit-list")
    public ResponseEntity<byte[]> getCommitList(@RequestParam(value = "owner") String owner,@RequestParam(value = "repo") String repo, @RequestParam(value = "path") String path){
        return githubApiServiceImpl.getCommitList(owner,repo,path);
    }

    @GetMapping("/get-commit-detail")
    public ResponseEntity<byte[]> getCommitDetail(@RequestParam(value = "owner") String owner,@RequestParam(value = "repo") String repo,@RequestParam(value = "sha") String sha,@RequestParam(value="refSha") String refSha){
        return githubApiServiceImpl.getCommitDetail(owner,repo,sha,refSha);
    }

    @GetMapping("/get-file-diff")
    public ResponseEntity<byte[]> getFileDiff(@RequestParam String owner, @RequestParam String repo, @RequestParam String path,@RequestParam String ref){
        return githubApiServiceImpl.getFileDiff(owner,repo,path,ref);
    }

    @GetMapping("/file-diff-check")
    public ResponseEntity<FileDiffCheckResponseDTO> fileDiffCheck(FileDiffCheckRequestDTO request){
        return ResponseEntity.ok(githubApiServiceImpl.fileDiffCheck(request));
    }

    @PostMapping("/sync-git-graph")
    public ResponseEntity<SyncGitGraphResponse> syncGitGraph(@RequestBody SyncGitGraphRequest request){
        return ResponseEntity.ok(githubApiServiceImpl.syncGitGraph(request));
    }

    @GetMapping("/get-git-graph")
    public ResponseEntity<GetGitGraphResponse> getGitGraph(GetGitGraphRequest request){
        return ResponseEntity.ok(githubApiServiceImpl.getGitGraph(request));
    }
}
