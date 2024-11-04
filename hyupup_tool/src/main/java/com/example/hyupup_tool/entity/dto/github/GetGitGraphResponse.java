package com.example.hyupup_tool.entity.dto.github;

import java.util.List;

public record GetGitGraphResponse(
        List<GitGraphDto> gitGraphDtoList
) {
}
