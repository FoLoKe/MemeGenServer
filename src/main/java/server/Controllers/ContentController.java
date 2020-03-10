package server.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.Entities.Meme;
import server.Entities.MemeInfo;
import server.Entities.Template;
import server.Entities.User;
import server.Services.MemeService;
import server.Services.TagsService;
import server.Services.TemplatesService;
import server.Services.UsersService;

@RestController
public class ContentController {

    @Autowired
    MemeService memeService;

    @Autowired
    TemplatesService templatesService;

    @Autowired
    TagsService tagsService;

    @Autowired
    UsersService usersService;

    @GetMapping("/get")
    public List<MemeInfo> getSome() {
        List<Meme> memes = memeService.getSomeImages(0, 10);
        List<MemeInfo> memesInfo = new ArrayList<>();
        for(Meme meme : memes) {
            MemeInfo memeInfo = new MemeInfo();
            memeInfo.setLikes(meme.getLikes().size());
            memeInfo.setDislikes(meme.getDislikes().size());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.isAuthenticated()) {
                User user = usersService.findByName(auth.getName());
                if(user != null) {
                    for (User poster : meme.getLikes()) {
                        if (poster.getName().equals(user.getName())) {
                            memeInfo.setLikeState(true);
                            break;
                        }
                    }

                    for (User poster : meme.getDislikes()) {
                        if (poster.getName().equals(user.getName())) {
                            memeInfo.setDislikeState(true);
                            break;
                        }
                    }
                }
            }

            meme.setDislikes(null);
            meme.setLikes(null);
            memeInfo.setMeme(meme);
            memesInfo.add(memeInfo);
        }
        return memesInfo;
    }

    @GetMapping("/getTemplates")
    public List<Template> getTemplates() {
        List<Template> loaded = templatesService.getSomeTemplates(0,2);
        return loaded;
    }

    @PostMapping("/rating")
    public ResponseEntity<MemeInfo> postRating(@RequestParam(name = "id") int id, @RequestParam(name = "type") String typeString) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            User user = usersService.findByName(auth.getName());
            if (user != null) {
                boolean type = Boolean.parseBoolean(typeString);

                MemeInfo memeInfo = memeService.postRating(id, type, user);

                return new ResponseEntity<>(memeInfo, HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

//регистрация нового пользователя в БД.
    @PostMapping("/newUser")
    public ResponseEntity<String> register(@RequestBody User user)
    {
        usersService.regNewUser(user);
        return new ResponseEntity<String>(usersService.getUser(user.getId())+"", HttpStatus.ACCEPTED);
    }
//добавление мема в БД если имеются не существующие в бд теги создать экземпляры в Tag и сохранить
    @PostMapping("/postMeme")
    public ResponseEntity<String> addMeme(@RequestBody Meme meme)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            User user = usersService.findByName(auth.getName());
            if (user != null) {
                meme.setUser(user);
                memeService.regNewImage(meme);
                return new ResponseEntity<String>(memeService.getImage(meme.getId())+"", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/postTemplate")
    public ResponseEntity<String> register(@RequestBody Template template)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            User user = usersService.findByName(auth.getName());
            if (user != null) {
                template.setUser(user);
                templatesService.regNewTemplate(template);
                return new ResponseEntity<String>(templatesService.getTemplate(template.getId())+"", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

}