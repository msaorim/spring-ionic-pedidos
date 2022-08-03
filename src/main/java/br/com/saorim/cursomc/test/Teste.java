package br.com.saorim.cursomc.test;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.saorim.cursomc.entities.Categoria;
import br.com.saorim.cursomc.entities.Cidade;
import br.com.saorim.cursomc.entities.Cliente;
import br.com.saorim.cursomc.entities.Endereco;
import br.com.saorim.cursomc.entities.Estado;
import br.com.saorim.cursomc.entities.PagamentoComBoleto;
import br.com.saorim.cursomc.entities.PagamentoComCartao;
import br.com.saorim.cursomc.entities.Pedido;
import br.com.saorim.cursomc.entities.Produto;
import br.com.saorim.cursomc.entities.enums.EstadoPagamento;
import br.com.saorim.cursomc.entities.enums.TipoCliente;
import br.com.saorim.cursomc.repositories.CategoriaRepository;
import br.com.saorim.cursomc.repositories.CidadeRepository;
import br.com.saorim.cursomc.repositories.ClienteRepository;
import br.com.saorim.cursomc.repositories.EnderecoRepository;
import br.com.saorim.cursomc.repositories.EstadoRepository;
import br.com.saorim.cursomc.repositories.PagamentoRepository;
import br.com.saorim.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Override
	public void run(String... args) throws Exception {
				
		// =============================================================
		
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
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		// =============================================================
		
		var est1 = new Estado(null, "Minas Gerais");
		var est2 = new Estado(null, "São Paulo");
		
		var c1 = new Cidade(null, "Uberlândia", est1);
		var c2 = new Cidade(null, "São Paulo", est2);
		var c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		// =============================================================

		var cli1 = new Cliente(null, "Maria Silva", "maria@email.com", "098.787.666-33", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().add("(11) 98876-2314");
		cli1.getTelefones().add("(15) 94545-9898");
		
		var e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220-834", c1, cli1);
		var e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38150-000", c2, cli1);
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		// =============================================================
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		var ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		var ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		var pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		var pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		clienteRepository.save(cli1);

		// =============================================================
	}

}
