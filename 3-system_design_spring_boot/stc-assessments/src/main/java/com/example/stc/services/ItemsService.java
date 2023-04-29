package com.example.stc.services;

import com.example.stc.domain.Files;
import com.example.stc.domain.Items;
import com.example.stc.domain.PermissionGroups;
import com.example.stc.domain.Permissions;
import com.example.stc.exceptions.customExceptions.AlreadyFoundException;
import com.example.stc.exceptions.customExceptions.InsufficientPrivilege;
import com.example.stc.exceptions.customExceptions.NotFoundException;
import com.example.stc.logger.STCLogger;
import com.example.stc.model.*;
import com.example.stc.repositories.FilesRepository;
import com.example.stc.repositories.ItemsRepository;
import com.example.stc.repositories.PermissionGroupsRepository;

import com.example.stc.repositories.PermissionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;


@Service
public class ItemsService {

    private final ItemsRepository itemsRepository;
    private final PermissionGroupsRepository permissionGroupsRepository;

    private final PermissionsRepository permissionsRepository;
    private final FilesRepository filesRepository;


    public ItemsService(ItemsRepository itemsRepository, PermissionGroupsRepository permissionGroupsRepository, PermissionsRepository permissionsRepository, FilesRepository filesRepository) {
        this.itemsRepository = itemsRepository;
        this.permissionGroupsRepository = permissionGroupsRepository;
        this.permissionsRepository = permissionsRepository;
        this.filesRepository = filesRepository;
    }

    public Long createSpace(ItemsDTO itemsDTO) throws NotFoundException, AlreadyFoundException {
        STCLogger.business.info("start createSpace method");
        Items items = new Items();
        items.setType(ItemType.Space);
        Items spaceItem = itemsRepository.findByNameAndType(itemsDTO.getName(), ItemType.Space);
        if (Objects.nonNull(spaceItem)) {
            throw new AlreadyFoundException("space with name " + itemsDTO.getName() + " is already created");
        }
        STCLogger.business.info("end createSpace method");
        return createItem(itemsDTO, items).getId();
    }

    public Long createFolder(String space, ItemsDTO itemsDTO) throws NotFoundException, InsufficientPrivilege, AlreadyFoundException {
        STCLogger.business.info("start createFolder method");
        Items items = new Items();
        items.setType(ItemType.Folder);
        Items spaceItem = itemsRepository.findByNameAndType(space, ItemType.Space);
        if (Objects.isNull(spaceItem)) {
            throw new NotFoundException("space with name " + space + " Not Found");
        }
        Items folderItem = itemsRepository.findByNameAndType(space + "\\" + itemsDTO.getName(), ItemType.Folder);
        if (Objects.nonNull(folderItem)) {
            throw new AlreadyFoundException("folder with name " + itemsDTO.getName() + " is already created");
        }
        Permissions permissions = permissionsRepository.findByUserEmail(itemsDTO.getUserEmail());
        if (Objects.isNull(permissions)) {
            throw new NotFoundException("user with email " + itemsDTO.getUserEmail() + " Not Found");
        }
        if (permissions.getPermissionLevel().equals(PermissionLevels.EDIT)) {
            itemsDTO.setName(space + "\\" + itemsDTO.getName());
            STCLogger.business.info("end createFolder method");
            return createItem(itemsDTO, items).getId();
        } else {
            throw new InsufficientPrivilege("InsufficientPrivilege contact your administrator");
        }
    }

    @Transactional
    public Long createFile(String space, String folder, ItemsDTO itemsDTO) throws NotFoundException, InsufficientPrivilege, IOException, AlreadyFoundException {
        STCLogger.business.info("start createFile method");
        Items items = new Items();
        items.setType(ItemType.File);
        Items spaceItem = itemsRepository.findByNameAndType(space, ItemType.Space);
        if (Objects.isNull(spaceItem)) {
            throw new NotFoundException("space with name " + space + " Not Found");
        }
        Items folderItem = itemsRepository.findByNameAndType(space + "\\" + folder, ItemType.Folder);
        if (Objects.isNull(folderItem)) {
            throw new NotFoundException("folder with name " + space + " Not Found");
        }
        Items fileItem = itemsRepository.findByNameAndType(space + "\\" + folder + "\\" + itemsDTO.getName(), ItemType.File);
        if (Objects.nonNull(fileItem)) {
            throw new AlreadyFoundException("file with name " + itemsDTO.getName() + " is already created");
        }
        Permissions permissions = permissionsRepository.findByUserEmail(itemsDTO.getUserEmail());
        if (Objects.isNull(permissions)) {
            throw new NotFoundException("user with email " + itemsDTO.getUserEmail() + " Not Found");
        }
        if (permissions.getPermissionLevel().equals(PermissionLevels.EDIT)) {
            itemsDTO.setName(space + "\\" + folder + "\\" + itemsDTO.getName());
            Items returnedItemAfterSaving = createItem(itemsDTO, items);
            uploadFile(itemsDTO.getFile(), returnedItemAfterSaving);
            STCLogger.business.info("end createFile method");
            return returnedItemAfterSaving.getId();
        } else {
            throw new InsufficientPrivilege("InsufficientPrivilege contact your administrator");
        }
    }

    public FileMetadataResponse getMetadataForaFile(Long id, String userEmail) throws NotFoundException, InsufficientPrivilege {
        STCLogger.business.info("start getMetadataForaFile method");
        FileMetadataDTO findFileMetadata = itemsRepository.findFileMetadata(id);
        if (Objects.isNull(findFileMetadata)) {
            throw new NotFoundException("file with id " + id + " Not Found");
        }
        Permissions permissions = permissionsRepository.findByUserEmail(userEmail);
        if (Objects.isNull(permissions)) {
            throw new NotFoundException("user with email " + userEmail + " Not Found");
        }
        Items file = itemsRepository.findById(id).get();
        if (!permissions.getGroupId().getId().equals(file.getPermissionGroupId().getId())) {
            throw new InsufficientPrivilege("InsufficientPrivilege contact your administrator");
        }
        STCLogger.business.info("end getMetadataForaFile method");
        return mapFileMetadataResponse(findFileMetadata);
    }


    public Files downloadFile(Long id, String userEmail) throws NotFoundException, InsufficientPrivilege {
        STCLogger.business.info("start downloadFile method");
        if (!itemsRepository.findById(id).isPresent()) {
            throw new NotFoundException("file with id " + id + " Not Found");
        }
        Permissions permissions = permissionsRepository.findByUserEmail(userEmail);
        if (Objects.isNull(permissions)) {
            throw new NotFoundException("user with email " + userEmail + " Not Found");
        }
        Items file = itemsRepository.findById(id).get();
        if (!permissions.getGroupId().getId().equals(file.getPermissionGroupId().getId())) {
            throw new InsufficientPrivilege("InsufficientPrivilege contact your administrator");
        }
        STCLogger.business.info("end downloadFile method");
        return file.getFilesCollection().get(0);
    }

    private FileMetadataResponse mapFileMetadataResponse(FileMetadataDTO findFileMetadata) {
        FileMetadataResponse fileMetadataResponse = new FileMetadataResponse();
        fileMetadataResponse.setFilePath(findFileMetadata.getName());
        fileMetadataResponse.setFileSize(findFileMetadata.getSize());
        String[] fileNameArray = findFileMetadata.getName().replace("\\", "#").split("#");
        fileMetadataResponse.setFileSpace(fileNameArray[0]);
        fileMetadataResponse.setFileFolder(fileNameArray[1]);
        fileMetadataResponse.setFileName(fileNameArray[2]);
        return fileMetadataResponse;
    }

    private void uploadFile(MultipartFile fileRequest, Items items) throws IOException {
        STCLogger.business.info("start uploadFile method");
        Files file = new Files();
        file.setItemId(items);
        file.setBinary(fileRequest.getBytes());
        filesRepository.save(file);
        STCLogger.business.info("end uploadFile method");
    }

    private Items createItem(ItemsDTO itemsDTO, Items items) throws NotFoundException {
        STCLogger.business.info("start createItem method");
        mapToEntity(itemsDTO, items);
        STCLogger.business.info("end createItem method");
        return itemsRepository.save(items);
    }

    private void mapToEntity(ItemsDTO itemsDTO, Items items) throws NotFoundException {
        STCLogger.business.info("start mapToEntity method");
        items.setName(itemsDTO.getName());
        PermissionGroups permissionGroup = itemsDTO.getPermissionGroupId() == null ? null : permissionGroupsRepository.findById(itemsDTO.getPermissionGroupId()).orElseThrow(() -> new NotFoundException("permissionGroup not found"));
        items.setPermissionGroupId(permissionGroup);
        STCLogger.business.info("end mapToEntity method");
    }

}
