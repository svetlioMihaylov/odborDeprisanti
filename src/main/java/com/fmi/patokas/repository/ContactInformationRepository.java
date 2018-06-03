package com.fmi.patokas.repository;

import com.fmi.patokas.domain.ContactInformation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContactInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long> {

}
