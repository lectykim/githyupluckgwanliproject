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

    public ResponseEntity<byte[]> getUserInfo(){
        return userManager.getUserInfo(new GithubUserInfoRequest());
    }

    public ResponseEntity<byte[]> gerBranchList(String owner, String repo) {
        return branchManager.getBranchList(owner,repo);
    }

    public ResponseEntity<byte[]> getBranchDetail(String owner, String repo, String branch) {
        return branchManager.getBranchDetail(owner,repo,branch);
    }

    public ResponseEntity<byte[]> getCommitList(String owner, String repo) {
        return commitManager.getCommitList(owner,repo);
    }

    public ResponseEntity<byte[]> getCommitDetail(String owner, String repo, String sha) {
        return commitManager.getCommitDetail(owner,repo,sha);
    }

    @Override
    public ResponseEntity<byte[]> getFileDiff(String owner, String repo, String path) {
        return commitManager.getFileDiff(owner,repo,path);
    }

    @Override
    public FileDiffCheckResponseDTO fileDiffCheck(FileDiffCheckRequestDTO request) {
        List<String> originList = rawToArray(request.origin());
        List<String> patchList = rawToArray(request.patch());


        originList = originList
                .stream()
                .map(s->new String(Base64.getDecoder().decode(s), StandardCharsets.UTF_8))
                .toList();

        Patch<String> diff = UnifiedDiffUtils.parseUnifiedDiff(patchList);
        List<String> result = new ArrayList<>();
        try{
             result = DiffUtils.patch(originList,diff);
        } catch (PatchFailedException e) {
            throw new BadRequestException("Patch Failed");
        }

        //TODO : originList와 result를 비교하여, 나만의 포맷으로 재정의

        return new FileDiffCheckResponseDTO(result);


    }

    public List<String> rawToArray(String str){
        return Arrays.stream(str.split("\n")).toList();
    }
}
