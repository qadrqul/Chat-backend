package com.Alif.ChatAppX.mapper;

import com.Alif.ChatAppX.dto.file.FileDataResponse;
import com.Alif.ChatAppX.entities.FileData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileDataMapperImpl implements FileDataMapper{
    @Override
    public FileDataResponse toDto(FileData fileData) {
        if (fileData==null){
            return null;
        }
        FileDataResponse response=new FileDataResponse();
        response.setPath(fileData.getPath());
        response.setId(fileData.getId());
        response.setName(fileData.getName());
        response.setType(fileData.getType());

        return response;
    }

    @Override
    public List<FileDataResponse> toDtos(List<FileData> files) {
        List<FileDataResponse> responses=new ArrayList<>();
        for (FileData file:files) {
            responses.add(toDto(file));
        }
        return responses;
    }
}
