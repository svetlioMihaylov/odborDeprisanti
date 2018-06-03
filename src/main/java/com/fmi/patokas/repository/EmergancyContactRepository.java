package com.fmi.patokas.repository;

import com.fmi.patokas.domain.EmergancyContact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmergancyContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmergancyContactRepository extends JpaRepository<EmergancyContact, Long> {

}
