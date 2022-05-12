package com.produtos.grupo6.spring.aws.DAO;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.produtos.grupo6.spring.aws.model.Clientes;

@Repository
public interface ClientesDAO extends CrudRepository<Clientes, Integer>{
	
}
