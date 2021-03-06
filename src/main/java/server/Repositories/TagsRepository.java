package server.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.Entities.Tag;

import java.util.List;

public interface TagsRepository extends JpaRepository<Tag, Integer> {
    @Query("select i from Tag i")
    List<Tag> findTags(Pageable pageable);

    Tag findByName(String name);

    @Query("select t from Tag t where t.name in :tags")
    List<Tag> findAll(@Param(value = "tags") String[] tags);
}
