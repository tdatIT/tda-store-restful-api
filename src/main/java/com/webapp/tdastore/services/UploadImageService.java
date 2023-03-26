package com.webapp.tdastore.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.webapp.tdastore.upload.CloudinaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadImageService {
    @Autowired
    Cloudinary cloudinary;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Map r = this.cloudinary.uploader().upload(multipartFile.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));
        return (String) r.get("secure_url");

    }

    public void removeFile(String url_cloudinary) throws IOException {
        String old_imageId = CloudinaryUtils.getPublicId(url_cloudinary);
        cloudinary.uploader().destroy(old_imageId,
                ObjectUtils.asMap("resource_type", "image"));
    }
}
