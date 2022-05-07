package com.produtos.grupo6.spring.aws.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "produto_id")
	private Integer id;

	@Column(name = "nome", length = 50, nullable = false)
	private String nome;

	@Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
	private String descricao;

	@Column(name = "quantidade", nullable = false)
	private Integer quantidade;

	@Column(name = "data_cadastro", nullable = false, columnDefinition = "TIMESTAMP")
	private Timestamp dataCadastro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Timestamp getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public String toString() {
		return "Produtos [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", quantidade=" + quantidade
				+ ", dataCadastro=" + dataCadastro + "]";
	}

}
