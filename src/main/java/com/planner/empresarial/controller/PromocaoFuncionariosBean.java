package com.planner.empresarial.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.planner.empresarial.model.Cargo;
import com.planner.empresarial.model.Funcionario;
import com.planner.empresarial.repository.Cargos;
import com.planner.empresarial.repository.Funcionarios;
import com.planner.empresarial.repository.filter.FuncionarioFilter;
import com.planner.empresarial.service.CadastroFuncionarioService;
import com.planner.empresarial.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PromocaoFuncionariosBean implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private Cargo cargo;
	
	@Inject
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@Inject
	private Funcionario funcionarioPromocao;
	
	@Inject
	private Cargos cargos;
	
	@Inject
	private FuncionarioFilter filtro;
	
	@Inject
	private Funcionarios funcionarios;
	
	private List<Funcionario> funcionariosPromocao;

	private BigDecimal percentualDePromocao;
		
	/**
	 *  Construtor padrão sem argumentos
	 *   
	 */
	public PromocaoFuncionariosBean() {
	  zerarPercentual();
	}
		
	/**
	 * Popula o atributo da classe percentualDeAumento com o valor ZERO
	 * 
	 */
	public void zerarPercentual() {
		percentualDePromocao = BigDecimal.ZERO;
	}
	
	public void filtrarFuncionariosPromocao(){
		funcionariosPromocao = funcionarios.filtrados(filtro);
	}
	
	/**
	 * Aumenta os salários de todos os objetos da classe (Funcionario) relacionado ao
	 * cargo atual selecionado
	 * 
	 */
	public void aumentarSalarios(){
		calcularSalarios();
		zerarPercentual();
	}
	
	public void aumentarSalario(){
		calcularSalario();
		zerarPercentual();
	}
	
	/**
	 * Faz o cálculo de aumento do salário para cada objeto de uma lista de objetos da classe(Funcionario)
	 * e atualiza no banco.
	 * 
	 */
	private void calcularSalarios() {
		for (Funcionario funcionario : getFuncionariosPromocao()) {
			funcionario.recebePromocao(getPercentualDePromocao());
			cadastroFuncionarioService.editar(funcionario);
		}
		
		FacesUtil.addInfoMessage("Promoção para todos os os Funcionários com cargos selecionado foi realizada com sucesso!");
	}
	
	private void calcularSalario() {
			
		this.funcionarioPromocao.recebePromocao(getPercentualDePromocao());
		cadastroFuncionarioService.editar(this.funcionarioPromocao);
		FacesUtil.addInfoMessage("Promoção efetivada o Funcionário com sucesso!");
				
	}
	
		
	/**
	 * Popula um valor para o objeto cargo
	 * 
	 * @param cargo objeto da classe (Cargo)
	 * 
	 */
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	/**
	 * 
	 * Retorna o objeto cargo
	 * 
	 * @return cargo
	 * 
	 */
	public Cargo getCargo() {
		return cargo;
	}
		
	/**
	 * Retorna uma lista de objetos da classe (Cargo) cadastrados no banco
	 * 
	 * @return cargos lista de objetos da classe (Cargo)
	 * 
	 */
	public List<Cargo> getCargos(){
	    return cargos.listOfCargos();
	    
	}
	
	
	public List<Funcionario> getFuncionariosPromocao() {
		return funcionariosPromocao;
	}

	public void setFuncionariosPromocao(List<Funcionario> funcionariosPromocao) {
		this.funcionariosPromocao = funcionariosPromocao;
	}

	/**
	 * Retorna o valor do atributo da classe percentualDeAumento
	 * 
	 * @return percentualDeAumento objeto do tipo (BigDecimal)
	 * 
	 */
	public BigDecimal getPercentualDePromocao() {
		return percentualDePromocao;
	}

	/**
	 * Popula o valor do atributo da classe percentualDeAumento
	 * 
	 * @param percentualDeAumento objeto do tipo (BigDecimal)
	 * 
	 */
	public void setPercentualDePromocao(BigDecimal percentualDePromocao) {
		this.percentualDePromocao = percentualDePromocao;
	}

	public FuncionarioFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(FuncionarioFilter filtro) {
		this.filtro = filtro;
	}

	public Funcionario getFuncionarioPromocao() {
		return funcionarioPromocao;
	}

	public void setFuncionarioPromocao(Funcionario funcionarioPromocao) {
		this.funcionarioPromocao = funcionarioPromocao;
	}
	
	
}
