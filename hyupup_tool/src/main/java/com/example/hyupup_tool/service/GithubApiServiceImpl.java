package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.GetBranchDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetBranchListResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitDetailsResponseDTO;
import com.example.hyupup_tool.entity.dto.GetCommitListResponseDTO;
import com.example.hyupup_tool.entity.dto.github.*;
import com.example.hyupup_tool.entity.dto.util.GitGraphIterator;
import com.example.hyupup_tool.entity.dto.util.Parents;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.externalapi.BranchManager;
import com.example.hyupup_tool.externalapi.CommitManager;
import com.example.hyupup_tool.externalapi.UserManager;
import com.example.hyupup_tool.repository.RoomRepository;
import com.example.hyupup_tool.util.DiffFormatter;
import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GithubApiServiceImpl implements GithubApiService{

    private final BranchManager branchManager;
    private final CommitManager commitManager;
    private final UserManager userManager;
    private final DiffFormatter diffFormatter;
    private final RoomRepository roomRepository;
    private final RedisTemplate<String,String> redisTemplate;


    private final String[] colors = {"red","orange","yellow","green","blue","navy","white"};
    public ResponseEntity<byte[]> getUserInfo(){
        return userManager.getUserInfo(new GithubUserInfoRequest());
    }

    public ResponseEntity<byte[]> gerBranchList(String owner, String repo) {
        return branchManager.getBranchList(owner,repo);
    }

    public ResponseEntity<byte[]> getBranchDetail(String owner, String repo, String branch) {
        return branchManager.getBranchDetail(owner,repo,branch);
    }

    public ResponseEntity<byte[]> getCommitList(String owner, String repo, String path) {
        return commitManager.getCommitList(owner,repo,path);
    }

    public ResponseEntity<byte[]> getCommitDetail(String owner, String repo, String sha,String refSha) {
        return commitManager.getCommitDetail(owner,repo,sha,refSha);
    }

    @Override
    public ResponseEntity<byte[]> getFileDiff(String owner, String repo, String path,String ref) {
        return commitManager.getFileDiff(owner,repo,path,ref);
    }

    @Override
    public FileDiffCheckResponseDTO fileDiffCheck(FileDiffCheckRequestDTO request) {

        String origin = rawToArray(request.origin());
        String before = rawToArray(request.before());

        origin = new String(Base64.getDecoder().decode(origin),StandardCharsets.UTF_8);
        before = new String(Base64.getDecoder().decode(before),StandardCharsets.UTF_8);
        String fileOrigin = diffFormatter.diff(origin,before);
        return new FileDiffCheckResponseDTO(fileOrigin);


    }

    @Override
    public SyncGitGraphResponse syncGitGraph(SyncGitGraphRequest request) {
        var roomEntity = roomRepository.findById(request.roomId())
                .orElseThrow(()-> new BadRequestException("Room not found"));

        /*
        * Git Graph를 그리는 기본적인 아이디어
        * 1. get branch로 모든 브런치를 따오기
        * 2. 모든 브런치에 detail commit을 따와서 데이터를 가져오기
        * 3. key: sha, value : [branch][commit name][parent commit sha]로 이루어진 데이터셋을
        * 레디스에 저장
        * 4. key: branch, value : color을 저장하는 데이터를 레디스에 저장
        * 5. A커밋의 parent가 두 개 이상이면? <- 브런치 병합 지점
        * 6. A 커밋과 B 커밋의 parent가 하나의 커밋이다? <- 브런치 분리 지점
        * 7. 1번 브런치를 어떻게 잡을 건가? <- 제일 최근 커밋부터 늘어나는 브런치에 따라 카운트를 달면 될 듯?
        *
        * */

        List<GetBranchListResponseDTO> branchList = branchManager.getBranchListWithDto(roomEntity.getOwner(),roomEntity.getRepository());

        setBranchColor(branchList,roomEntity.getOwner(),roomEntity.getRepository());
        for(GetBranchListResponseDTO dto:branchList){
            List<GetCommitDetailsResponseDTO> commitList = commitManager.getCommitDetailsWithDto(roomEntity.getOwner(),roomEntity.getRepository(),null, dto.getName());
            setCommitData(commitList,dto.getName());
        }

        return new SyncGitGraphResponse(
                drawGitGraph(
                        commitManager.getRecentCommit(
                                roomEntity.getOwner(),roomEntity.getRepository()
                        ),
                        roomEntity.getOwner(),roomEntity.getRepository())
        );
    }

    @Override
    public GetGitGraphResponse getGitGraph(GetGitGraphRequest request) {
        var roomEntity = roomRepository.findById(request.roomId())
                .orElseThrow(()-> new BadRequestException("Room not found"));

        return new GetGitGraphResponse(
                drawGitGraph(
                        commitManager.getRecentCommit(
                                roomEntity.getOwner(),roomEntity.getRepository()
                        ),roomEntity.getOwner(),roomEntity.getRepository()
                )
        );
    }

    public String rawToArray(String str){
        StringBuilder sb =new StringBuilder();
        String[] strs = str.split("\n");
        for(String data:strs){
            sb.append(data);
        }
        return sb.toString();
    }

    @Override
    public List<GitGraphDto> drawGitGraph(String recentCommitSha,String owner,String repo) {
        List<GitGraphDto> gitGraphDtoList = new ArrayList<>();
        Queue<GitGraphIterator> gitGraphIterators = new LinkedList<>();
        Map<String,String> branchColorStore = new HashMap<>();
        Map<String,String> commitSavePointStore = new HashMap<>();
        Map<String,Long> branchLineNumStore = new HashMap<>();
        long counter = 0;
        long lineCounter = 0;
        gitGraphIterators.add(findGitGraph(recentCommitSha));
        while(!gitGraphIterators.isEmpty()){
            var iter = gitGraphIterators.poll();

            String branchColor = branchColorStore.get(iter.getBranch());
            Long branchLine = branchLineNumStore.get(iter.getBranch());
            if(branchColor == null){
                branchColor = redisTemplate.opsForValue().get(owner+":"+repo+":color:");
                branchColorStore.put(iter.getBranch(),branchColor);
            }
            if(branchLine == null){
                branchLine = lineCounter;
                branchLineNumStore.put(iter.getBranch(),lineCounter);
                lineCounter++;
            }
            commitSavePointStore.put(iter.getSha(),iter.getBranch());

            GitGraphDto gitGraphDto = new GitGraphDto(
                    counter++,
                    iter.getBranch(),
                    branchColor,
                    branchLine,
                    iter.getMessage(),
                    iter.getSha()
            );

            gitGraphDtoList.add(gitGraphDto);

            //부모 커밋을 큐에 삽입
            for(var parents:iter.getParentsList()){
                var next = findGitGraph(parents.getSha());
                if(next == null)
                    continue;
                gitGraphIterators.add(next);
            }


        }

        return gitGraphDtoList;
    }

    @Override
    public void setRecentCommit(List<GetCommitDetailsResponseDTO> commitList, String branch) {

    }

    @Override
    public void setBranchColor(List<GetBranchListResponseDTO> branchList,String owner,String repo) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        for(int idx=0;idx<branchList.size();idx++){
            String sb = owner +
                    ":" +
                    repo +
                    ":color:";
            valueOperations.set(sb,colors[idx%7]);
        }

    }

    @Override
    public void setCommitData(List<GetCommitDetailsResponseDTO> commitList, String branch) {
        for(GetCommitDetailsResponseDTO dto:commitList){
            //새로고침 시 이전 캐시 삭제
            redisTemplate.delete(dto.getSha());


            ListOperations<String,String> listOps = redisTemplate.opsForList();
            //브런치
            listOps.rightPush(dto.getSha(),branch);
            //메세지
            listOps.rightPush(dto.getSha(),dto.getCommit().getMessage());
            for(Parents parents:dto.getParents()){
                //부모 커밋 sha
                listOps.rightPush(dto.getSha(),parents.getSha());
            }

        }
    }

    @Override
    public GitGraphIterator findGitGraph(String currentCommitSha) {
        List<String> gitGraphList = redisTemplate.opsForList().range(currentCommitSha,0,-1);
        GitGraphIterator gitGraphIterator = new GitGraphIterator();
        if(gitGraphList.isEmpty()){
            return null;
            //이 경우는 Initial Commit
        }else if(gitGraphList.size() == 2){
            gitGraphIterator.setSha(currentCommitSha);
            gitGraphIterator.setBranch(gitGraphList.get(0));
            gitGraphIterator.setMessage(gitGraphList.get(1));
            return gitGraphIterator;
        }else{
            //이니셜 커밋 이외의 경우
            gitGraphIterator.setSha(currentCommitSha);
            gitGraphIterator.setBranch(gitGraphList.get(0));
            gitGraphIterator.setMessage(gitGraphList.get(1));
            List<Parents> parentsList = new ArrayList<>();
            for(int idx=2;idx<gitGraphList.size();idx++){
                Parents parents = new Parents(gitGraphList.get(idx),null,null);
                parentsList.add(parents);
            }
            gitGraphIterator.setParentsList(parentsList);
            return gitGraphIterator;
        }



    }


}
