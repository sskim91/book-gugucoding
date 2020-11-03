package org.zerock.domain;

import lombok.Data;

/**
 * @author sskim
 */
@Data
public class BoardAttachVO {

    private String uuid;
    private String uploadPath;
    private String fileName;
    private boolean fileType;

    private Long bno;
}
