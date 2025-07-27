package com.TrungTinhBackend.banking_backend.Service.Img;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImgService {
    String upload(MultipartFile img) throws IOException;
    String update(String oldImg,MultipartFile img) throws IOException;
}
