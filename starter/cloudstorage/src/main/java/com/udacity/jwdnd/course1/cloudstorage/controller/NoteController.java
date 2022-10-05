package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.routes.Router;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addNote(Note note, Authentication auth) {
        if(note != null){
            User user = userService.getUser(auth.getName());
            note.setUserId(user.getUserId());
            noteService.addNote(note);
            return Router.SUCCESS;
        } else {
            return Router.ERROR;
        }
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") int noteId) {
        if(noteId > 0){
            noteService.deleteNote(noteId);
            return Router.SUCCESS;
        } else {
            return Router.ERROR;
        }
    }
}
