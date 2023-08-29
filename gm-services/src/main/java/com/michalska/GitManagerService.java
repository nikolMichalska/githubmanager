package com.michalska;

import com.michalska.Branch;
import com.michalska.GitHubRepository;
import com.michalska.dto.RepositoryDTO;
import com.michalska.exceptions.UserNotFoundException;
import com.michalska.mappers.RepositoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitManagerService {

    private final RestTemplate client;

    private final String repositoriesURL;

    private final String branchesURL;

    private final RepositoryMapper repositoryMapper = new RepositoryMapper();

    @Autowired
    public GitManagerService(@Qualifier("githubClient") RestTemplate client,
                             @Value("${api.github.repositories.url}") String repositoriesURL,
                             @Value("${api.github.branches.url}") String branchesURL) {
        this.client = client;
        this.repositoriesURL = repositoriesURL;
        this.branchesURL = branchesURL;

    }

    public List<RepositoryDTO> getUserGitHubRepositories(String userName) {
        List<RepositoryDTO> repositoryDTOS = new ArrayList<>();
        List<GitHubRepository> repositories = getAllNonForkRepositoriesForUser(userName);
        repositories.forEach(repository -> {
            List<Branch> branches = getBranchesForRepository(repository.getName(), userName);
            RepositoryDTO repositoryDTO = repositoryMapper.mapToRepositoryDTO(repository, branches);
            repositoryDTOS.add(repositoryDTO);
        });
        return repositoryDTOS;
    }

    private List<Branch> getBranchesForRepository(String repositoryName, String userName) {
        Branch[] branches = client.getForObject(String.format(branchesURL, userName, repositoryName), Branch[].class);
        return branches == null ? List.of() : List.of(branches);
    }


    private List<GitHubRepository> getAllNonForkRepositoriesForUser(String userName) {
        GitHubRepository[] repositories;
        try {
            repositories = client.getForObject(String.format(repositoriesURL, userName), GitHubRepository[].class);

        } catch (RestClientException exception) {
            throw new UserNotFoundException();
        }
        if (repositories == null) {
            return List.of();
        }
        return Arrays.stream(repositories)
                .filter(repository -> !repository.isFork())
                .collect(Collectors.toList());
    }
}
