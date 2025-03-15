package ex.controller;

import java.time.LocalDate;

import ex.model.Cliente;

public class ClienteFormRequest {
	
	private Long id;
	private LocalDate nascimento;
	private String cpf;
	private String nome;
	private String endereco;
	private String telefone;
	private String email;
	private LocalDate datacadastro;
	
	public ClienteFormRequest() {
		super();
	}

	public ClienteFormRequest(Long id, LocalDate nascimento, String cpf, String nome, String endereco, String telefone,
			String email, LocalDate datacadastro) {
		super();
		this.id = id;
		this.nascimento = nascimento;
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
		this.datacadastro = datacadastro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(LocalDate datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	public Cliente toModel() {
		return new Cliente(id, nascimento, cpf, nome, endereco, telefone, email, datacadastro);
	}
	
	public static ClienteFormRequest fromModel(Cliente cliente) {
		return new ClienteFormRequest(cliente.getId(), cliente.getNascimento(), cliente.getCpf(), 
				cliente.getNome(), cliente.getEndereco(), cliente.getTelefone(), cliente.getEmail(), 
				cliente.getDatacadastro());
	}
}