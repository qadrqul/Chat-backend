package com.Alif.ChatAppX.dto.message;


import com.Alif.ChatAppX.dto.file.FileDataResponse;
import com.Alif.ChatAppX.entities.FileData;
import com.Alif.ChatAppX.entities.Message;
import com.Alif.ChatAppX.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
public class GroupRequest {
    private String adminNickname;
    private String groupName;
    private List<String> userNickNames;

    private List<MessageRequest> messages;

    private String createdTime;
    private String updatedTime;

    private Long groupImageId;
}
