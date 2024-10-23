package com.example.hyupup_tool.domain.github.service;

import com.example.hyupup_tool.entity.dto.github.FileDiffCheckRequestDTO;
import com.example.hyupup_tool.service.GithubApiService;
import com.example.hyupup_tool.util.DiffFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@SpringBootTest
public class GithubServiceTest {

    @Autowired
    private GithubApiService githubApiService;

    @Autowired
    private DiffFormatter diffFormatter;

    private final String GITHUB_BASE64_STR_ORIGIN ="cGFja2FnZSBjb20uZGV2d2lraS5wcm9qZWN0LmFwcC53ZWIuYXBpLmRvbWFp\nbi5hbGFybTsKCmltcG9ydCBjb20uZGV2d2lraS5wcm9qZWN0LmFwcC53ZWIu\nYXBpLmRvbWFpbi5hbGFybS5kdG8uQ2hlY2tBbGxBbGFybXNSZXF1ZXN0Owpp\nbXBvcnQgY29tLmRldndpa2kucHJvamVjdC5hcHAud2ViLmFwaS5kb21haW4u\nYWxhcm0uZHRvLkNoZWNrU2luZ2xlQWxhcm1SZXF1ZXN0OwppbXBvcnQgY29t\nLmRldndpa2kucHJvamVjdC5hcHAud2ViLmFwaS5kb21haW4uYWxhcm0uZHRv\nLkxpc3RBcmFybVJlcXVlc3Q7CmltcG9ydCBjb20uZGV2d2lraS5wcm9qZWN0\nLmFwcC53ZWIuYXBpLmlkZW50aWZpZXIuSWRlbnRpZmllckhvbGRlcjsKaW1w\nb3J0IGNvbS5kZXZ3aWtpLnByb2plY3QuY29yZS5kb21haW4uYWxhcm0uQWxh\ncm07CmltcG9ydCBsb21ib2suUmVxdWlyZWRBcmdzQ29uc3RydWN0b3I7Cmlt\ncG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLmh0dHAuUmVzcG9uc2VFbnRpdHk7\nCmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90YXRp\nb24uUG9zdE1hcHBpbmc7CmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndl\nYi5iaW5kLmFubm90YXRpb24uUmVxdWVzdEJvZHk7CmltcG9ydCBvcmcuc3By\naW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90YXRpb24uUmVxdWVzdE1hcHBp\nbmc7CmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90\nYXRpb24uUmVzdENvbnRyb2xsZXI7CgppbXBvcnQgamF2YS51dGlsLkxpc3Q7\nCgpAUmVzdENvbnRyb2xsZXIKQFJlcXVlc3RNYXBwaW5nKCIvdjEvd2ViL2Fw\naS9hbGFybSIpCkBSZXF1aXJlZEFyZ3NDb25zdHJ1Y3RvcgpwdWJsaWMgY2xh\nc3MgQWxhcm1Db250cm9sbGVyIHsKCiAgICBwcml2YXRlIGZpbmFsIEFsYXJt\nQXBpU2VydmljZSBhbGFybUFwaVNlcnZpY2U7CiAgICBwcml2YXRlIGZpbmFs\nIElkZW50aWZpZXJIb2xkZXIgaWRlbnRpZmllckhvbGRlcjsKCgogICAgQFBv\nc3RNYXBwaW5nKCIvY2hlY2tBbGwiKQogICAgcHVibGljIFJlc3BvbnNlRW50\naXR5PFN0cmluZz4gY2hlY2tBbGxBbGFybShAUmVxdWVzdEJvZHkgQ2hlY2tB\nbGxBbGFybXNSZXF1ZXN0IHJlcSkgewogICAgICAgIGFsYXJtQXBpU2Vydmlj\nZS5jaGVja0FsbE5vdGlmaWNhdGlvbnMoaWRlbnRpZmllckhvbGRlci5nZXQo\nKSwgcmVxLnVzZXJJZCgpKTsKICAgICAgICByZXR1cm4gUmVzcG9uc2VFbnRp\ndHkub2soIuuqqOuToCDslYzrprzsnbQg7ZmV7J24IOyDge2DnOuhnCDrs4Dq\nsr3rkJjsl4jsirXri4jri6QuIik7CiAgICB9CgoKICAgIEBQb3N0TWFwcGlu\nZygiL2NoZWNrU2luZ2xlIikKICAgIHB1YmxpYyBSZXNwb25zZUVudGl0eTxT\ndHJpbmc+IGNoZWNrU2luZ2xlQWxhcm0oQFJlcXVlc3RCb2R5IENoZWNrU2lu\nZ2xlQWxhcm1SZXF1ZXN0IHJlcSkgewogICAgICAgIGFsYXJtQXBpU2Vydmlj\nZS5jaGVja1NpbmdsZU5vdGlmaWNhdGlvbihpZGVudGlmaWVySG9sZGVyLmdl\ndCgpLCByZXEudXNlcklkKCksIHJlcS5hbGFybUlkKCkpOwogICAgICAgIHJl\ndHVybiBSZXNwb25zZUVudGl0eS5vaygi7ZW064u5IOyVjOumvOydtCDtmZXs\nnbgg7IOB7YOc66GcIOuzgOqyveuQmOyXiOyKteuLiOuLpC4iKTsKCiAgICB9\nCgogICAgQFBvc3RNYXBwaW5nKCIvbGlzdCIpCiAgICBwdWJsaWMgUmVzcG9u\nc2VFbnRpdHk8TGlzdDxBbGFybT4+IGxpc3RBbGFybXMoQFJlcXVlc3RCb2R5\nIExpc3RBcmFybVJlcXVlc3QgcmVxKSB7CiAgICAgICAgTGlzdDxBbGFybT4g\nbm90aWZpY2F0aW9ucyA9IGFsYXJtQXBpU2VydmljZS5saXN0KGlkZW50aWZp\nZXJIb2xkZXIuZ2V0KCksIHJlcS51c2VySWQoKSk7CiAgICAgICAgcmV0dXJu\nIFJlc3BvbnNlRW50aXR5Lm9rKG5vdGlmaWNhdGlvbnMpOwogICAgfQp9\n";
    private final String GITHUB_BASE64_STR_BEFORE = "cGFja2FnZSBjb20uZGV2d2lraS5wcm9qZWN0LmFwcC53ZWIuYXBpLmRvbWFp\nbi5hbGFybTsKCmltcG9ydCBjb20uZGV2d2lraS5wcm9qZWN0LmFwcC53ZWIu\nYXBpLmRvbWFpbi5hbGFybS5kdG8uQ2hlY2tBbGxBbGFybXNSZXF1ZXN0Owpp\nbXBvcnQgY29tLmRldndpa2kucHJvamVjdC5hcHAud2ViLmFwaS5kb21haW4u\nYWxhcm0uZHRvLkNoZWNrU2luZ2xlQWxhcm1SZXF1ZXN0OwppbXBvcnQgY29t\nLmRldndpa2kucHJvamVjdC5hcHAud2ViLmFwaS5kb21haW4uYWxhcm0uZHRv\nLkxpc3RBcmFybVJlcXVlc3Q7CmltcG9ydCBjb20uZGV2d2lraS5wcm9qZWN0\nLmFwcC53ZWIuYXBpLmlkZW50aWZpZXIuSWRlbnRpZmllckhvbGRlcjsKaW1w\nb3J0IGNvbS5kZXZ3aWtpLnByb2plY3QuY29yZS5kb21haW4uYWxhcm0uQWxh\ncm07CmltcG9ydCBsb21ib2suUmVxdWlyZWRBcmdzQ29uc3RydWN0b3I7Cmlt\ncG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLmh0dHAuUmVzcG9uc2VFbnRpdHk7\nCmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90YXRp\nb24uUG9zdE1hcHBpbmc7CmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndl\nYi5iaW5kLmFubm90YXRpb24uUmVxdWVzdEJvZHk7CmltcG9ydCBvcmcuc3By\naW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90YXRpb24uUmVxdWVzdE1hcHBp\nbmc7CmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90\nYXRpb24uUmVzdENvbnRyb2xsZXI7CgppbXBvcnQgamF2YS51dGlsLkxpc3Q7\nCgpAUmVzdENvbnRyb2xsZXIKQFJlcXVlc3RNYXBwaW5nKCIvYWxhcm0iKQpA\nUmVxdWlyZWRBcmdzQ29uc3RydWN0b3IKcHVibGljIGNsYXNzIEFsYXJtQ29u\ndHJvbGxlciB7CgogICAgcHJpdmF0ZSBmaW5hbCBBbGFybUFwaVNlcnZpY2Ug\nYWxhcm1BcGlTZXJ2aWNlOwogICAgcHJpdmF0ZSBmaW5hbCBJZGVudGlmaWVy\nSG9sZGVyIGlkZW50aWZpZXJIb2xkZXI7CgoKICAgIEBQb3N0TWFwcGluZygi\nL2NoZWNrQWxsIikKICAgIHB1YmxpYyBSZXNwb25zZUVudGl0eTxTdHJpbmc+\nIGNoZWNrQWxsQWxhcm0oQFJlcXVlc3RCb2R5IENoZWNrQWxsQWxhcm1zUmVx\ndWVzdCByZXEpIHsKICAgICAgICBhbGFybUFwaVNlcnZpY2UuY2hlY2tBbGxO\nb3RpZmljYXRpb25zKGlkZW50aWZpZXJIb2xkZXIuZ2V0KCksIHJlcS51c2Vy\nSWQoKSk7CiAgICAgICAgcmV0dXJuIFJlc3BvbnNlRW50aXR5Lm9rKCLrqqjr\nk6Ag7JWM66a87J20IO2ZleyduCDsg4Htg5zroZwg67OA6rK965CY7JeI7Iq1\n64uI64ukLiIpOwogICAgfQoKCiAgICBAUG9zdE1hcHBpbmcoIi9jaGVja1Np\nbmdsZSIpCiAgICBwdWJsaWMgUmVzcG9uc2VFbnRpdHk8U3RyaW5nPiBjaGVj\na1NpbmdsZUFsYXJtKEBSZXF1ZXN0Qm9keSBDaGVja1NpbmdsZUFsYXJtUmVx\ndWVzdCByZXEpIHsKICAgICAgICBhbGFybUFwaVNlcnZpY2UuY2hlY2tTaW5n\nbGVOb3RpZmljYXRpb24oaWRlbnRpZmllckhvbGRlci5nZXQoKSwgcmVxLnVz\nZXJJZCgpLCByZXEuYWxhcm1JZCgpKTsKICAgICAgICByZXR1cm4gUmVzcG9u\nc2VFbnRpdHkub2soIu2VtOuLuSDslYzrprzsnbQg7ZmV7J24IOyDge2DnOuh\nnCDrs4Dqsr3rkJjsl4jsirXri4jri6QuIik7CgogICAgfQoKICAgIEBQb3N0\nTWFwcGluZygiL2xpc3QiKQogICAgcHVibGljIFJlc3BvbnNlRW50aXR5PExp\nc3Q8QWxhcm0+PiBsaXN0QWxhcm1zKEBSZXF1ZXN0Qm9keSBMaXN0QXJhcm1S\nZXF1ZXN0IHJlcSkgewogICAgICAgIExpc3Q8QWxhcm0+IG5vdGlmaWNhdGlv\nbnMgPSBhbGFybUFwaVNlcnZpY2UubGlzdChpZGVudGlmaWVySG9sZGVyLmdl\ndCgpLCByZXEudXNlcklkKCkpOwogICAgICAgIHJldHVybiBSZXNwb25zZUVu\ndGl0eS5vayhub3RpZmljYXRpb25zKTsKICAgIH0KfQ==\n";

    /*@Test
    void logging_origin_str_test(){
        List<String> originList = githubApiService.rawToArray(GITHUB_BASE64_STR);

        originList = originList
                .stream()
                .map(s->new String(Base64.getDecoder().decode(s), StandardCharsets.UTF_8))
                .toList();

        for(String str:originList){
            System.out.println(str);
        }
    }

    @Test
    void logging_after_patched_str_test(){
        var response = githubApiService.fileDiffCheck(new FileDiffCheckRequestDTO(GITHUB_BASE64_STR,GITHUB_PATCH));

        for(String str:response.fileResult()){
            System.out.println(str);
        }
    }

    @Test
    void diff_origin_and_patched(){
        List<String> originList = githubApiService.rawToArray(GITHUB_BASE64_STR);

        originList = originList
                .stream()
                .map(s->new String(Base64.getDecoder().decode(s), StandardCharsets.UTF_8))
                .toList();

        var response = githubApiService.fileDiffCheck(new FileDiffCheckRequestDTO(GITHUB_BASE64_STR,GITHUB_PATCH));
        boolean isDiff = false;
        if(originList.size() == response.fileResult().size()){
            for(int idx=0;idx<originList.size();idx++){
                if(!originList.get(idx).equals(response.fileResult().get(idx))){
                    System.out.println(idx + "번 문자열 다름");
                    isDiff = true;
                }
            }
            if(!isDiff){
                System.out.println("완전 같음");
            }
        }else{
            System.out.println("DIFFERENT");
        }
    }*/

    @Test
    void logging_file_str_diff(){
        String origin = githubApiService.rawToArray(GITHUB_BASE64_STR_ORIGIN);
        String before = githubApiService.rawToArray(GITHUB_BASE64_STR_BEFORE);

        origin = new String(Base64.getDecoder().decode(origin),StandardCharsets.UTF_8);
        before = new String(Base64.getDecoder().decode(before),StandardCharsets.UTF_8);
        String finalOrigin = diffFormatter.diff(origin,before);

        System.out.println(finalOrigin);
    }



}
