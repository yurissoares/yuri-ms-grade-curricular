package com.yuri.cliente.escola.gradecurricular.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class CursoDto extends RepresentationModel<CursoDto> {

	private Long id;
	
	@NotBlank(message = "Informe o nome do curso.")
	private String nome;
	
	@NotBlank(message = "Informe o código da matéria.")
	private String codigo;
	
	private List<Long> materias;
	
}
