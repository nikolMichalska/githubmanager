package com.michalska.mappers;

import com.michalska.Branch;
import com.michalska.GitHubRepository;
import com.michalska.dto.BranchDTO;
import com.michalska.dto.RepositoryDTO;

import java.util.ArrayList;
import java.util.List;

public class RepositoryMapper {

    public RepositoryDTO mapToRepositoryDTO(GitHubRepository gitHubRepository, List<Branch> branchList) {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setName(gitHubRepository.getName());
        repositoryDTO.setLogin(gitHubRepository.getOwner().getLogin());
        List<BranchDTO> branchesDTO = new ArrayList<>();
        for (Branch branch : branchList) {
            branchesDTO.add(mapToBranchDTO(branch));
        }

        repositoryDTO.setBranches(branchesDTO);
        return repositoryDTO;
    }

    private BranchDTO mapToBranchDTO(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setName(branch.getName());
        if (branch.getCommit() != null) {
            branchDTO.setCommitSha(branch.getCommit().getSha());
        }
        return branchDTO;
    }
}
