package server.Entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "memegen.Templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    public int id;

    @Column(name = "image", length = 1024 * 1024 * 32)
    private byte[] image;

    @Column(name = "tags")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "memegen.tags_templates")
    @OrderBy(value = "tag_id")
    private Set<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
