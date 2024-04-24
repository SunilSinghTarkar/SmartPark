package com.smartpark.sftp;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {

    
    private String sftpHost="localhost";

  
    private int sftpPort=22;

    
    private String sftpUser="sunil";

   
    private String sftpPassword="4177";

    
    private String sftpRemoteDirectory="/home/sunil/Sftpfiles";

    private final DefaultSftpSessionFactory factory;

    public FileService() {
        this.factory = createSftpSessionFactory();
    }

    private DefaultSftpSessionFactory createSftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost(sftpHost);
        factory.setPort(sftpPort);
        factory.setUser(sftpUser);
        factory.setPassword(sftpPassword);
        factory.setAllowUnknownKeys(true); 
        return factory;
    }

    public void uploadFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             SftpSession session = factory.getSession()) {
            session.write(inputStream, sftpRemoteDirectory + "/" + file.getOriginalFilename());
            System.out.println(file.getOriginalFilename());
        }
    }

    public InputStreamResource downloadFile(String fileName) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             SftpSession session = factory.getSession()) {
            session.read(sftpRemoteDirectory + "/" + fileName, outputStream);
            return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
        }
    }
}
