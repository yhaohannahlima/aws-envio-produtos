package com.produtos.grupo6.spring.aws.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.google.gson.Gson;
import com.produtos.grupo6.spring.aws.DAO.ClientesDAO;
import com.produtos.grupo6.spring.aws.DAO.PedidosDAO;
import com.produtos.grupo6.spring.aws.DAO.ProdutosDAO;
import com.produtos.grupo6.spring.aws.model.Clientes;
import com.produtos.grupo6.spring.aws.model.Pedidos;
import com.produtos.grupo6.spring.aws.model.Produtos;
import com.produtos.grupo6.spring.aws.service.RedisService;
import com.produtos.grupo6.spring.aws.service.RestElasticService;
import com.produtos.grupo6.spring.aws.util.KafkaUtil;
import com.produtos.grupo6.spring.aws.util.S3Util;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private ProdutosDAO produtosDAO;
    @Autowired
    private ClientesDAO clientesDAO;
    @Autowired
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
            System.out.println("estou enviando uma mensagem : " + message);

            KafkaUtil.sendMessage(fileName, message);
        } catch (Exception ex) {
            message = "Error uploading file: " + ex.getMessage();
        }
        model.addAttribute("message", message);
        return "message";
    }

  
    @GetMapping("/download")
    public String buscarDados(ModelMap model) {
        List<Produtos> produtos = (List<Produtos>) produtosDAO.findAll();
        model.addAttribute("produtos", produtos);
        return "download";
    }
   
   @GetMapping("/cadastro")
   public String Cadastro(ModelMap model) {
	   
	   return "cadastro";
   }

   @GetMapping("/cadastrar_cliente")
   public String CadastroCliente(ModelMap model) {
	   model.addAttribute("cliente", new Clientes());
	   return "cadastrar_cliente";
   }

     
    @GetMapping("/produtos")
    public String buscarDadosProdutos(ModelMap model) {
        List<Produtos> produtos = (List<Produtos>) produtosDAO.findAll();
        
        for(Produtos p : produtos) {
        	try {
				System.out.println(RestElasticService.post(p));
			} catch(ClientProtocolException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {				
				e.printStackTrace();
			} catch (SecurityException e) {				
				e.printStackTrace();
			} catch (IllegalArgumentException e) {				
				e.printStackTrace();
			} catch (IllegalAccessException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
        	
        }
        model.addAttribute("produtos", produtos);
        return "/produtos";
    }

    @GetMapping("/listar_clientes")
    public String buscarDadosClientes(ModelMap model){    	  		
    		List<Clientes> clientes = (List<Clientes>)clientesDAO.findAll();    		
    		model.addAttribute("clientes",clientes);    	
    	return "listar_clientes";    	
    }    

    @GetMapping("/pedidos")
    public String buscarDadosPedidos(ModelMap model) {
        List<Pedidos> pedidos = (List<Pedidos>) pedidosDAO.findAll();
        model.addAttribute("pedidos", pedidos);
        return "pedidos";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Clientes cliente ,RedirectAttributes attr){
    	RedisService jedis = new RedisService();

        Gson gson = new Gson();
        try {
            Clientes  resp = clientesDAO.save(cliente);
            String json = gson.toJson(cliente); 
            String idCliente = "Cliente_" + Integer.toString(resp.getId());
            System.out.println(idCliente);
            jedis.write(idCliente, json, 1000);            
            System.out.println("Dado salvo no redis: " + jedis.read(idCliente));

            
        } catch (Exception e) {
            attr.addFlashAttribute("Error","Não foi possível salvar o Cliente!");
            e.getStackTrace();
            return "salvar";   
        }
        finally{
            jedis.close();
        }

        attr.addFlashAttribute("Sucess","Cliente salvo com sucesso.");      
        return "cadastro";
    }
}
