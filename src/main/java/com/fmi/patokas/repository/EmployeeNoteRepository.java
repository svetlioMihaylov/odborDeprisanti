package com.fmi.patokas.repository;

import com.fmi.patokas.domain.EmployeeNote;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeNoteRepository extends JpaRepository<EmployeeNote, Long> {

}
