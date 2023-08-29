package com.michalska.services;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.michalska.GitManagerService;
import com.michalska.dto.RepositoryDTO;
import com.michalska.exceptions.UserNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GitManagerServiceTest {

    @Autowired
    private GitManagerService gitManagerService;

    private final String GIT_GET_ALL_REPOSITORIES_URL = "/users/nikolMichalska/repos";
    private final String GIT_GET_ALL_REPOSITORIES_URL_WRONG_USER = "/users/nikolMichalska8/repos";
    private final String GIT_GET_ALL_BRANCHES_URL = "/repos/nikolMichalska/diceGameJS/branches";
    private final String GET_ALL_REPOSITORIES_RESPONSE = "Get_All_Repositories_response_200.json";
    private final String GET_ALL_BRANCHES_RESPONSE = "Get_All_Branches_response_200.json";
    private final String GET_ALL_REPOSITORIES_RESPONSE_404 = "Get_All_Repositories_response_404.json";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("api.github.repositories.url", () -> "http://localhost:8089/users/%s/repos");
        registry.add("api.github.branches.url", () -> "http://localhost:8089/repos/%s/%s/branches");
    }

    @Test
    public void getAllRepositoriesTest_returns200() throws IOException, InterruptedException {
        wireMockRule.stubFor(get(GIT_GET_ALL_REPOSITORIES_URL).willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json").withBodyFile(GET_ALL_REPOSITORIES_RESPONSE)));

        wireMockRule.stubFor(get(GIT_GET_ALL_BRANCHES_URL).willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json").withBodyFile(GET_ALL_BRANCHES_RESPONSE)));

        List<RepositoryDTO> repositoryDTO = gitManagerService.getUserGitHubRepositories("nikolMichalska");
        assertNotNull(repositoryDTO);
        assertEquals(1, repositoryDTO.size());
        assertNotNull(repositoryDTO.get(0).getBranches());
        assertEquals(1, repositoryDTO.get(0).getBranches().size());
    }

    @Test(expected = UserNotFoundException.class)
    public void getAllRepositoriesTest_returns400_user_not_found() {
        wireMockRule.stubFor(get(GIT_GET_ALL_REPOSITORIES_URL_WRONG_USER).willReturn(aResponse().withStatus(404)
                .withHeader("Content-Type", "application/json").withBodyFile(GET_ALL_REPOSITORIES_RESPONSE_404)));

        gitManagerService.getUserGitHubRepositories("nikolMichalska8");
    }

}
