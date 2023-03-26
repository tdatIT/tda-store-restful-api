package com.webapp.tdastore.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryUtils {
    @Bean
    public Cloudinary getCloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dddb8btv0",
                "api_key", "159138865977743",
                "api_secret", "xz-CUQykgKnBja571VNtfhX2gsU"));
        return cloudinary;
    }
    public static String getPublicId(String url) {
        //Pattern: https://res.cloudinary.com/dddb8btv0/image/upload/v1673351470/fjzp6oxncho5b7zj06fv.png
        String[] url_token = url.split("/");
        //url_token[7] = filename.png
        return url_token[7].split("\\.")[0];//public_id without extension file
    }
}
