package com.yuri.cliente.escola.gradecurricular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yuri.cliente.escola.gradecurricular.entity.CursoEntity;

@Repository
public interface ICursoRepository extends JpaRepository<CursoEntity, Long> {
	
	@Query("select c from CursoEntity c where c.codigo = :codigo")
	public CursoEntity findByCodigo(@Param("codigo") String codigo);

}
