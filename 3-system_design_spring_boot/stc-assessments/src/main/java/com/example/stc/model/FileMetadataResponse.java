package com.example.stc.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileMetadataResponse {
    private String fileSpace;
    private String fileFolder;
    private String fileName;
    private String filePath;
    private String fileSize;
}
