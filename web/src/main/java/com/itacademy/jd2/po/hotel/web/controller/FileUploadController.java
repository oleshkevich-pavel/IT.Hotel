package com.itacademy.jd2.po.hotel.web.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.itacademy.jd2.po.hotel.service.IStorageService;
import com.itacademy.jd2.po.hotel.service.googledrive.StorageFileNotFoundException;

@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {

    @Autowired
    private IStorageService storageService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listUploadedFiles() throws IOException {
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("files",
                storageService.loadAll()
                        .map(path -> MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                        .collect(Collectors.toList()));

        return new ModelAndView("upload", hashMap);
    }

    // @GetMapping("upload/files/{filename:.+}")
    @ResponseBody
    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file)
            throws GeneralSecurityException, IOException {

        storageService.store(file);
        String url = storageService.getUploadedFileUrl(file);
        storageService.deleteAll();
        System.out.println("new uploaded file " + url);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("message", url);
        return new ModelAndView("upload", hashMap);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
