package com.sfac.javaSpringBoot.modules.test.controller;

import com.sfac.javaSpringBoot.modules.test.entity.City;
import com.sfac.javaSpringBoot.modules.test.entity.Country;
import com.sfac.javaSpringBoot.modules.test.service.CityService;
import com.sfac.javaSpringBoot.modules.test.service.CountryService;
import com.sfac.javaSpringBoot.modules.test.vo.ApplicationTest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/test")
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Value("${server.port}")
    private int port;
    @Value("${com.name}")
    private String name;
    @Value("${com.age}")
    private int age;
    @Value("${com.desc}")
    private String desc;
    @Value("${com.random}")
    private String random;

    @Autowired
    private ApplicationTest applicationTest;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    /**
     * 127.0.0.1/test/indexSimple ------ get
     */
    @GetMapping("/indexSimple")
    public String indexSimpleTestPage(){
        return "indexSimple";
    }

    /**
     * 127.0.0.1:8085/test/logTest   -----get
     */
    @GetMapping("/logTest")
    @ResponseBody
    public String logTest(){
        LOGGER.trace("This is trace log");
        LOGGER.debug("This is debug log");
        LOGGER.info("This is info log");
        LOGGER.warn("This is warn log");
        LOGGER.error("This is error log");
        return "This is myLog111111";
    }

    /**
     * 127.0.0.1:8085/test/config ---------get
     */
    @GetMapping("/config")
    @ResponseBody
    public String configTest(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(port).append("----")
                .append(name).append("----")
                .append(age).append("----")
                .append(desc).append("----")
                .append(random).append("----").append("<br/>");
        stringBuffer.append(applicationTest.getPort()).append("----")
                .append(applicationTest.getName()).append("----")
                .append(applicationTest.getAge()).append("----")
                .append(applicationTest.getDesc()).append("----")
                .append(applicationTest.getRandom()).append("----").append("<br/>");
        return stringBuffer.toString();
    }


    /**
     * 127.0.0.1:8080/test/testDesc ---------get
     */
    //@GetMapping("/testDesc")
    //@ResponseBody
    //public String testDesc(){
    //    return "This is test module desc!";
    //}

    /**
     * 127.0.0.1/test/testDesc?paramKey=fuck ---------get
     */
    @GetMapping("/testDesc")
    @ResponseBody
    public String testDesc(HttpServletRequest request, @RequestParam(value = "paramKey")String paramValue){
        String paramValue2 = request.getParameter("paramKey");
        return "This is test module desc."+paramValue+"=="+paramValue2;
    }

    /**
     * 127.0.0.1/test/index  -----  get
     */
    //@GetMapping("/index")
    //public String testIndexPage(ModelMap modelMap){
    //    modelMap.addAttribute("thymeleafTitle","Thymeleaf Text");
    //    modelMap.addAttribute("template","test/index");
    //    return "index";
    //}
    /**
     * 127.0.0.1/test/index2  -----  get
     */
    @GetMapping("/index2")
    public String testIndex2Page(ModelMap modelMap){
        modelMap.addAttribute("template","test/index2");
        return "index";
    }

    /**
     * 127.0.0.1/test/file ----- post
     */
    @PostMapping(value = "/file",consumes = "multipart/form-data")
    public String uploadFiles(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes){
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Please select file.");
            return "redirect:/test/index";
        }
        try {
            String destFilePath = "E:\\img\\upload\\"+file.getOriginalFilename();
            File destFile = new File(destFilePath);
            file.transferTo(destFile);
            redirectAttributes.addFlashAttribute("message","Upload file success.");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","Upload file failed");
        }
        return "redirect:/test/index";
    }

    /**
     * 127.0.0.1/test/files ------ post
     */
    @PostMapping(value = "/files",consumes = "multipart/form-data")
    public String uploadFiles(@RequestParam MultipartFile[] files,RedirectAttributes redirectAttributes) {
        boolean empty = true;
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                String destFilePath = "E:\\img\\upload\\" + file.getOriginalFilename();
                File destFile = new File(destFilePath);
                file.transferTo(destFile);
                empty = false;
            }
            if (empty){
                redirectAttributes.addFlashAttribute("message","Please select file.");
            }else {
                redirectAttributes.addFlashAttribute("message","Upload file success.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","Upload file failed.");
        }
        return "redirect:/test/index";
    }

    /**
     * 127.0.0.1/test/file ------- get
     */
    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName){
        Resource resource = null;
        try {
            String aa = new String(fileName.getBytes("utf-8"),"ISO-8859-1");
            resource = new UrlResource(Paths.get("E:\\img\\upload\\"+fileName).toUri());

            if (resource.exists() && resource.isReadable()){
                return ResponseEntity.ok().header(
                        HttpHeaders.CONTENT_TYPE,"application/octet-stream")
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                String.format("attachment;filename=\"%s\"",
                                        aa)).body(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文件以BufferedInputStream的方式读取到byte[]里面，然后用OutputStream.write输出文件
     */
    @RequestMapping("/download1")
    public void downloadFile1(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam String fileName){
        String filePath = "E:\\img\\upload\\"+File.separator+fileName;
        File downloadFile = new File(filePath);
        if (downloadFile.exists()){
            String aa = null;
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                aa = new String(fileName.getBytes("utf-8"),"ISO-8859-1");
                response.setContentType("application/octet-stream");
                response.setContentLength((int)downloadFile.length());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment;filename=\"%s\"",aa));
                byte[] buffer = new byte[1024];
                fis = new FileInputStream(downloadFile);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1){
                    os.write(buffer,0,i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                LOGGER.debug(e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    if (fis != null){
                        fis.close();
                    }
                    if (bis != null){
                        bis.close();
                    }
                }catch (Exception e2){
                    LOGGER.debug(e2.getMessage());
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 以包装类IOUtils输出文件
     */
    @RequestMapping("/download2")
    public void downloadFile2(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam String fileName){
        String filePath = "E:\\img\\upload\\"+fileName;
        File downloadFile = new File(filePath);
        try {
            if (downloadFile.exists()){
                String aa = new String(fileName.getBytes("utf-8"),"ISO-8859-1");
                response.setContentType("application/octet-stream");
                response.setContentLength((int)downloadFile.length());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment;filename=\"%s\"",aa));
                InputStream is = new FileInputStream(downloadFile);
                IOUtils.copy(is,response.getOutputStream());
                response.flushBuffer();
            }
        }catch (Exception e){
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 127.0.0.1/test/index  -----  get
     */
    @GetMapping("/index")
    public String testIndexPage(ModelMap modelMap){
        int countryId = 522;
        List<City> cities = cityService.getCitiesByCountryId(countryId);
        cities = cities.stream().limit(10).collect(Collectors.toList());
        Country country = countryService.getCountryByCountryId(countryId);
        modelMap.addAttribute("thymeleafTile","sc");
        modelMap.addAttribute("checked",true);
        modelMap.addAttribute("currentNumber",99);
        modelMap.addAttribute("changeType","checkbox");
        modelMap.addAttribute("baiduUrl","/test/log");
        modelMap.addAttribute("city",cities.get(0));
        modelMap.addAttribute("shopLogo","http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
        modelMap.addAttribute("shopLogo","/upload/1111.png");
        modelMap.addAttribute("country", country);
        modelMap.addAttribute("cities", cities);
        modelMap.addAttribute("updateCityUri", "/api/city");
        //modelMap.addAttribute("template", "test/index");
        return "index";
    }
}
