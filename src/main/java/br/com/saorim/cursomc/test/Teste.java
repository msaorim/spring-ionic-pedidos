package br.com.saorim.cursomc.test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.saorim.cursomc.entities.Categoria;
import br.com.saorim.cursomc.entities.Cidade;
import br.com.saorim.cursomc.entities.Estado;
import br.com.saorim.cursomc.entities.Produto;
import br.com.saorim.cursomc.repositories.CategoriaRepository;
import br.com.saorim.cursomc.repositories.CidadeRepository;
import br.com.saorim.cursomc.repositories.EstadoRepository;
import br.com.saorim.cursomc.repositories.ProdutoRepository;

@Configuration
public class Teste implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;

	@Override
	public void run(String... args) throws Exception {
				
		var cat1 = new Categoria(null, "Informática");
		var cat2 = new Categoria(null, "Escritório");
		
		var p1 = new Produto(null, "Computador", 2000.0);
		var p2 = new Produto(null, "Impressora", 800.0);
		var p3 = new Produto(null, "Mouse", 80.0);
		
		p1.setCategorias(Arrays.asList(cat1));
		p2.setCategorias(Arrays.asList(cat1, cat2));
		p3.setCategorias(Arrays.asList(cat1));
		
		cat1.setProdutos(Arrays.asList(p1, p2, p3));
		cat2.setProdutos(Arrays.asList(p2));
		
		var est1 = new Estado(null, "Minas Gerais");
		var est2 = new Estado(null, "São Paulo");
		
		var c1 = new Cidade(null, "Uberlândia", est1);
		var c2 = new Cidade(null, "São Paulo", est2);
		var c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
	}

}
