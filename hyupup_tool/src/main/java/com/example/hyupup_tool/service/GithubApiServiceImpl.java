package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.entity.dto.github.GithubUserInfoRequest;
import com.example.hyupup_tool.entity.dto.github.GithubUserInfoResponse;
import com.example.hyupup_tool.externalapi.BranchManager;
import com.example.hyupup_tool.externalapi.CommitManager;
import com.example.hyupup_tool.externalapi.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubApiServiceImpl implements GithubApiService{

    private final BranchManager branchManager;
    private final CommitManager commitManager;
    private final UserManager userManager;

    public ResponseEntity<GithubUserInfoResponse> getUserInfo(){
        return userManager.getUserInfo(new GithubUserInfoRequest());
    }

    public ResponseEntity<List<GetBranchListResponseDTO>> gerBranchList(String owner, String repo) {
        return branchManager.getBranchList(owner,repo);
    }

    public ResponseEntity<GetBranchDetailsResponseDTO> getBranchDetail(String owner, String repo, String branch) {
        return branchManager.getBranchDetail(owner,repo,branch);
    }

    public ResponseEntity<List<GetCommitListResponseDTO>> getCommitList(String owner, String repo) {
        return commitManager.getCommitList(owner,repo);
    }

    public ResponseEntity<GetCommitDetailsResponseDTO> getCommitDetail(String owner, String repo, String sha) {
        return commitManager.getCommitDetail(owner,repo,sha);
    }
}
