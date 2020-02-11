package server.Controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Entities.Image;
import server.Services.ImagesService;

@RestController
public class ContentController {

    @Autowired
    ImagesService imagesService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/get")
    public List<Image> getSome() {
        List<Image> loaded = imagesService.getSomeImages(0,2);
        return loaded;

    }

    @PostMapping("/ratingup")
    public ResponseEntity<String> ratingUp(@RequestBody Image image) {
        imagesService.ratingUp(image.getId());
        return new ResponseEntity<String>(imagesService.getImage(image.id).getRatingUp() + "", HttpStatus.ACCEPTED);
    }
}