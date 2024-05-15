package app.growingseasons.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BusinessKeyCheck<T, ID> extends JpaRepository<T, ID> {

    boolean existsByBusinessKey(String key);
}
