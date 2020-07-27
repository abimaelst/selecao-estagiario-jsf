package com.planner.empresarial.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.planner.empresarial.model.Cargo;
import com.planner.empresarial.model.Funcionario;
import com.planner.empresarial.repository.Cargos;
import com.planner.empresarial.repository.Funcionarios;
import com.planner.empresarial.service.CadastroFuncionarioService;
import com.planner.empresarial.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroFuncionarioService cadastroFuncionarioService;

	@Inject
	private Cargos cargos;

	private Cargo cargo;

	private PesquisaFuncionariosBean pesquisaFuncionarioBean;

	private Funcionario funcionario;

	@Inject
	private Funcionarios funcionarios;

	private List<Cargo> listCargos;

	private List<Funcionario> listFuncionarios;
	
	public CadastroFuncionarioBean() {
		limpar();
	}

	public void inicializar() {
		
			listCargos = cargos.listOfCargos();
			listFuncionarios = funcionarios.listOfFuncionarios();
		
		
	}

	private void limpar() {
		funcionario = new Funcionario();
	}

	public void salvar() {

		if (this.isEditando()) {

			this.funcionario = cadastroFuncionarioService.editar(this.funcionario);
			limpar();
			FacesUtil.addInfoMessage("Funcionário Editado com sucesso!");

		} else {

			this.funcionario = cadastroFuncionarioService.salvar(this.funcionario);
			limpar();
			FacesUtil.addInfoMessage("Funcionário salvo com sucesso!");

		}

	}

	public boolean isEditando() {
		return funcionario.getId() != null;
	}

	public CadastroFuncionarioService getCadastroFuncionarioService() {
		return cadastroFuncionarioService;
	}

	public void setCadastroFuncionarioService(CadastroFuncionarioService cadastroFuncionarioService) {
		this.cadastroFuncionarioService = cadastroFuncionarioService;
	}
	
	public void Promocao(BigDecimal percentual) {
		
		funcionario.setSalario(funcionario.getSalario().multiply((new BigDecimal("1.00").add(percentual))));
	}

	public Cargos getCargos() {
		return cargos;
	}

	public void setCargos(Cargos cargos) {
		this.cargos = cargos;
	}

	@NotNull
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public PesquisaFuncionariosBean getPesquisaFuncionarioBean() {
		return pesquisaFuncionarioBean;
	}

	public void setPesquisaFuncionarioBean(PesquisaFuncionariosBean pesquisaFuncionarioBean) {
		this.pesquisaFuncionarioBean = pesquisaFuncionarioBean;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;

	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public List<Cargo> getListCargos() {
		return listCargos;
	}

	public void setListCargos(List<Cargo> listCargos) {
		this.listCargos = listCargos;
	}

	public List<Funcionario> getListFuncionarios() {
		return listFuncionarios;
	}

	public void setListFuncionarios(List<Funcionario> listFuncionarios) {
		this.listFuncionarios = listFuncionarios;
	}

}
