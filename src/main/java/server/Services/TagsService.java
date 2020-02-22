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
}
