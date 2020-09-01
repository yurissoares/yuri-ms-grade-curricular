package com.yuri.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.yuri.cliente.escola.gradecurricular.constante.Mensagens;
import com.yuri.cliente.escola.gradecurricular.entity.CursoEntity;
import com.yuri.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.yuri.cliente.escola.gradecurricular.exception.CursoException;
import com.yuri.cliente.escola.gradecurricular.model.CursoModel;
import com.yuri.cliente.escola.gradecurricular.repository.ICursoRepository;
import com.yuri.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
public class CursoService implements ICursoService {

	private ICursoRepository cursoRepository;

	private IMateriaRepository materiaRepository;

	@Autowired
	public CursoService(ICursoRepository cursoRepository, IMateriaRepository materiaRepository) {
		this.cursoRepository = cursoRepository;
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean cadastrar(CursoModel curso) {
		try {

			if (curso.getId() != null) {
				throw new CursoException(Mensagens.ERRO_ID_INFORMADO.getValor(), HttpStatus.BAD_REQUEST);
			}

			if (this.cursoRepository.findByCodigo(curso.getCodigo()) != null) {
				throw new CursoException(Mensagens.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getValor(), HttpStatus.BAD_REQUEST);
			}
			return this.cadastrarOuAtualizar(curso);

		}catch (CursoException c) {
			throw c;
		}
		catch (Exception e) {
			throw new CursoException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private Boolean cadastrarOuAtualizar(CursoModel curso) {
		List<MateriaEntity> listMateriaEntity = new ArrayList<>();

		if (!curso.getMaterias().isEmpty()) {

			curso.getMaterias().forEach(materia -> {
				if (this.materiaRepository.findById(materia).isPresent())
					listMateriaEntity.add(this.materiaRepository.findById(materia).get());
			});
		}

		CursoEntity cursoEntity = new CursoEntity();
		if(curso.getId()!=null) {
			cursoEntity.setId(curso.getId());
		}
		cursoEntity.setCodigo(curso.getCodigo());
		cursoEntity.setNome(curso.getNome());
		cursoEntity.setMaterias(listMateriaEntity);

		this.cursoRepository.save(cursoEntity);

		return Boolean.TRUE;
	}


	@Override
	public Boolean atualizar(CursoModel curso) {
		try {
			this.buscarPorCodigo(curso.getCodigo());
			return this.cadastrarOuAtualizar(curso);
		} catch (CursoException c) {
			throw c;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean excluir(Long id) {
		try {
			if(this.cursoRepository.findById(id).isPresent()) {
				this.cursoRepository.deleteById(id);
				return Boolean.TRUE;
			}
			throw new CursoException(Mensagens.ERRO_CURSO_NAO_ENCONTRADO.getValor(), HttpStatus.NOT_FOUND);
		}catch (CursoException c) {
			throw c;
		}catch (Exception e) {
			throw new CursoException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<CursoEntity> listar() {
		try {
			return this.cursoRepository.findAll();
		} catch (Exception e) {
			throw new CursoException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public CursoEntity buscarPorCodigo(String codigo) {

		try {
			CursoEntity curso = this.cursoRepository.findByCodigo(codigo);

			if (curso == null) {
				throw new CursoException(Mensagens.ERRO_CURSO_NAO_ENCONTRADO.getValor(), HttpStatus.NOT_FOUND);
			}
			return curso;

		} catch (CursoException c) {
			throw c;
		} catch (Exception e) {
			throw new CursoException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
