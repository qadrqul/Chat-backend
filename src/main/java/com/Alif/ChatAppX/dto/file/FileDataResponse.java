package com.Alif.ChatAppX.dto.file;

import com.Alif.ChatAppX.entities.Message;
import com.Alif.ChatAppX.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
public class FileDataResponse {
    private Long id;

    private String name;
    private String type;
    //    @Lob
    @Column(name = "image_data")
    private byte[] fileData;

    @Column(name = "path")
    private String path;
}
