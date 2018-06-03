package com.fmi.patokas.repository;

import com.fmi.patokas.domain.IDCard;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IDCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IDCardRepository extends JpaRepository<IDCard, Long> {

}
