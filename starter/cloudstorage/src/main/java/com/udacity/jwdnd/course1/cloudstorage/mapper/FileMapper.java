package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    int uploadFile(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllFiles(int userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileByFileId(int fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(int fileId);

}
