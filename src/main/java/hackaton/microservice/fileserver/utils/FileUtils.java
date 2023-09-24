package hackaton.microservice.fileserver.utils;

import hackaton.microservice.fileserver.constant.FileConstant;
import io.micrometer.common.util.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

    private static final String defaultPath = FileConstant.DEFAULT_PATH;

    public static String save(MultipartFile file){

        String resultPath = "";

        try {

            if(!StringUtils.isBlank(file.getOriginalFilename())) {
                File directory = new File(defaultPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = file.getOriginalFilename();

                resultPath = UUID.randomUUID() + fileName;

                File destFile = new File(defaultPath + "/" + resultPath);
                file.transferTo(destFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultPath;
    }
}
