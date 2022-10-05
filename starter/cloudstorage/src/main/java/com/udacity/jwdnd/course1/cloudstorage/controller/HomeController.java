package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private FilesService filesService;
    private NoteService noteService;
    private CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(UserService userService, FilesService filesService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.filesService = filesService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String Home(Authentication auth, Model model) {

        User user = userService.getUser(auth.getName());

        if (user != null) {

            List<File> fileList = filesService.getAllFilesByUserId(user.getUserId());
            model.addAttribute("fileList", fileList);
            List<Note> noteList = noteService.getNotesByUserId(user.getUserId());
            model.addAttribute("noteList", noteList);
            List<Credential> credentialList = credentialService.getAllCredentials(user.getUserId());
            model.addAttribute("credentialList", credentialList);
            model.addAttribute("encryptionService", encryptionService);

            return "home";
        }
        return "signup";
    }
}
