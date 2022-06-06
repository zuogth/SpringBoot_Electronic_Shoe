package com.dth.spring_boot_shoe.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class FirebaseService {

    private Storage storage;

    @EventListener
    public void init(ApplicationReadyEvent event){
        try{
            ClassPathResource serviceAccount=new ClassPathResource("spring-boot-shoe-firebase-adminsdk.json");
            storage=StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setProjectId("spring-boot-shoe").build().getService();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public String upLoadFile(MultipartFile file,String folderName) throws IOException {
        String imageName = generateFileName(file.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        BlobId blobId = BlobId.of("spring-boot-shoe.appspot.com", folderName+"/"+imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo,file.getInputStream());
        return imageName;
    }

    public String upLoadFileText(String base64) throws IOException {
        byte[] decoder = Base64.getDecoder().decode(base64);
        String fileName = UUID.randomUUID().toString()+".pdf";
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", fileName);
        BlobId blobId = BlobId.of("spring-boot-shoe.appspot.com", "export/"+fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType("application/pdf")
                .build();
        storage.create(blobInfo,decoder);
        return fileName;
    }

    public void deleteImage(String imageName,String folderName){

        BlobId blobId=BlobId.of("spring-boot-shoe.appspot.com",folderName+"/"+imageName);
        storage.delete(blobId);
    }

    public String getUrl(String imageName,String folderName){

        String url="https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/"+folderName+"%2F";
        url+=imageName+"?alt=media";

        return url;
    }


    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

}
