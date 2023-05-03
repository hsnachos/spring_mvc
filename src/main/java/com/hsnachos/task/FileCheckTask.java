package com.hsnachos.task;

import com.hsnachos.controller.UploadController;
import com.hsnachos.domain.AttachFileDTO;
import com.hsnachos.domain.AttachVO;
import com.hsnachos.mapper.AttachMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.hsnachos.task
 * fileName       : FileCheckTask
 * author         : banghansol
 * date           : 2023/04/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/17        banghansol       최초 생성
 */
@Component
@Log4j
public class FileCheckTask {
    @Autowired
    private AttachMapper attachMapper;

    // 매 1분 0초 정각마다 file check가 뜰 수 있게 함
    // (초 분 시 일 월 요일 (연도))
    // @Scheduled(cron = "0 * * * * *")
    public void check() {
        log.warn("file check");
    }

    // 매월 2, 4주 목요일 02시 마다
    // 0 0 2 * * 4#7, 4#4

    //@Scheduled(cron = "0 0 2 * * *")
    public void checkFiles(){
        List<AttachVO> fileList = attachMapper.getOldFiles();
        log.info("====================== DB 상 목록 ======================");
        fileList.forEach(log::warn);
        log.info("총 갯수 : " + fileList.size());

        log.info("====================== 어제 날짜 ======================");
        log.info(getYesterday());

        log.info("====================== FS 목록 ======================");
        //Paths.get()
        List<File> files = fileList.stream().map(AttachFileDTO::getOriginFile).collect(Collectors.toList());
        fileList.stream().filter(vo -> vo.isImage()).forEach(vo-> files.add(vo.getThumbFile()));

        files.forEach(f -> log.info(f.exists()));

        log.info("====================== FS의 폴더 내 모든 파일 목록 ======================");
        List<File> realFiles = new ArrayList<>(Arrays.asList(new File(UploadController.getPATH(), getYesterday()).listFiles()));
        realFiles.forEach(log::info);

        log.info("====================== FS의 폴더 내 모든 파일 목록 중 DB와 일치하지 않은 파일 목록 ======================");
        realFiles = new ArrayList<>(Arrays.asList(new File(UploadController.getPATH(), getYesterday()).listFiles(f -> !files.contains(f))));
        realFiles.forEach(log::info);

        //realFiles.forEach(f -> f.delete());
    }

    private String getYesterday(){
        // getTime시 에포크 반환, ms 단위!
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date().getTime() - 1000 * 60 * 60 * 24);
    }
}
