package com.example.hyupup_tool.entity.dto;

import com.example.hyupup_tool.entity.dto.util.Author;
import com.example.hyupup_tool.entity.dto.util.Committer;
import com.example.hyupup_tool.entity.dto.util.InnerCommit;
import lombok.Getter;

@Getter
public class GetCommitListResponseDTO {

    private String sha;

    private String nodeId;

    private InnerCommit innerCommit;

    private Author author;

    private Committer committer;

}
