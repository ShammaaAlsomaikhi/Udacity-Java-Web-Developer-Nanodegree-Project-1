package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Note note) {
        if (noteMapper.getNoteByNoteId(note.getNoteId()) == null){
        noteMapper.addNote(note);
        } else {
            updateNote(note);
        }
    }

    public List<Note> getNotesByUserId(int userId) {
        return noteMapper.getNotesByUserId(userId);
    }

    public void deleteNote(int noteId) {
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(Note note) {
        noteMapper.editNote(note);
    }

}
