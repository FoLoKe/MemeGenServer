package server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.Entities.Image;
import server.Entities.Tag;
import server.Entities.User;
import server.Repositories.ImagesRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ImagesService {
    @Autowired
    public ImagesRepository repo;
    public TagsService tagser;
    public List<Image> getSomeImages(int start, int max){
        return  repo.findImages(PageRequest.of(start, max, Sort.Direction.ASC, "id"));
    }

    @Transactional
    public void ratingUp(int id) {
        Image image = repo.findById(id).orElse(null);
        image.setRatingUp(image.getRatingUp() + 1);
        repo.saveAndFlush(image);
    }

    @Transactional
    public void ratingDown(int id) {
        Image image = repo.findById(id).orElse(null);
        image.setRatingDown(image.getRatingDown() +1);
        repo.saveAndFlush(image);
    }


    public void regNewImage(Image image)
    {
        List<Tag> tags= tagser.getSomeTags(0,100);

        if(!tags.contains(image.getTags()))
        {

        }

        if(image.getImage()!=null)
        {
            repo.saveAndFlush(image);
        }

    }

    public Image getImage(int id) {
        return repo.findById(id).orElse(null);
    }
}
