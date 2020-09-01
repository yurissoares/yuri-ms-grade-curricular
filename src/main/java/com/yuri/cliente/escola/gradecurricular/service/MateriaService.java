package com.yuri.cliente.escola.gradecurricular.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.yuri.cliente.escola.gradecurricular.controller.MateriaController;
import com.yuri.cliente.escola.gradecurricular.dto.MateriaDto;
import com.yuri.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.yuri.cliente.escola.gradecurricular.exception.MateriaException;
import com.yuri.cliente.escola.gradecurricular.repository.IMateriaRepository;

@CacheConfig(cacheNames = "materia")
@Service
public class MateriaService implements IMateriaService {

	private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte.";
	private IMateriaRepository materiaRepository;
	private ModelMapper mapper;

	@Autowired
	public MateriaService(IMateriaRepository materiaRepository) {
		this.mapper = new ModelMapper();
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean atualizar(MateriaDto materia) {
		try {
			this.buscar(materia.getId());
			MateriaEntity materiaEntityAtualizada = this.mapper.map(materia, MateriaEntity.class);
			this.materiaRepository.save(materiaEntityAtualizada);

			return Boolean.TRUE;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean excluir(Long id) {
		try {
			this.buscar(id);
			this.materiaRepository.deleteById(id);
			return Boolean.TRUE;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size()<3")
	@Override
	public List<MateriaDto> listar() {
		try {
			List<MateriaDto> materiaDto = this.mapper.map(this.materiaRepository.findAll(),
					new TypeToken<List<MateriaDto>>() {
					}.getType());

			materiaDto.forEach(materia -> {
				materia.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).buscarMateria(materia.getId()))
						.withSelfRel());
			});

			return materiaDto;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(key = "#id")
	@Override
	public MateriaDto buscar(Long id) {
		try {
			Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
			if (materiaOptional.isPresent()) {
				return this.mapper.map(materiaOptional.get(), MateriaDto.class);
			}
			
			throw new MateriaException("Matéria não encontrada", HttpStatus.NOT_FOUND);
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean cadastrar(MateriaDto materia) {
		try {
			MateriaEntity materiaEntity = this.mapper.map(materia, MateriaEntity.class);
			this.materiaRepository.save(materiaEntity);
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<MateriaDto> listarPorHoraMinima(int horaMinima) {
		try {
			List<MateriaDto> materiaDto = this.mapper.map(this.materiaRepository.findByHoraMinima(horaMinima),
					new TypeToken<List<MateriaDto>>() {}.getType());

			materiaDto.forEach(materia -> {
				materia.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).buscarMateria(materia.getId()))
						.withSelfRel());
			});
			return materiaDto;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<MateriaDto> listarPorFrequencia(int frequencia) {
		try {
			List<MateriaDto> materiaDto = this.mapper.map(this.materiaRepository.findByFrequencia(frequencia),
					new TypeToken<List<MateriaDto>>() {
					}.getType());

			materiaDto.forEach(materia -> {
				materia.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).buscarMateria(materia.getId()))
						.withSelfRel());
			});

			return materiaDto;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
