package com.michalska.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RepositoryDTO {
    private String name;
    private String login;
    private List<BranchDTO> branches;
}
