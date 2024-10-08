package com.example.hyupup_tool.entity.dto.github;

public record GithubUserInfoResponse(
   String login,
   String avatar_url,
   String bio
) {
}
