package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {

    private int fileId;
    private String fileName;
    private String contentType;
    private int userId;
    private String fileSize;
    private byte[] fileData;

}
