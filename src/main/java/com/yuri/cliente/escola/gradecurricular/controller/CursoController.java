package com.yuri.cliente.escola.gradecurricular.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuri.cliente.escola.gradecurricular.entity.CursoEntity;
import com.yuri.cliente.escola.gradecurricular.model.CursoModel;
import com.yuri.cliente.escola.gradecurricular.model.Response;
import com.yuri.cliente.escola.gradecurricular.service.ICursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

	@Autowired
	private ICursoService cursoService;

	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarCurso(@Valid @RequestBody CursoModel curso) {
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.cadastrar(curso));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<Response<List<CursoEntity>>> listarCurso() {
		Response<List<CursoEntity>> response = new Response<>();
		response.setData(this.cursoService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Response<CursoEntity>> consultarCursoPorMateria(@PathVariable String codigo) {
		Response<CursoEntity> response = new Response<>();
		response.setData(this.cursoService.buscarPorCodigo(codigo));
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping
	public ResponseEntity<Response<Boolean>> atualizarCurso(@Valid @RequestBody CursoModel curso) {
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.atualizar(curso));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirCurso( @PathVariable Long id) {
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.excluir(id));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
