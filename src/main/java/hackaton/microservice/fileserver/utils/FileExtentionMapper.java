package hackaton.microservice.fileserver.utils;


import org.springframework.http.MediaType;

public class FileExtentionMapper {

    public static MediaType getMediaTypeForFileName(String fileName) {
        String fileExtension = getFileExtension(fileName);
        if (fileExtension != null) {
            switch (fileExtension.toLowerCase()) {
                case "pdf":
                    return MediaType.APPLICATION_PDF;
                case "jpg":
                case "jpeg":
                    return MediaType.IMAGE_JPEG;
                case "png":
                    return MediaType.IMAGE_PNG;
                case "gif":
                    return MediaType.IMAGE_GIF;
                default:
                    // If the file extension is not recognized, return a generic binary stream
                    return MediaType.APPLICATION_OCTET_STREAM;
            }
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return null;
    }
}