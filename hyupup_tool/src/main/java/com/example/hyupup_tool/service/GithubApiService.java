package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.entity.dto.github.*;
import com.example.hyupup_tool.entity.dto.util.GitGraphIterator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GithubApiService {
    ResponseEntity<byte[]> gerBranchList(String owner, String repo);
    ResponseEntity<byte[]> getBranchDetail(String owner, String repo, String branch);
    ResponseEntity<byte[]> getCommitList(String owner, String repo,String path);
    ResponseEntity<byte[]> getCommitDetail(String owner, String repo, String sha,String refSha);

    ResponseEntity<byte[]> getFileDiff(String owner,String repo,String path,String ref);

    FileDiffCheckResponseDTO fileDiffCheck(FileDiffCheckRequestDTO request);

    SyncGitGraphResponse syncGitGraph(SyncGitGraphRequest request);

    GetGitGraphResponse getGitGraph(GetGitGraphRequest request);

    String rawToArray(String str);

    List<GitGraphDto> drawGitGraph(String recentCommitSha,String owner,String repo);

    void setRecentCommit(List<GetCommitDetailsResponseDTO> commitList,String branch);

    void setBranchColor(List<GetBranchListResponseDTO> branchList,String owner,String repo);

    void setCommitData(List<GetCommitDetailsResponseDTO> commitList,String branch);
    GitGraphIterator findGitGraph(String currentCommitSha);


}
