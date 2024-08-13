package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GithubApiService {
    ResponseEntity<List<GetBranchListResponseDTO>> gerBranchList(String owner, String repo);
    ResponseEntity<GetBranchDetailsResponseDTO> getBranchDetail(String owner, String repo, String branch);
    ResponseEntity<List<GetCommitListResponseDTO>> getCommitList(String owner, String repo);
    ResponseEntity<GetCommitDetailsResponseDTO> getCommitDetail(String owner, String repo, String sha);
}
