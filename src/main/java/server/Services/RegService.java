package server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.Entities.User;
import server.Repositories.UsersRepository;

import javax.transaction.Transactional;

@Service
public class RegService {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User registerNewUserAccount(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean userExists(String username) {
        User user = userRepository.findByName(username);
        return user != null;
    }

}
