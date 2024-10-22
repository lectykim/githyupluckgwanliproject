package com.example.hyupup_tool.entity.dto.github;

public record FileDiffCheckRequestDTO(
        String origin,
        String patch
) {
}
