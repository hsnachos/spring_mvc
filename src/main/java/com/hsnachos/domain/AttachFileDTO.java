package com.hsnachos.domain;

import com.hsnachos.controller.UploadController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * packageName    : com.hsnachos.domain
 * fileName       : AttachFileDTO
 * author         : banghansol
 * date           : 2023/04/07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/07        banghansol       최초 생성
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachFileDTO {
    private String name;
    private String path;
    private String uuid;
    private boolean image;

    private int odr;

    public String getUrl() throws UnsupportedEncodingException {
        return UriComponentsBuilder.fromPath("")
                .queryParam("name", URLEncoder.encode(name, "utf-8"))
                .queryParam("path", path)
                .queryParam("uuid", uuid)
                .queryParam("image", image)
                .build().toString();
    }

    public File getOriginFile() {
        return new File(UploadController.getPATH(), path + File.separator + uuid + "_" + name);
    }

    public File getThumbFile() {
        return new File(UploadController.getPATH(), path + File.separator +"s_" + uuid + "_" + name);
    }
}
