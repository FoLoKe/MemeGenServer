package server.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "memegen.Memes")
public class Meme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    public int id;

    @Column(name = "image", length = 1024 * 1024 * 32)
    private byte[] image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "tags")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "memegen.tags_images")
    @OrderBy(value = "tag_id")
    private Set<Tag> tags;

    @Column(name = "likes")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "memegen.users_likes")
    @OrderBy(value = "user_id")
    private Set<User> likes;

    @Column(name = "dislikes")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "memegen.users_dislikes")
    @OrderBy(value = "user_id")
    private Set<User> dislikes;

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Set<User> getDislikes() {
        return dislikes;
    }

    public void setDislikes(Set<User> dislikes) {
        this.dislikes = dislikes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}