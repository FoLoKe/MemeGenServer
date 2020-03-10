package server.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.Entities.Meme;
import server.Entities.Tag;

import java.util.List;

public interface MemeRepository extends JpaRepository<Meme, Integer> {
    @Query("select i from Meme i")
    List<Meme> findMemes(Pageable pageable);

    @Query("select i from Meme i Left join i.tags t where t in :tags")
    List<Meme> findMemes(Pageable pageable, List<Tag> tags);

}
