package server.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.Entities.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Integer> {
    @Query("select i from User i")
    List<User> findUsers(Pageable pageable);


}
