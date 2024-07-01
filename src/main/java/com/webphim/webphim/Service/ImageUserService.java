package com.webphim.webphim.Service;

import com.webphim.webphim.Model.ImageUser;
import com.webphim.webphim.Reponsitory.ImageUserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageUserService {
    @Autowired
    private ImageUserRepository imageUserRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    public Optional<ImageUser> getImageUserById(Long id) {return imageUserRepository.findById(id);}
    @SneakyThrows
    public void updateImageUserById(int id, MultipartFile AvatarUrl) {
        ImageUser imageUser = this.getImageUserById((long) id).orElse(null);
        imageUser.setUrl(cloudinaryService.uploadImage(AvatarUrl));
        imageUserRepository.save(imageUser);
    }
}
