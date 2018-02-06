package com.duke.web.controller;

import com.duke.domain.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    private static  final String folder = "E:\\workspace_duke\\duke-security\\duke-security-demo\\src\\main\\resources\\file";

    @RequestMapping(method = RequestMethod.POST)
    public FileInfo upload(MultipartFile file) throws IOException {
        File localFile = new File(folder, new Date().getTime() + ".txt");

        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public void downLoad(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            IOUtils.copy(inputStream, outputStream);

            outputStream.flush();
        }
    }

}
