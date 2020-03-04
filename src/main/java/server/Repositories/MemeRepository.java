package server.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.Entities.Meme;

import java.util.List;

public interface MemeRepository extends JpaRepository<Meme, Integer> {
    @Query("select i from Meme i")
    List<Meme> findMemes(Pageable pageable);


}
