package org.dms.repository;

import org.dms.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Documents, Long> {

}
