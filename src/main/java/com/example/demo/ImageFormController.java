package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.io.ByteArrayInputStream;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;

//Zadanie 8
@RestController
public class ImageFormController {

    private BufferedImage image1;
    private String Base;
    private ImageController increase;


    public void increaseBrightness(BufferedImage image, int factor) {
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                int pixel = image.getRGB(x, y);
                pixel = brightenPixel(pixel, factor);
                image.setRGB(x, y, pixel);
            }
        }
    }


    private int brightenPixel(int pixel, int factor) {
        int mask = 255;
        int blue = pixel & mask;
        int green = (pixel >> 8) & mask;
        int red = (pixel >> 16) & mask;
        blue = brightenPixelPart(blue, factor);
        green = brightenPixelPart(red, factor);
        red = brightenPixelPart(red, factor);
        return blue + (green << 8) + (red << 16);
    }

    private int brightenPixelPart(int color, int factor) {
        color += factor;
        if (color > 255) {
            return 255;
        } else {
            return color;
        }
    }

    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("factor") int factor,  Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "error");
            return "index";
        }

        try {
            byte[] photo =file.getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(photo);
            ImageRequest request = new ImageRequest(encodedImage, factor);

            byte[] imageBytes = Base64.getDecoder().decode(request.getImageBase());
            BufferedImage bf = ImageIO.read(new ByteArrayInputStream(imageBytes));
            increaseBrightness(bf, factor);
            Base = Base64.getEncoder().encodeToString(photo);
            model.addAttribute("image", bf);
            return "image";
        } catch (IOException e) {
            model.addAttribute("message", "error: " + e.getMessage());
            return "index";
        }
    }

    @RequestMapping("index")
    public String index() {
        return "index.html";
    }

    @GetMapping("image")
    public String show(Model model) {
        model.addAttribute("image", Base);
        return "image";
    }
}
