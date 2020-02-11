package server.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.Entities.Image;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Image, Integer> {
    @Query("select i from Image i")
    List<Image> findImages(Pageable pageable);
}
