package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


//Zadanie 6
public class ImageController {

    @GetMapping("brightenImage}")
    public String increaseImageBrigthness(@RequestParam String imageBase, @RequestParam int factor) throws IOException {
        byte[] photo = Base64.getDecoder().decode(imageBase);
        BufferedImage bf = ImageIO.read(new ByteArrayInputStream(photo));
        BufferedImage image = ImageIO.read((ImageInputStream) bf);

        ImageFormController imageFormController = new ImageFormController();
        imageFormController.getImage1(image);
        imageFormController.increaseBrightness(bf, factor);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", stream);
        byte[] photo2 = stream.toByteArray();
        String result = Base64.getEncoder().encodeToString(photo2);
        return result;
    }

    //Zadanie 7
    @GetMapping
    public ResponseEntity<byte[]> increaseImageBrightnessBinary(@RequestParam String ImageBase, @RequestParam int factor) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(ImageBase);
        BufferedImage bf = ImageIO.read(new ByteArrayInputStream(imageBytes));
        ImageFormController imageFormController = new ImageFormController();
        imageFormController.increaseBrightness(bf, factor);
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


