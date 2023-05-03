package com.hsnachos.controller;

import com.hsnachos.domain.AttachFileDTO;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * packageName    : com.hsnachos.controller
 * fileName       : UploadController
 * author         : banghansol
 * date           : 2023/04/07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/07        banghansol       최초 생성
 */
@Controller
@Log4j
public class UploadController {
    @Getter
    private static final String PATH = "/Users/banghansol/file/upload/";

    @GetMapping("upload")
    public void upload(){
        log.info("upload Get");
    }

    @GetMapping("uploadAjax")
    public void uploadAjax(){
        log.info("upload Ajax Get");
    }

    // 파라미터의 이름은 view(upload.jsp에서 보내주는 파라미터의 name과 일치해야한다.
    @PostMapping("upload")
    public void upload(MultipartFile[] files) throws IOException {
        log.info("upload post");

        for(MultipartFile m : files){
            log.info("원본 파일명" + m.getOriginalFilename());
            log.info("파일 사이즈" + m.getSize());

            // 실제 스트림 전송
            //m.transferTo(new File("c:/upload", m.getOriginalFilename()));
            m.transferTo(new File(PATH, m.getOriginalFilename()));
        }
    }

    @PostMapping(value = "uploadAjax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttachFileDTO> uploadAjaxPost(MultipartFile[] files) {
        log.info("upload Ajax Post");
        List<AttachFileDTO> list = new ArrayList<>();

        File uploadPath = new File(PATH, getFolder());
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        for(MultipartFile m : files){
            log.info("원본 파일명" + m.getOriginalFilename());
            log.info("파일 사이즈" + m.getSize());

            // 실제 스트림 전송
            File f = null;
            String uuidStr = UUID.randomUUID().toString();
            String tName = uuidStr + "_" + m.getOriginalFilename();
            f = new File(uploadPath, tName);
            boolean image = false;

            try {

                image = isImage(f);
                // m.transferTo(new File(uploadPath, uuidStr + "_" +  m.getOriginalFilename()));
                 m.transferTo(f);


                if(image){
                    // 섬네일 처리
                    File file2 = new File(uploadPath + "/" + "s_" + tName);
                    Thumbnails.of(f).crop(Positions.CENTER).size(200, 200).toFile(file2);
                }

            }catch (UnsupportedFormatException e){
                image = false;
            } catch (IOException e) {
                e.printStackTrace();
            }

            AttachFileDTO  dto = new AttachFileDTO();
            dto.setUuid(uuidStr);
            dto.setPath(getFolder());
            dto.setImage(image);
            dto.setName(m.getOriginalFilename());

            list.add(dto);
        }
        return list;
    }

    @GetMapping("deleteFile") @ResponseBody
    public String deleteFile(AttachFileDTO dto) {
        log.info(dto);
        String s = PATH + "/" + dto.getPath() + "/" + dto.getUuid() + "_" + dto.getName();

        File file = new File(s);
        file.delete();
        if(dto.isImage()){
            s = PATH + "/" + dto.getPath() + "/s_" + dto.getUuid() + "_" + dto.getName();
            file = new File(s);
            file.delete();
        }

        return "deleted";
    }

    @GetMapping(value = "display") @ResponseBody
    public ResponseEntity<byte[]> getFile(AttachFileDTO dto, Boolean thumb) {
        // fileName : path + uuid + name

        log.info(dto);
        String s = PATH + "/" + dto.getPath() + "/" + (thumb != null && thumb ? "s_" : "") + dto.getUuid() + "_" + dto.getName();
        File file = new File(s);
        log.info("exist() : " + file.exists());
        log.info(thumb);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @GetMapping(value = "download") @ResponseBody
    public ResponseEntity<byte[]> download(AttachFileDTO dto) {
        // fileName : path + uuid + name

        log.info(dto);
        String s = PATH + "/" + dto.getPath() + "/"  + dto.getUuid() + "_" + dto.getName();
        File file = new File(s);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            headers.add("Content-Disposition", "attachment; filename="
                    + new String(dto.getName().getBytes("utf-8"), "iso-8859-1"));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @GetMapping("list") @ResponseBody
    public List<String> test() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        return list;
    }

    @GetMapping(value = "dto", produces = MediaType.APPLICATION_JSON_VALUE) @ResponseBody
    public AttachFileDTO getDto(){
        AttachFileDTO dto = new AttachFileDTO();
        dto.setImage(true);
        dto.setName("파일명.png");

        return dto;
    }

    private String getFolder() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    // 해당 파일에 대한 mime type stream 확인
    private boolean isImage(File file) throws IOException {
        List<String> excludes = Arrays.asList("ico", "webp");
        int idx = file.toString().lastIndexOf(".");
        if(idx == -1){
            return false;
        }

        String ext = file.toString().substring(idx + 1);
        if(excludes.contains(ext)){
            return false;
        }

        String mime = Files.probeContentType(file.toPath());
        //return Files.probeContentType(file.toPath()).startsWith("image");

        // mime image/png image/jpeg image/x-icon
        // webp null
        return mime != null && mime.startsWith("image");
    }

    @PostMapping("/ckImage") @ResponseBody
    public Map<String, Object> uploadAjax(MultipartHttpServletRequest request) throws IOException {
        log.info(request);
        MultipartFile multipartFile = request.getFile("upload");

        String origin = multipartFile.getOriginalFilename();
        String ext = origin.substring(origin.indexOf("."));

        String uuidStr = UUID.randomUUID() + ext;
        String tName = uuidStr + "_" + origin;

        File f = new File(PATH, tName);
        AttachFileDTO dto = new AttachFileDTO();
        dto.setUuid(uuidStr);
        dto.setPath("");
        dto.setName (origin);

        multipartFile.transferTo(f);

        Map<String, Object> map = new HashMap<>();

        map.put("uploaded", true);
        map.put("url", "/display" + dto.getUrl());
        return map;
    }

}
