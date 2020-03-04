package server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.Entities.Tag;

import server.Repositories.TagsRepository;

import java.util.List;

@Service
public class TagsService {
    @Autowired
    public TagsRepository repo;

    public List<Tag> getSomeTags(int start, int max){
        return  repo.findTags(PageRequest.of(start, max, Sort.Direction.ASC,"id"));
    }

    public List<Tag> getAllTags(){
        return  repo.findAll();
    }

    public void regNewTag(Tag tag)
    {
        List<Tag> tags= getAllTags();


        if(tag.getName()!=null&&!tags.contains(tag))
        {
            repo.saveAndFlush(tag);
        }

    }

    public Tag getTag(int id) {
        return repo.findById(id).orElse(null);
    }
}
