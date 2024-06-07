package com.example.hyupup_tool.entity.dto;

public class GetBranchListResponseDTO {
    private String name;

    private Commit commit;
    static class Commit{
        private String sha;
        private String url;
    }
}
