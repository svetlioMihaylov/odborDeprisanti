package com.fmi.patokas.repository;

import com.fmi.patokas.domain.EmployeePhoto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeePhoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeePhotoRepository extends JpaRepository<EmployeePhoto, Long> {

}
