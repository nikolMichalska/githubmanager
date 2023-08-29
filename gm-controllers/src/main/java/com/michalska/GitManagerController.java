package com.michalska;

import com.michalska.dto.RepositoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gitmanager")
public class GitManagerController {

    private final GitManagerService gitManagerService;

    @Autowired
    public GitManagerController(GitManagerService gitManagerService) {
        this.gitManagerService = gitManagerService;
    }


    @GetMapping(path = "/get-all-repositories/{userName}")
    public List<RepositoryDTO> getAllRepositories(@PathVariable String userName) {
//        if(acceptHeader == null || acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)){
//            throw new NotAcceptableTypeException();
//        }
        return gitManagerService.getUserGitHubRepositories(userName);
    }


}


