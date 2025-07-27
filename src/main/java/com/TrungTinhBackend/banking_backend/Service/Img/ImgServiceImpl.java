package com.TrungTinhBackend.banking_backend.Service.Img;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImgServiceImpl implements ImgService{

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile img) throws IOException {
        Map<?,?> uploadResult = cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    @Override
    public String update(String oldImg, MultipartFile img) throws IOException {
        String publicId = extractPublicId(oldImg);
        if(publicId != null) {
            cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
        }
        Map<?,?> uploadResult = cloudinary.uploader().upload(img.getBytes(),ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    private String extractPublicId(String img) {
        return (img == null || img.isEmpty()) ? null:
                img.substring(img.lastIndexOf("/")+1,img.lastIndexOf("."));
    }
}
