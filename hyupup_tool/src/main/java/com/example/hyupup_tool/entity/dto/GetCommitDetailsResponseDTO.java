package com.example.hyupup_tool.entity.dto;

import com.example.hyupup_tool.entity.dto.util.Author;
import com.example.hyupup_tool.entity.dto.util.CommitFile;
import com.example.hyupup_tool.entity.dto.util.Committer;
import com.example.hyupup_tool.entity.dto.util.InnerCommit;

import java.util.List;

public class GetCommitDetailsResponseDTO {

    private String sha;

    private String nodeId;

    private InnerCommit innerCommit;

    private String url;

    private Author author;

    private Committer committer;

    private List<CommitFile> files;

}
