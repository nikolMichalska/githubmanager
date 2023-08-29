import com.michalska.Branch;
import com.michalska.Commit;
import com.michalska.GitHubRepository;
import com.michalska.Owner;
import com.michalska.dto.RepositoryDTO;
import com.michalska.mappers.RepositoryMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class RepositoryMapperTest {

    @Test
    public void mapToRepositoryDTOTest_commit_is_null() {
        GitHubRepository gitHubRepository = getGitHubRepository("Java application", getOwner(), false);

        RepositoryMapper repositoryMapper = new RepositoryMapper();
        RepositoryDTO repositoryDTO = repositoryMapper.mapToRepositoryDTO(gitHubRepository, getBranches(null));
        assertEquals(gitHubRepository.getName(), repositoryDTO.getName());
        assertEquals(gitHubRepository.getOwner().getLogin(), repositoryDTO.getLogin());
        assertNull(repositoryDTO.getBranches().get(0).getCommitSha());
    }

    @Test
    public void mapToRepositoryDTOTest() {
        GitHubRepository gitHubRepository = getGitHubRepository("Java application", getOwner(), false);

        RepositoryMapper repositoryMapper = new RepositoryMapper();
        RepositoryDTO repositoryDTO = repositoryMapper.mapToRepositoryDTO(gitHubRepository, getBranches(getCommit()));
        assertEquals(gitHubRepository.getName(), repositoryDTO.getName());
        assertEquals(gitHubRepository.getOwner().getLogin(), repositoryDTO.getLogin());
        assertEquals("7738hh", repositoryDTO.getBranches().get(0).getCommitSha());
    }

    private GitHubRepository getGitHubRepository(String name, Owner owner, boolean isFork) {
        GitHubRepository gitHubRepository = new GitHubRepository();
        gitHubRepository.setName(name);
        gitHubRepository.setOwner(owner);
        gitHubRepository.setFork(isFork);
        return gitHubRepository;
    }

    private Owner getOwner() {
        Owner owner = new Owner();
        owner.setLogin("matDavies");
        return owner;
    }

    private List<Branch> getBranches(Commit commit) {
        Branch branch = new Branch();
        branch.setName("Branch1");
        branch.setCommit(commit);
        return List.of(branch);
    }

    private Commit getCommit() {
        Commit commit = new Commit();
        commit.setSha("7738hh");
        return commit;
    }
}
