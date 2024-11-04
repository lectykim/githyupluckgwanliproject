package com.example.hyupup_tool.entity.dto.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GitGraphIterator {
    private String sha;
    private String branch;
    private String message;
    private List<Parents> parentsList;
}
