package com.yuri.cliente.escola.gradecurricular.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yuri.cliente.escola.gradecurricular.entity.MateriaEntity;

@Repository
public interface IMateriaRepository extends JpaRepository<MateriaEntity, Long> {

	@Query("SELECT m FROM MateriaEntity m WHERE m.horas >= :horaMinima")
	public List<MateriaEntity> findByHoraMinima(@Param("horaMinima") int horaMinima);
	
	@Query("SELECT m FROM MateriaEntity m WHERE m.frequencia = :frequencia")
	public List<MateriaEntity> findByFrequencia(@Param("frequencia") int frequencia);
}
