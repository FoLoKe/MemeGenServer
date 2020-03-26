package server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import server.Entities.Tag;
import server.Entities.Template;

import server.Entities.User;
import server.Repositories.TagsRepository;
import server.Repositories.TemplatesRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TemplatesService {
    @Autowired
    public TemplatesRepository repo;

    @Autowired
    public TagsRepository tagsRepository;

    @Transactional
    public List<Template> getSomeTemplates(int start, int max){
        return  repo.findTemplates(PageRequest.of(start, max, Sort.Direction.ASC,"id"));
    }

    @Transactional
    public List<Template> getAllTemplates(){
        return  repo.findAll();
    }

    @Transactional
    public void regNewTemplate(Template template)
    {
        List<Tag> allTags = tagsRepository.findAll();
        Set<Tag> realTags = new HashSet<>();


            for(Tag templateTag : template.getTags()) {
                boolean found = false;

                for (Tag tag : allTags) {
                    if (tag.getName().equals(templateTag.getName())) {
                        found = true;
                    }
                }

                if(!found) {
                    tagsRepository.saveAndFlush(templateTag);
                }

                Tag tag = tagsRepository.findByName(templateTag.getName());
                realTags.add(tag);

            }
        template.setTags(realTags);
        repo.saveAndFlush(template);

    }

    @Transactional
    public Template getTemplate(int id) {
        return repo.findById(id).orElse(null);
    }
}
