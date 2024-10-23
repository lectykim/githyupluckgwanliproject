package com.example.hyupup_tool.util;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.StringsComparator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiffFormatter {
    // LCS 알고리즘을 사용하여 공통 부분을 찾는 메서드


    // 차이를 비교하여 출력하는 메서드
    public String diff(String origin, String source) {

        StringsComparator comparator = new StringsComparator(origin,source);


        StringBuilder res =new StringBuilder();
        comparator.getScript().visit(new CommandVisitor<Character>() {
            @Override
            public void visitInsertCommand(Character character) {
                res.append("\uD83D\uDE00").append(character);
            }

            @Override
            public void visitKeepCommand(Character character) {
                res.append(character);
            }

            @Override
            public void visitDeleteCommand(Character character) {
                res.append("\uD83D\uDE03").append(character);
            }
        });
        return res.toString();
    }
}
