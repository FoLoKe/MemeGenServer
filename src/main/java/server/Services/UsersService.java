package server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.Entities.User;
import server.Repositories.UsersRepository;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService implements UserDetailsService {
    @Autowired
    public UsersRepository repo;

    public List<User> getSomeUsers(int start, int max) {
        return repo.findUsers(PageRequest.of(start, max, Sort.Direction.ASC, "id"));
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public void regNewUser(User user) {
        List<User> users = getAllUsers();

        if (user.getName() != null || user.getPassword() != null) {
            repo.saveAndFlush(user);
        }

    }

    public User getUser(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repo.findByName(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new MGUser(user);
    }

    public User findByName(String s) {
        User user = repo.findByName(s);
        return user;
    }

    public class MGUser implements UserDetails {
        private User user;

        public MGUser(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
