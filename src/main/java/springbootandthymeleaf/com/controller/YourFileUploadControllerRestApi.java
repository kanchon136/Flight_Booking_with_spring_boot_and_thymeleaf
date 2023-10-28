package springbootandthymeleaf.com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import springbootandthymeleaf.com.entity.FileEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;


@RestController
@RequestMapping("/api")
public class YourFileUploadControllerRestApi {

    private static final String uploadDirectory = "/your/upload/directory/";

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("distIds") String[] airportIds,
                                            @RequestParam("files") List<MultipartFile> files) {

        try {
            int index1 = 0;

            for (String airportId : airportIds) {
                FileEntity fileEntity = new FileEntity();

                List<MultipartFile> filesByAirportId = files.stream()
                        .filter(file -> file.getName().equals("files_" + airportId))
                        .collect(Collectors.toList());

                if (!filesByAirportId.isEmpty()) {
                    int idex2 = 0;

                    for (MultipartFile file : filesByAirportId) {
                        if (!file.isEmpty()) {
                            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                            String filePath = uploadDirectory + fileName;

                            fileEntity.setFileName(fileName);
                            fileEntity.setFilePath(filePath);
                            fileEntity.setAirportId(airportId);

                            // Save fileEntity information to your database (fileEntityRepository.save)

                            saveFile(filePath, file);
                        }

                        idex2++;
                    }
                }

                index1++;
            }

            return ResponseEntity.ok("Files uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }

    private void saveFile(String filePath, MultipartFile file) throws IOException {
        Path uploadDir = Paths.get(filePath).getParent();
        Files.createDirectories(uploadDir);
        file.transferTo(uploadDir.resolve(file.getOriginalFilename()));
    }
    
   
    
    @GetMapping("/testingforRestApi")
    public String check() {
    	return "Succes to the Authentication";
    }
}
























