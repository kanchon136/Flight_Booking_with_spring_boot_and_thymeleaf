package springbootandthymeleaf.com.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springbootandthymeleaf.com.entity.FileEntity;
import springbootandthymeleaf.com.repository.FileEntityRepository;
import springbootandthymeleaf.com.service.AirportService;

@Controller
public class FileHandallingController {
	
@Autowired
private FileEntityRepository fileEntityRepository;
@Autowired
 private AirportService aiportService;

@Value("${upload.directory}")
private String uploadDirectory;

@GetMapping("/fileUpload")
public String showFileUploadPage(Model model) {
	model.addAttribute("airports", aiportService.getAllAirports());
	
	return "fileUpload";
}


@PostMapping("/upload")
public String fileUpload(HttpServletRequest request) throws IOException {
	
	Map<String,  String[]> parameterMap= request.getParameterMap();
	Map<String, List<MultipartFile> > multipartFilemap = ((MultipartHttpServletRequest)request).getMultiFileMap();
	
	
	String[] airportIds = (String[])parameterMap.get("distId[]");
	List<String>airportIdList = Arrays.stream(airportIds).collect(Collectors.toList());
	
	int index1 =0;
	for(String s : airportIdList) {
		 FileEntity fileEntity = new FileEntity();
		List<MultipartFile> filesByAirportId = multipartFilemap.get("files_"+airportIdList.get(index1));
		
		if(filesByAirportId != null && !filesByAirportId.isEmpty()) {
			
			int idex2 = 0;
			for(MultipartFile file : filesByAirportId) {
				
				if(!file.isEmpty()) {
					String fileName= StringUtils.cleanPath(file.getOriginalFilename());
					
					   
					    fileEntity.setFileName(fileName);
					    fileEntity.setFilePath(uploadDirectory+fileName);
					    fileEntity.setAirportId(airportIdList.get(index1));
					    fileEntityRepository.save(fileEntity);
					    saveFile(uploadDirectory, fileName, file);
					
				}else {
					
					// just save database information when file not choose (if other information need to save the database)
					
				}
				
				idex2++;
			}	
		}		
				index1++;
		
	}
	
	return "redirect:/fileUpload";
}

public static void saveFile(String uploadDirectory, String fileName, MultipartFile file) throws IOException {
	Path uploadDir = Paths.get(uploadDirectory);
	Files.createDirectories(uploadDir);
	file.transferTo(uploadDir);
	
}


//// new Method by kanchon
//@ResponseBody
//@RequestMapping(value = "/saveResultInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//public ModelAndView saveResultInfo   (HttpServletRequest request,
//                     @Valid @ModelAttribute("qcResultInfo") ResultInfo resultInfo) throws IOException {
//	
//	Map<String, String[]> parameters = request.getParameterMap();
//	Map<String, List<MultipartFile>> fileParameters =((MultipartHttpServletRequest)request).getMultiFileMap();
//   
//	 String [] mstId =(String[]) parameters.get("distId[]");
//	 List<String> distIdList= Arrays.stream(mstId).collect(Collectors.toList());
//	 
//	 String [] unitId = (String[])  parameters.get("unitIdA[]");
//	 List<String> unitIdList = new ArrayList<String>(Arrays.asList(unitId));
//	 
//	 String [] testResult = (String[]) parameters.get("testResultA[]");
//	 List<String> testResultList = Arrays.stream(testResult).collect(Collectors.toList());
//	 
//	 String [] iName = (String[])parameters.get("i_nameA[]");
//	 List<String> iNameList = Arrays.stream(iName).collect(Collectors.toList());
//	 
//	 String remarks = parameters.get("remarks")[0];
//	
//	 String filePathOut = "../WEB-INF/assets/image/distribution_doc/";
//	 String filePathIn = "/WEB-INF/assets/image/distribution_doc/";
//	 ModelAndView modelAndView = new ModelAndView();
//	  
//	 ResultInfo resuInfonew = new ResultInfo();
//	 
//	 
//	 for (int i = 0; i < distIdList.size(); i++) {
//		 
//		 resuInfonew.setDistributionId(UUID.fromString(distIdList.get(i)));
//		 resuInfonew.setTestResult(testResultList.get(i));
//
//		    if (StringUtils.isNotBlank(unitIdList.get(i).toString())) {
//		    	resuInfonew.setUnitId(UUID.fromString(unitIdList.get(i)));
//		    } else {
//		    	resuInfonew.setUnitId(null);
//		    }
//
//		    resuInfonew.setRemarks(remarks);
//
//		    List<MultipartFile> filesByDistId = fileParameters.get("files_" + distIdList.get(i));
//		    if (filesByDistId != null && !filesByDistId.isEmpty()) {
//		    	int a=0;
//		        for (MultipartFile file : filesByDistId) {
//		            if (!file.isEmpty()) {
//		            	System.out.println("distId if the file is contain========="+distIdList.get(i));
//		                String originalFilename = file.getOriginalFilename();
//		                String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//		                String fileName = distIdList.get(i)+"_"+a + "." + extension;
//		                resuInfonew.setDocLocation(fileName);
//		                saveFile(file, fileName, filePathIn);
//		                saveFile(file, fileName, filePathOut);
//		                resultService.saveResultInfo(resuInfonew);
//		                a++;
//		            } else {
//		            	resuInfonew.setDocLocation(null);
//		                resultService.saveResultInfo(resuInfonew);
//		            }
//		        }
//		    }
//		}
// 
//    modelAndView.addObject("singleDistInfos", resultService.getAllSingle());
//    modelAndView.setViewName("dist_dashboard/transaction_info/distribution_infos");
//
//    return modelAndView;
//}









}
