package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


//Zadanie 6
@RestController
public class ImageController {

    @GetMapping("brightenImage")
    public String increaseImageBrightness(@RequestBody ImageRequest request) throws IOException {

        String imageBase = request.getImageBase();
        int factor = request.getFactor();

        byte[] photo = Base64.getDecoder().decode(imageBase);
        BufferedImage bf = ImageIO.read(new ByteArrayInputStream(photo));

        ImageFormController imageFormController = new ImageFormController();
        imageFormController.increaseBrightness(bf, factor);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", stream);
        byte[] photo2 = stream.toByteArray();
        return Base64.getEncoder().encodeToString(photo2);
    }

    //Zadanie 7
    @GetMapping("brightenImage1")
    public ResponseEntity<byte[]> increaseImageBrightnessBinary(@RequestBody ImageRequest request) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(request.getImageBase());
        BufferedImage bf = ImageIO.read(new ByteArrayInputStream(imageBytes));
        ImageFormController imageFormController = new ImageFormController();
        imageFormController.increaseBrightness(bf, request.getFactor());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", stream);
        byte[] newImage = stream.toByteArray();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(newImage.length);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(newImage);
    }
}

