package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//Zadanie 3
@RestController
public class RectangleController {
    private final List<Rectangle> rectangles = new ArrayList<>();

    @GetMapping("rectangle")
    public Rectangle getRectangle() {
        return new Rectangle(30, 20,
                150, 220, "red");
    }

    @GetMapping("rectangles")
    public List<Rectangle> getRectangles() {
        return this.rectangles;
    }

   @GetMapping("toSvg")
   public String toSvg(){
        StringBuilder sb = new StringBuilder();
        sb.append("<svg width=\"500\" height=\"500\">");
        for (Rectangle rectangle : rectangles) {
            sb.append(String.format("<text font-size=\"20\" y=\"50%%\"> rectangle width=\"%d\" height=\"%d\") x=\"%d\" y=\"%d\" fill=\"%s\"</text>", rectangle.getWidth(), rectangle.getHeight(), rectangle.getX(), rectangle.getY(), rectangle.getColor()));
        }
        sb.append("</svg>");
        return sb.toString();
   }

    //Zadanie 4
    @PostMapping("addRectangle")
    public int addRectangle(@RequestBody Rectangle rectangle) {
        rectangles.add(rectangle);

        return rectangles.size();
    }

    //Zadanie 5
    @GetMapping("rectangle/{id}")
    public Rectangle getRectangle(@PathVariable int id) throws IOException {
        return rectangles.get(id);
    }

    @PutMapping("rectangle/{id}")
    public void updateRectangle(@PathVariable int id, @RequestBody Rectangle rectangle) {
        rectangles.set(id, rectangle);
    }

    @DeleteMapping ("rectangle/{id}")
        public void deleteRectangle(@PathVariable int id) {
        rectangles.remove(id);
        }
}