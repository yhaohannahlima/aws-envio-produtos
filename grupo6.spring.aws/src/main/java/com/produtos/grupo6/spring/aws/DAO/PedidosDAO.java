package com.produtos.grupo6.spring.aws.DAO;


import org.springframework.data.repository.CrudRepository;
import com.produtos.grupo6.spring.aws.model.Produtos;

public interface PedidosDAO extends CrudRepository<Produtos, Integer>{
	
}