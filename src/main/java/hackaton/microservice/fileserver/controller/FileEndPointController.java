package hackaton.microservice.fileserver.controller;

import hackaton.microservice.fileserver.utils.FileDownloadUtil;
import hackaton.microservice.fileserver.utils.FileExtentionMapper;
import hackaton.microservice.fileserver.utils.FileUtils;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/storage")
public class FileEndPointController {

    @PostMapping("/upload")
    public String saveFile(@RequestParam MultipartFile file){
        return FileUtils.save(file);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String filePath) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String fName = resource.getFilename();
        MediaType mediaType = FileExtentionMapper.getMediaTypeForFileName(filePath);
        String contentType = mediaType.toString();
        String headerValue = "attachment; filename=\"" + fName + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }


}
