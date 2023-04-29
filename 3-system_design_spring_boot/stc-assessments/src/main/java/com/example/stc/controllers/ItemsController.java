package com.example.stc.controllers;

import com.example.stc.domain.Files;
import com.example.stc.exceptions.customExceptions.AlreadyFoundException;
import com.example.stc.exceptions.customExceptions.InsufficientPrivilege;
import com.example.stc.exceptions.customExceptions.NotFoundException;
import com.example.stc.logger.STCLogger;
import com.example.stc.model.FileMetadataResponse;
import com.example.stc.model.ItemsDTO;
import com.example.stc.services.ItemsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemsController {
    private final ItemsService itemsService;

    public ItemsController(final ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostMapping(value = "/space")
    public ResponseEntity<Long> createItemsSpace(@RequestBody @Valid ItemsDTO itemsDTO) throws NotFoundException, AlreadyFoundException {
        STCLogger.business.info("start receiving create space request");
        return new ResponseEntity<>(itemsService.createSpace(itemsDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/folder/{space}")
    public ResponseEntity<Long> createItemsFolder(@PathVariable @Valid @NotNull String space, @RequestBody @Valid ItemsDTO itemsDTO) throws NotFoundException, InsufficientPrivilege, AlreadyFoundException {
        STCLogger.business.info("start receiving create folder request under space {}", space);
        return new ResponseEntity<>(itemsService.createFolder(space, itemsDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/file/{space}/{folder}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> createItemsFile(@PathVariable @Valid @NotNull String space, @PathVariable @Valid @NotNull String folder, @ModelAttribute @Valid ItemsDTO itemsDTO) throws NotFoundException, InsufficientPrivilege, IOException, AlreadyFoundException {
        STCLogger.business.info("start receiving create file request under space {} and folder {}", space, folder);
        return new ResponseEntity<>(itemsService.createFile(space, folder, itemsDTO), HttpStatus.CREATED);
    }

    @GetMapping("/file-metadata/{id}")
    public ResponseEntity<FileMetadataResponse> getFileMetaData(@PathVariable Long id, @RequestHeader("userEmail") @Valid @NotNull String userEmail) throws NotFoundException, InsufficientPrivilege {
        STCLogger.business.info("start receiving get file metadata request with id " + id);
        return ResponseEntity.ok().body(itemsService.getMetadataForaFile(id, userEmail));
    }

    @GetMapping("/file-download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id, @RequestHeader("userEmail") @Valid @NotNull String userEmail) throws NotFoundException, InsufficientPrivilege {
        STCLogger.business.info("start receiving download file request with id " + id);
        Files file = itemsService.downloadFile(id, userEmail);
        String[] fileNameArray = file.getItemId().getName().replace("\\", "#").split("#");
        String fileName = file.getItemId().getName().replace("\\", "#").split("#")[fileNameArray.length - 1];
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(file.getBinary());
    }
}

