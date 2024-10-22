package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.entity.dto.github.FileDiffCheckRequestDTO;
import com.example.hyupup_tool.entity.dto.github.FileDiffCheckResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GithubApiService {
    ResponseEntity<byte[]> gerBranchList(String owner, String repo);
    ResponseEntity<byte[]> getBranchDetail(String owner, String repo, String branch);
    ResponseEntity<byte[]> getCommitList(String owner, String repo);
    ResponseEntity<byte[]> getCommitDetail(String owner, String repo, String sha);

    ResponseEntity<byte[]> getFileDiff(String owner,String repo,String path);

    FileDiffCheckResponseDTO fileDiffCheck(FileDiffCheckRequestDTO request);

    public List<String> rawToArray(String str);
}
