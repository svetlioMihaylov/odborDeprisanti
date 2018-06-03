package com.fmi.patokas.repository;

import com.fmi.patokas.domain.ExternalPerson;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExternalPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalPersonRepository extends JpaRepository<ExternalPerson, Long> {

}
