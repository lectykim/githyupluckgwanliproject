package com.example.hyupup_tool.entity.dto.github;

public record GitGraphDto(
        Long recentCommitNum,
        String branch,
        String branchColor,
        Long branchLineNum,
        String commitMessage,
        String commitSha
) {
}
