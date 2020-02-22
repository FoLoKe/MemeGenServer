package server.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.Entities.Template;

import java.util.List;

public interface TemplatesRepository extends JpaRepository<Template, Integer> {
    @Query("select i from Template i")
    List<Template> findTemplates(Pageable pageable);
}
