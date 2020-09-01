package com.yuri.cliente.escola.gradecurricular.service;

import java.util.List;

import com.yuri.cliente.escola.gradecurricular.entity.CursoEntity;
import com.yuri.cliente.escola.gradecurricular.model.CursoModel;

public interface ICursoService {

	public Boolean cadastrar(final CursoModel curso);

	public Boolean atualizar(final CursoModel curso);
	
	public Boolean excluir(final Long id);
	
	public List<CursoEntity> listar();
	
	public CursoEntity buscarPorCodigo(final String codigo);

}
