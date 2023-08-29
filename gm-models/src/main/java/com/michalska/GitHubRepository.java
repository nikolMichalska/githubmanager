package com.michalska;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepository {
    private String name;
    private Owner owner;
    private boolean isFork;
}
