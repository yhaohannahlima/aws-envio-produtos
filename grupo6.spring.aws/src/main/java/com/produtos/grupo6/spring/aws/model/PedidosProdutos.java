package com.produtos.grupo6.spring.aws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos_produtos_id")
public class PedidosProdutos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedidos_produtos_id")
    private Integer pedidosProdutosId;

    @Column(name = "pedido_id")
    private Integer pedidoId;

    @Column(name = "produto_id")
    private Integer produtoId;

    @Column(name = "quantidade")
    private Integer quantidade;

	public Integer getPedidosProdutosId() {
		return pedidosProdutosId;
	}

	public void setPedidosProdutosId(Integer pedidosProdutosId) {
		this.pedidosProdutosId = pedidosProdutosId;
	}

	public Integer getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Integer pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "PedidosProdutos [pedidosProdutosId=" + pedidosProdutosId + ", pedidoId=" + pedidoId + ", produtoId="
				+ produtoId + ", quantidade=" + quantidade + "]";
	} 
}