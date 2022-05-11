package com.produtos.grupo6.spring.aws.controller;

import java.net.URI;
import java.util.List;

import com.produtos.grupo6.spring.aws.DAO.ClientesDAO;
import com.produtos.grupo6.spring.aws.DAO.PedidosDAO;
import com.produtos.grupo6.spring.aws.DAO.ProdutosDAO;
import com.produtos.grupo6.spring.aws.model.Clientes;
import com.produtos.grupo6.spring.aws.model.Pedidos;
import com.produtos.grupo6.spring.aws.model.Produtos;
import com.produtos.grupo6.spring.aws.util.KafkaUtil;
import com.produtos.grupo6.spring.aws.util.S3Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {	
	
	@Autowired
	private ProdutosDAO produtosDAO;
    private ClientesDAO clientesDAO;
    private PedidosDAO pedidosDAO;


    @GetMapping("/")
    public String viewHomePage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleUploadForm(Model model, String description,
            @RequestParam("file") MultipartFile multipart) {
        
        String fileName = multipart.getOriginalFilename();
        System.out.println("Description: " + description);
        System.out.println("filename: " + fileName);

        String message = "";

        try {
            URI res = S3Util.uploadFile(fileName, multipart.getInputStream());
            System.out.println(res);
            message = res.toString();
            System.out.println("estou enviando uma mensagem : "+message);
           
            KafkaUtil.sendMessage(fileName, message);
        } catch (Exception ex) {
            message = "Error uploading file: " + ex.getMessage();
        }
        model.addAttribute("message", message);
        return "message";
    }
    
    
   @GetMapping("/download")
    public String buscarDadosProdutos(ModelMap model){
    	System.out.println("entrar");
    	List<Produtos> produtos = (List<Produtos>)produtosDAO.findAll();    	
    	model.addAttribute("produtos",produtos);    	
    	for( Produtos p : produtos) {    		
    		System.out.println(p.toString());
    	}    
    	return "download";    	
    }


    @GetMapping("/clientes")
    public String buscarDadosClientes(ModelMap model){    	
    	List<Clientes> clientes = (List<Clientes>)clientesDAO.findAll();    	
    	model.addAttribute("clientes",clientes);    	
    	for( Clientes c : clientes) {    		
    		System.out.println(c.toString());
    	}    
    	return "clientes";    	
    }


    @GetMapping("/pedidos")
    public String buscarDadosPedidos(ModelMap model){    	
    	List<Pedidos> pedidos = (List<Pedidos>)pedidosDAO.findAll();    	
    	model.addAttribute("clientes",pedidos);    	
    	for( Pedidos p : pedidos) {    		
    		System.out.println(p.toString());
    	}    
    	return "pedidos";    	
    }
    
}
