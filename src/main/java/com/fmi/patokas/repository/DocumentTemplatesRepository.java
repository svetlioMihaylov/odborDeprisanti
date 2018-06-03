package com.fmi.patokas.repository;

import com.fmi.patokas.domain.DocumentTemplates;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DocumentTemplates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTemplatesRepository extends JpaRepository<DocumentTemplates, Long> {

}
