package sandro.elib.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sandro.elib.elib.domain.EbookService;

public interface EBookServiceRepository extends JpaRepository<EbookService, Long> {
    EbookService findByName(String name);
}
