package com.produtos.grupo6.spring.aws.DTO;

import java.util.List;

public class PedidoDTO {
    private Integer pedidoId;
    private Integer clienteId;
    private Integer quantidadeTotal;
    private List<ProdutoDTO> produtos;

    public PedidoDTO(Integer pedidoId, Integer clienteId, Integer quantidadeTotal, List<ProdutoDTO> produtos) {
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.quantidadeTotal = quantidadeTotal;
        this.produtos = produtos;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }
}
