package server.Controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Entities.Image;
import server.Entities.Template;
import server.Entities.User;
import server.Services.ImagesService;
import server.Services.TagsService;
import server.Services.TemplatesService;
import server.Services.UsersService;

@RestController
public class ContentController {

    @Autowired
    ImagesService imagesService;
    TemplatesService templatesService;
    TagsService tagsService;
    UsersService usersService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/get")
    public List<Image> getSome() {
        List<Image> loaded = imagesService.getSomeImages(0,2);
        return loaded;

    }

    @GetMapping("/getTemplates")
    public List<Template> getTemplates() {
        List<Template> loaded = templatesService.getSomeTemplates(0,2);
        return loaded;
    }


//повышение рейтинга мема по id переданому в image, сохранение и отправка нового рейтинга обратно.
    @PostMapping("/ratingUp")
    public ResponseEntity<String> ratingUp(@RequestBody Image image) {
        imagesService.ratingUp(image.getId());
        return new ResponseEntity<String>(imagesService.getImage(image.id).getRatingUp() + "", HttpStatus.ACCEPTED);
    }
//повышение отрицательного рейтинга мема по id переданому в image, сохранение и отправка нового рейтинга обратно.
    @PostMapping("/ratingDown")
    public ResponseEntity<String> ratingDown(@RequestBody Image image) {
        imagesService.ratingDown(image.getId());
        return new ResponseEntity<String>(imagesService.getImage(image.id).getRatingDown() + "", HttpStatus.ACCEPTED);

    }
//регистрация нового пользователя в БД.
    @PostMapping("/newUser")
    public ResponseEntity<String> register(@RequestBody User user)
    {
        usersService.regNewUser(user);
        return new ResponseEntity<String>(usersService.getUser(user.getId())+"", HttpStatus.ACCEPTED);
    }
//добавление мема в БД если имеются не существующие в бд теги создать экземпляры в Tag и сохранить
    @PostMapping("/addMeme")
    public ResponseEntity<String> addMeme(@RequestBody Image meme)
    {
        imagesService.regNewImage(meme);
        return new ResponseEntity<String>(imagesService.getImage(meme.getId())+"", HttpStatus.ACCEPTED);

    }
//
    @PostMapping("/addTemplate")
    public ResponseEntity<String> register(@RequestBody Template template)
    {
        templatesService.regNewTemplate(template);
        return new ResponseEntity<String>(templatesService.getTemplate(template.getId())+"", HttpStatus.ACCEPTED);
    }

}