package server.Entities;

import javax.persistence.*;

@Entity
@Table(name = "memegen.users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "role")
    private String role;

    @Column(name = "nickname")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "banned")
    private boolean banned;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!User.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        if(obj.hashCode() != hashCode()) {
            return false;
        }

        return ((User) obj).getName().equals(name);
    }
}
