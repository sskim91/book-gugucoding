package org.zerock.domain;

import lombok.Data;

@Data
public class AttachFileDTO {

    private String fileName;    //원본파일의이름
    private String uploadPath;  //업로드경로
    private String uuid;    //uuid값
    private boolean image;  //이미지 여부
}
