package br.com.saorim.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.saorim.cursomc.entities.Categoria;
import br.com.saorim.cursomc.entities.Cidade;
import br.com.saorim.cursomc.entities.Cliente;
import br.com.saorim.cursomc.entities.Endereco;
import br.com.saorim.cursomc.entities.Estado;
import br.com.saorim.cursomc.entities.ItemPedido;
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
import br.com.saorim.cursomc.repositories.ItemPedidoRepository;
import br.com.saorim.cursomc.repositories.PagamentoRepository;
import br.com.saorim.cursomc.repositories.PedidoRepository;
import br.com.saorim.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
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
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;


	public void instantietaTestDatabase() throws ParseException {

		// =============================================================

		var cat1 = new Categoria(null, "Informática");
		var cat2 = new Categoria(null, "Escritório");
		var cat3 = new Categoria(null, "Cama, Mesa e Banho");
		var cat4 = new Categoria(null, "Eletrônicos");
		var cat5 = new Categoria(null, "Jardinagem");
		var cat6 = new Categoria(null, "Decoração");
		var cat7 = new Categoria(null, "Perfumaria");
		var cat8 = new Categoria(null, "Cozinha");
		var cat9 = new Categoria(null, "Celular");
		var cat10 = new Categoria(null, "Brinquedos");
		var cat11 = new Categoria(null, "Jogos");
		var cat12 = new Categoria(null, "Esporte");
		var cat13 = new Categoria(null, "Comida");
		var cat14 = new Categoria(null, "Bebida");
		var cat15 = new Categoria(null, "Lazer");

		var p1 = new Produto(null, "Computador", 2000.0);
		var p2 = new Produto(null, "Impressora", 800.0);
		var p3 = new Produto(null, "Mouse", 80.0);
		var p4 = new Produto(null, "Mesa de Escritório", 300.0);
		var p5 = new Produto(null, "Toalha", 50.0);
		var p6 = new Produto(null, "Colcha", 200.0);
		var p7 = new Produto(null, "TV Ture Color", 1200.0);
		var p8 = new Produto(null, "Roçadeira", 800.0);
		var p9 = new Produto(null, "Abajour", 100.0);
		var p10 = new Produto(null, "Pendente", 180.0);
		var p11 = new Produto(null, "Shampoo", 90.0);

		p1.setCategorias(Arrays.asList(cat1, cat4));
		p2.setCategorias(Arrays.asList(cat1, cat2, cat4));
		p3.setCategorias(Arrays.asList(cat1, cat4));
		p4.setCategorias(Arrays.asList(cat2));
		p5.setCategorias(Arrays.asList(cat3));
		p6.setCategorias(Arrays.asList(cat3));
		p7.setCategorias(Arrays.asList(cat4));
		p8.setCategorias(Arrays.asList(cat5));
		p9.setCategorias(Arrays.asList(cat6));
		p10.setCategorias(Arrays.asList(cat6));
		p11.setCategorias(Arrays.asList(cat7));

		cat1.setProdutos(Arrays.asList(p1, p2, p3));
		cat2.setProdutos(Arrays.asList(p2, p4));
		cat3.setProdutos(Arrays.asList(p5, p6));
		cat4.setProdutos(Arrays.asList(p1, p2, p3, p7));
		cat5.setProdutos(Arrays.asList(p8));
		cat6.setProdutos(Arrays.asList(p9, p10));
		cat7.setProdutos(Arrays.asList(p11));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5));
		categoriaRepository.saveAll(Arrays.asList(cat6, cat7, cat8, cat9, cat10));
		categoriaRepository.saveAll(Arrays.asList(cat11, cat12, cat13, cat14, cat15));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
		produtoRepository.saveAll(Arrays.asList(p7, p8, p9, p10, p11));

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

		var ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
		var ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0);
		var ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}
}
