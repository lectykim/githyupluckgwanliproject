package com.example.hyupup_tool.entity.dto;

import com.example.hyupup_tool.entity.dto.util.Author;
import com.example.hyupup_tool.entity.dto.util.Committer;
import com.example.hyupup_tool.entity.dto.util.InnerCommit;

import java.time.LocalDateTime;

public class GetBranchDetailsResponseDTO {
    private String name;

    private Commit commit;
    static class Commit{
        private String sha;

        private String nodeId;

        private InnerCommit innerCommit;

        private String url;

        private Author author;

        private Committer committer;



    }



}
