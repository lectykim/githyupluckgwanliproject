package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.entity.dto.github.FileDiffCheckRequestDTO;
import com.example.hyupup_tool.entity.dto.github.FileDiffCheckResponseDTO;
import com.example.hyupup_tool.entity.dto.github.GithubUserInfoRequest;
import com.example.hyupup_tool.entity.dto.github.GithubUserInfoResponse;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.externalapi.BranchManager;
import com.example.hyupup_tool.externalapi.CommitManager;
import com.example.hyupup_tool.externalapi.UserManager;
import com.example.hyupup_tool.util.DiffFormatter;
import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GithubApiServiceImpl implements GithubApiService{

    private final BranchManager branchManager;
    private final CommitManager commitManager;
    private final UserManager userManager;
    private final DiffFormatter diffFormatter;

    public ResponseEntity<byte[]> getUserInfo(){
        return userManager.getUserInfo(new GithubUserInfoRequest());
    }

    public ResponseEntity<byte[]> gerBranchList(String owner, String repo) {
        return branchManager.getBranchList(owner,repo);
    }

    public ResponseEntity<byte[]> getBranchDetail(String owner, String repo, String branch) {
        return branchManager.getBranchDetail(owner,repo,branch);
    }

    public ResponseEntity<byte[]> getCommitList(String owner, String repo, String path) {
        return commitManager.getCommitList(owner,repo,path);
    }

    public ResponseEntity<byte[]> getCommitDetail(String owner, String repo, String sha) {
        return commitManager.getCommitDetail(owner,repo,sha);
    }

    @Override
    public ResponseEntity<byte[]> getFileDiff(String owner, String repo, String path,String ref) {
        return commitManager.getFileDiff(owner,repo,path,ref);
    }

    @Override
    public FileDiffCheckResponseDTO fileDiffCheck(FileDiffCheckRequestDTO request) {

        String origin = rawToArray(request.origin());
        String before = rawToArray(request.before());

        origin = new String(Base64.getDecoder().decode(origin),StandardCharsets.UTF_8);
        before = new String(Base64.getDecoder().decode(before),StandardCharsets.UTF_8);
        String fileOrigin = diffFormatter.diff(origin,before);
        return new FileDiffCheckResponseDTO(fileOrigin);


    }

    public String rawToArray(String str){
        StringBuilder sb =new StringBuilder();
        String[] strs = str.split("\n");
        for(String data:strs){
            sb.append(data);
        }
        return sb.toString();
    }

}
