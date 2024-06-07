package com.example.hyupup_tool.entity.dto.util;

import java.time.LocalDateTime;


public class InnerCommit{
    private Author author;
    private Committer committer;
    private String message;

    static class Author{
        private String name;
        private String email;
        private LocalDateTime date;
    }
    static class Committer{
        private String name;
        private String email;
        private LocalDateTime date;
    }
}



