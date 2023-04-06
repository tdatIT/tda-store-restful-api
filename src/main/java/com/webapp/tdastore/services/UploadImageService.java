package com.webapp.tdastore.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.webapp.tdastore.config.CloudinaryUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Log4j2
public class UploadImageService {
    @Autowired
    Cloudinary cloudinary;

    public String uploadFile(MultipartFile multipartFile) {
        try {
            Map r = this.cloudinary.uploader().upload(multipartFile.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            return (String) r.get("secure_url");
        } catch (Exception e) {
            log.error("Delete file was failed. Not found this file");
        }
        return null;
    }

    public void removeFile(String url_cloudinary) {
        try {
            String old_imageId = CloudinaryUtils.getPublicId(url_cloudinary);
            cloudinary.uploader().destroy(old_imageId,
                    ObjectUtils.asMap("resource_type", "image"));
        } catch (Exception e) {
            log.error("Delete file was failed. Not found this file");
        }

    }
}
