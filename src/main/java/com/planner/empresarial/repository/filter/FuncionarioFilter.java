package com.planner.empresarial.repository.filter;

import java.io.Serializable;

public class FuncionarioFilter implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String nome;
	private String matricula;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
		
}