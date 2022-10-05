package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FilesService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FilesService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public void uploadFile(MultipartFile multipartFile, User user) throws Exception {
        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setFileSize(String.valueOf(multipartFile.getSize()));
        file.setFileData(multipartFile.getBytes());
        file.setContentType( multipartFile.getContentType());
        file.setUserId(user.getUserId());

        fileMapper.uploadFile(file);
    }

    public boolean checkIfFileNameAvailableForUser(String fileName, int userId){
        List<File> files = fileMapper.getAllFiles(userId);
        for (int i = 0; i < files.size(); i++) {
            if(files.get(i).getFileName() == fileName)
                return false;
        }
        return true;

    }

    public void deleteFile(int fileId) {fileMapper.deleteFile(fileId);}

    public File getFileByFileId(int fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public List<File> getAllFilesByUserId(int userId){return fileMapper.getAllFiles(userId);}

}
