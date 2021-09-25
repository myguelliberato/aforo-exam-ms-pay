package com.aforo255.exam.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aforo255.exam.domain.Operacion;

@Repository
public interface OperacionRepository extends CrudRepository<Operacion, Integer>, JpaSpecificationExecutor<Operacion> {

}
