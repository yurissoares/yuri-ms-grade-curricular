package com.yuri.cliente.escola.gradecurricular.service;

import java.util.List;

import com.yuri.cliente.escola.gradecurricular.dto.MateriaDto;

public interface IMateriaService {

	public Boolean atualizar(final MateriaDto materia);
	
	public Boolean excluir(final Long id);
	
	public List<MateriaDto> listar();
	
	public MateriaDto buscar(final Long id);
	
	public Boolean cadastrar(final MateriaDto materia);
	
	public List<MateriaDto> listarPorHoraMinima(int horaMinima);

	public List<MateriaDto> listarPorFrequencia(int frequencia);
	
}
