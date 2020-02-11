package server.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "memegen.Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    public int id;

    @Column(name = "image", length = 1024 * 1024 * 32)
    private byte[] image;

    @Column(name = "ratingUp")
    private int ratingUp;

    @Column(name = "ratingDown")
    private int ratingDown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "tags")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tags_images")
    @OrderBy(value = "tag_id")
    private Set<Tag> tags;

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

    public int getRatingUp() {
        return ratingUp;
    }

    public void setRatingUp(int ratingUp) {
        this.ratingUp = ratingUp;
    }

    public int getRatingDown() {
        return ratingDown;
    }

    public void setRatingDown(int ratingDown) {
        this.ratingDown = ratingDown;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}