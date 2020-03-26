package server.Services;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.Entities.Tag;

import server.Repositories.TagsRepository;

import javax.naming.directory.SearchResult;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagsService {
    @Autowired
    public TagsRepository repo;

    @PersistenceContext
    public EntityManager entityManager;

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

    @Transactional
    public List<Tag> findTags(String tagName) {

        //Search.getFullTextEntityManager()

        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Tag.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .wildcard()
                .onField("name")
                .matching("*"+tagName+"*")
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, Tag.class);

        List<Tag> results = jpaQuery.getResultList();

        return results;
    }
}
