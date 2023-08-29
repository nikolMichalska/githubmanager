package com.michalska.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchDTO {
    private String name;
    private String commitSha;
}
