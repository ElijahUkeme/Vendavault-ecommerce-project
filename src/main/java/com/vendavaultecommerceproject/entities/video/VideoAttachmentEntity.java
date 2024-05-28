package com.vendavaultecommerceproject.entities.video;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoAttachmentEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    private String fileType;
    private String fileName;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    public VideoAttachmentEntity(String fileType, String fileName, byte[] data) {
        this.fileType = fileType;
        this.fileName = fileName;
        this.data = data;
    }
}
