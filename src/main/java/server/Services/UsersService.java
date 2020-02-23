package server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.Entities.Image;
import server.Entities.User;
import server.Repositories.UsersRepository;


import java.util.List;

@Service
public class UsersService {
    @Autowired
    public UsersRepository repo;

    public List<User> getSomeUsers(int start, int max){
        return  repo.findUsers(PageRequest.of(start, max, Sort.Direction.ASC,"id"));
    }



    public void regNewUser(User user)
    {
        List<User> users= getSomeUsers(0,100);

if(user.getName()!=null||user.getPassword()!=null||users.contains(user.getName())==false)
{
    repo.saveAndFlush(user);
}

    }

    public User getUser(int id) {
        return repo.findById(id).orElse(null);
    }

}
