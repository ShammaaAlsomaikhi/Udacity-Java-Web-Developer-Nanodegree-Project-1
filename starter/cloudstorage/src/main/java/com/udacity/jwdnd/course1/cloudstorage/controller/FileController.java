package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.routes.Router;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    private FilesService filesService;
    private UserService userService;

    public FileController(FilesService filesService, UserService userService) {
        this.filesService = filesService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication auth) throws Exception {
        if (fileUpload.isEmpty()) {
            return Router.ERROR;
        } else {
            User user = userService.getUser(auth.getName());
            if (filesService.checkIfFileNameAvailableForUser(fileUpload.getName(), user.getUserId())) {
                filesService.uploadFile(fileUpload, user);
                return Router.SUCCESS;
            } else {
                return Router.ERROR;
            }
        }
    }

    @GetMapping("/view")
    public ResponseEntity<byte[]> viewFile(@RequestParam("id") int fileId) {
        File file = filesService.getFileByFileId(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + file.getFileName())
                .body(file.getFileData());
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("id") int fileId) {
        if (fileId > 0) {
            filesService.deleteFile(fileId);
            return Router.SUCCESS;
        }
        return Router.ERROR;
    }
}
