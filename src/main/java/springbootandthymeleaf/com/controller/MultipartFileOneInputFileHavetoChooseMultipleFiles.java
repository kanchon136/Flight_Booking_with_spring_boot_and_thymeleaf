//package springbootandthymeleaf.com.controller;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.util.StringUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api")
//public class MultipartFileOneInputFileHavetoChooseMultipleFiles {
//
//    @PostMapping("/saveResultInfo")
//    public ResponseEntity<String> saveResultInfo(
//            @RequestParam("distId") List<String> distIdList,
//            @RequestParam("unitIdA") List<String> unitIdList,
//            @RequestParam("testResultA") List<String> testResultList,
//            @RequestParam("i_nameA") List<String> iNameList,
//            @RequestParam("remarks") String remarks,
//            @RequestParam("files") List<MultipartFile> files) throws IOException {
//
//        String filePathOut = "../WEB-INF/assets/image/distribution_doc/";
//        String filePathIn = "/WEB-INF/assets/image/distribution_doc/";
//
//        for (int i = 0; i < distIdList.size(); i++) {
//            ResultInfo resuInfonew = new ResultInfo();
//            resuInfonew.setDistributionId(UUID.fromString(distIdList.get(i)));
//            resuInfonew.setTestResult(testResultList.get(i));
//
//            if (StringUtils.isNotBlank(unitIdList.get(i))) {
//                resuInfonew.setUnitId(UUID.fromString(unitIdList.get(i)));
//            } else {
//                resuInfonew.setUnitId(null);
//            }
//
//            resuInfonew.setRemarks(remarks);
//
//            List<MultipartFile> filesByDistId = files.stream()
//                    .filter(file -> file.getName().equals("files_" + distIdList.get(i)))
//                    .collect(Collectors.toList());
//
//            if (!filesByDistId.isEmpty()) {
//                int a = 0;
//                for (MultipartFile file : filesByDistId) {
//                    if (!file.isEmpty()) {
//                        String originalFilename = file.getOriginalFilename();
//                        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//                        String fileName = distIdList.get(i) + "_" + a + "." + extension;
//                        resuInfonew.setDocLocation(fileName);
//                        saveFile(file, fileName, filePathIn);
//                        saveFile(file, fileName, filePathOut);
//                        resultService.saveResultInfo(resuInfonew);
//                        a++;
//                    } else {
//                        resuInfonew.setDocLocation(null);
//                        resultService.saveResultInfo(resuInfonew);
//                    }
//                }
//            }
//        }
//
//        // You can customize the response message as needed
//        return new ResponseEntity<>("Files uploaded successfully!", HttpStatus.OK);
//    }
//
//    private void saveFile(MultipartFile file, String fileName, String path) throws IOException {
//        byte[] bytes = file.getBytes();
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
//        file.transferTo(serverFile);
//    }
//}
//
//
//
//
