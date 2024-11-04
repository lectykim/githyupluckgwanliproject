package com.example.hyupup_tool.entity.dto;

import com.example.hyupup_tool.entity.dto.util.*;
import lombok.Getter;

import java.util.List;

@Getter
public class GetCommitDetailsResponseDTO {

    private String sha;

    private InnerCommit commit;

    private String url;

    private Author author;

    private Committer committer;

    private List<Parents> parents;

    private List<CommitFile> files;

}
