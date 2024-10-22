package com.example.hyupup_tool.domain.github.service;

import com.example.hyupup_tool.entity.dto.github.FileDiffCheckRequestDTO;
import com.example.hyupup_tool.service.GithubApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@SpringBootTest
public class GithubServiceTest {

    @Autowired
    private GithubApiService githubApiService;

    private final String GITHUB_BASE64_STR ="cGFja2FnZSBjb20uZGV2d2lraS5wcm9qZWN0LmFwcC53ZWIuYXBpLmRvbWFp\nbi5hbGFybTsKCmltcG9ydCBjb20uZGV2d2lraS5wcm9qZWN0LmFwcC53ZWIu\nYXBpLmRvbWFpbi5hbGFybS5kdG8uQ2hlY2tBbGxBbGFybXNSZXF1ZXN0Owpp\nbXBvcnQgY29tLmRldndpa2kucHJvamVjdC5hcHAud2ViLmFwaS5kb21haW4u\nYWxhcm0uZHRvLkNoZWNrU2luZ2xlQWxhcm1SZXF1ZXN0OwppbXBvcnQgY29t\nLmRldndpa2kucHJvamVjdC5hcHAud2ViLmFwaS5kb21haW4uYWxhcm0uZHRv\nLkxpc3RBcmFybVJlcXVlc3Q7CmltcG9ydCBjb20uZGV2d2lraS5wcm9qZWN0\nLmFwcC53ZWIuYXBpLmlkZW50aWZpZXIuSWRlbnRpZmllckhvbGRlcjsKaW1w\nb3J0IGNvbS5kZXZ3aWtpLnByb2plY3QuY29yZS5kb21haW4uYWxhcm0uQWxh\ncm07CmltcG9ydCBsb21ib2suUmVxdWlyZWRBcmdzQ29uc3RydWN0b3I7Cmlt\ncG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLmh0dHAuUmVzcG9uc2VFbnRpdHk7\nCmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90YXRp\nb24uUG9zdE1hcHBpbmc7CmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndl\nYi5iaW5kLmFubm90YXRpb24uUmVxdWVzdEJvZHk7CmltcG9ydCBvcmcuc3By\naW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90YXRpb24uUmVxdWVzdE1hcHBp\nbmc7CmltcG9ydCBvcmcuc3ByaW5nZnJhbWV3b3JrLndlYi5iaW5kLmFubm90\nYXRpb24uUmVzdENvbnRyb2xsZXI7CgppbXBvcnQgamF2YS51dGlsLkxpc3Q7\nCgpAUmVzdENvbnRyb2xsZXIKQFJlcXVlc3RNYXBwaW5nKCIvdjEvd2ViL2Fw\naS9hbGFybSIpCkBSZXF1aXJlZEFyZ3NDb25zdHJ1Y3RvcgpwdWJsaWMgY2xh\nc3MgQWxhcm1Db250cm9sbGVyIHsKCiAgICBwcml2YXRlIGZpbmFsIEFsYXJt\nQXBpU2VydmljZSBhbGFybUFwaVNlcnZpY2U7CiAgICBwcml2YXRlIGZpbmFs\nIElkZW50aWZpZXJIb2xkZXIgaWRlbnRpZmllckhvbGRlcjsKCgogICAgQFBv\nc3RNYXBwaW5nKCIvY2hlY2tBbGwiKQogICAgcHVibGljIFJlc3BvbnNlRW50\naXR5PFN0cmluZz4gY2hlY2tBbGxBbGFybShAUmVxdWVzdEJvZHkgQ2hlY2tB\nbGxBbGFybXNSZXF1ZXN0IHJlcSkgewogICAgICAgIGFsYXJtQXBpU2Vydmlj\nZS5jaGVja0FsbE5vdGlmaWNhdGlvbnMoaWRlbnRpZmllckhvbGRlci5nZXQo\nKSwgcmVxLnVzZXJJZCgpKTsKICAgICAgICByZXR1cm4gUmVzcG9uc2VFbnRp\ndHkub2soIuuqqOuToCDslYzrprzsnbQg7ZmV7J24IOyDge2DnOuhnCDrs4Dq\nsr3rkJjsl4jsirXri4jri6QuIik7CiAgICB9CgoKICAgIEBQb3N0TWFwcGlu\nZygiL2NoZWNrU2luZ2xlIikKICAgIHB1YmxpYyBSZXNwb25zZUVudGl0eTxT\ndHJpbmc+IGNoZWNrU2luZ2xlQWxhcm0oQFJlcXVlc3RCb2R5IENoZWNrU2lu\nZ2xlQWxhcm1SZXF1ZXN0IHJlcSkgewogICAgICAgIGFsYXJtQXBpU2Vydmlj\nZS5jaGVja1NpbmdsZU5vdGlmaWNhdGlvbihpZGVudGlmaWVySG9sZGVyLmdl\ndCgpLCByZXEudXNlcklkKCksIHJlcS5hbGFybUlkKCkpOwogICAgICAgIHJl\ndHVybiBSZXNwb25zZUVudGl0eS5vaygi7ZW064u5IOyVjOumvOydtCDtmZXs\nnbgg7IOB7YOc66GcIOuzgOqyveuQmOyXiOyKteuLiOuLpC4iKTsKCiAgICB9\nCgogICAgQFBvc3RNYXBwaW5nKCIvbGlzdCIpCiAgICBwdWJsaWMgUmVzcG9u\nc2VFbnRpdHk8TGlzdDxBbGFybT4+IGxpc3RBbGFybXMoQFJlcXVlc3RCb2R5\nIExpc3RBcmFybVJlcXVlc3QgcmVxKSB7CiAgICAgICAgTGlzdDxBbGFybT4g\nbm90aWZpY2F0aW9ucyA9IGFsYXJtQXBpU2VydmljZS5saXN0KGlkZW50aWZp\nZXJIb2xkZXIuZ2V0KCksIHJlcS51c2VySWQoKSk7CiAgICAgICAgcmV0dXJu\nIFJlc3BvbnNlRW50aXR5Lm9rKG5vdGlmaWNhdGlvbnMpOwogICAgfQp9\n";
    private final String GITHUB_PATCH = "@@ -15,7 +15,7 @@\n import java.util.List;\n \n @RestController\n-@RequestMapping(\"/alarm\")\n+@RequestMapping(\"/v1/web/api/alarm\")\n @RequiredArgsConstructor\n public class AlarmController {\n ";

    @Test
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
    }



}
