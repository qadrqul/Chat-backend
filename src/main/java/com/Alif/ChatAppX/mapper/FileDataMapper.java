package com.Alif.ChatAppX.mapper;

import com.Alif.ChatAppX.dto.file.FileDataResponse;
import com.Alif.ChatAppX.entities.FileData;

import java.util.List;

public interface FileDataMapper {
    FileDataResponse toDto(FileData fileData);

    List<FileDataResponse> toDtos(List<FileData> files);
}
