package com.example.stc.model;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ItemsDTO {

    @NotNull
    @Size(max = 255)
    private String userEmail;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Long permissionGroupId;

    private MultipartFile file;

}
