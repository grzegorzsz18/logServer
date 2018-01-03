package com.semafors.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.semafors.dao.implementation.UserDAO;
import com.semafors.entity.Log;
import com.semafors.service.LogService;
import com.semafors.service.UserService;

@RestController
@RequestMapping("log/")
public class LogController {
	
	public static final String uploadingdir = System.getProperty("user.dir") + "/images/";
	@Autowired LogService logService;
	@Autowired ServletContext servletContext;
	@Autowired UserService userService;
	
	
	@GetMapping("numberOfActive")
	public Long getNumberOfActiveLogs() {
		return logService.getNumberOfActiveLogs();
	}
	@PostMapping("add")
	public long add(@RequestBody Log log) {
		return logService.addLog(log);
	}
	
	@GetMapping("byUser/{userId}")
	public List<Log> byUser(@PathVariable("userId") long userId){
		return logService.getByUser(userId);
	}
	
	@GetMapping("all")
	public List<Log> getAll(){
		return logService.getAll();
	}
	
	@GetMapping("byLogId/{logId}")
	public Log byLogId(@PathVariable("logId") long logId){
		return logService.getByLogId(logId);
	}
	
	@PostMapping("image")
    public void uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) throws IOException {
        for(MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file);
        }
    }
	
	@PostMapping("login")
	public long login(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, @RequestParam("login") String login, @RequestParam("password") String password) throws Exception {
		long loginId = logService.login(login, password, uploadingFiles);
		return loginId;
	}
	
	@PostMapping("logout")
	public void logout(@RequestParam("login") String login) {
		userService.logout(login);
	}
	
	@RequestMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) throws IOException {
        String filename = id;
        filename = uploadingdir + id + ".jpg";
        InputStream inputImage = new FileInputStream(filename);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int l = inputImage.read(buffer);
        while(l >= 0) {
            outputStream.write(buffer, 0, l);
            l = inputImage.read(buffer);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/png");
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);  
    }
	
}
