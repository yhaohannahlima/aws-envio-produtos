package com.produtos.grupo6.spring.aws.DAO;


import org.springframework.data.repository.CrudRepository;
import com.produtos.grupo6.spring.aws.model.Produtos;

public interface ClientesDAO extends CrudRepository<Produtos, Integer>{
	
}