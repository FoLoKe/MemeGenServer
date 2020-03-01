package server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import server.Entities.Template;

import server.Entities.User;
import server.Repositories.TemplatesRepository;

import java.util.List;

@Service
public class TemplatesService {
    @Autowired
    public TemplatesRepository repo;

    public List<Template> getSomeTemplates(int start, int max){
        return  repo.findTemplates(PageRequest.of(start, max, Sort.Direction.ASC,"id"));
    }

    public List<Template> getAllTemplates(){
        return  repo.findAll();
    }

    public void regNewTemplate(Template template)
    {
        List<Template> templates= getAllTemplates();


        if(template.getName()!=null&&!templates.contains(template))
        {
            repo.saveAndFlush(template);
        }

    }

    public Template getTemplate(int id) {
        return repo.findById(id).orElse(null);
    }
}
