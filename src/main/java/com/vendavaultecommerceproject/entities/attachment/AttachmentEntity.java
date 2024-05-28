package com.vendavaultecommerceproject.entities.attachment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@NoArgsConstructor
public class AttachmentEntity {
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid",strategy = "uuid2")
        private String id;
        private String fileType;
        private String fileName;
        @Lob
        @Column(columnDefinition = "LONGBLOB")
        private byte[] data;

        public AttachmentEntity(String fileType, String fileName, byte[] data) {
            this.fileType = fileType;
            this.fileName = fileName;
            this.data = data;
        }
    }


