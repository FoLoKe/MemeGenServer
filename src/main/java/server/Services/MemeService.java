package server.Services;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.Entities.Meme;
import server.Entities.MemeInfo;
import server.Entities.Tag;
import server.Entities.User;
import server.Repositories.MemeRepository;
import server.Repositories.TagsRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MemeService {
    @Autowired
    public MemeRepository repo;
    @Autowired
    public TagsRepository tagsRepository;

    @Transactional
    public List<Meme> getSomeImages(int start, int max){
        List<Meme> memes = repo.findMemes(PageRequest.of(start, max, Sort.Direction.ASC, "id"));

        return  memes;
    }

    @Transactional
    public MemeInfo postRating(int id, boolean type, User user) {
        Meme meme = repo.findById(id).orElse(null);

        if(meme != null) {
            MemeInfo memeInfo = new MemeInfo();

            boolean likeStatus = false;
            boolean dislikeStatus = false;
            User foundLiked = null;
            User foundDisliked = null;

            for (User poster : meme.getLikes()) {
                if (poster.getName().equals(user.getName())) {
                    foundLiked = poster;
                    break;
                }
            }

            for (User poster : meme.getDislikes()) {
                if (poster.getName().equals(user.getName())) {
                    foundDisliked = poster;
                    break;
                }
            }

            if(type) {
                if(foundLiked == null) {
                    meme.getLikes().add(user);
                    likeStatus = true;
                } else {
                    meme.getLikes().remove(foundLiked);
                }

                if(foundDisliked != null) {
                    meme.getDislikes().remove(foundDisliked);
                }
            } else {
                if(foundDisliked == null) {
                    meme.getDislikes().add(user);
                    dislikeStatus = true;
                } else {
                    meme.getDislikes().remove(foundDisliked);
                }

                if(foundLiked != null) {
                    meme.getLikes().remove(foundLiked);
                }
            }

            repo.saveAndFlush(meme);

            Meme temp = new Meme();
            temp.setId(meme.id);

            memeInfo.setMeme(temp);
            memeInfo.setDislikes(meme.getDislikes().size());
            memeInfo.setLikes(meme.getLikes().size());
            memeInfo.setDislikeState(dislikeStatus);
            memeInfo.setLikeState(likeStatus);

            return memeInfo;
        }

        return null;
    }

    public void regNewImage(Meme meme)
    {

        if(meme.getImage()!=null)
        {
            List<Tag> allTags = tagsRepository.findAll();
            Set<Tag> realTags = new HashSet<>();


            for(Tag memeTag : meme.getTags()) {
                boolean found = false;

                for (Tag tag : allTags) {
                    if (tag.getName().equals(memeTag.getName())) {
                        found = true;
                    }
                }

                if(!found) {
                    tagsRepository.saveAndFlush(memeTag);
                }

                Tag tag = tagsRepository.findByName(memeTag.getName());
                realTags.add(tag);

            }
            meme.setTags(realTags);
            repo.saveAndFlush(meme);
        }

    }

    public Meme getImage(int id) {
        return repo.findById(id).orElse(null);
    }
}
