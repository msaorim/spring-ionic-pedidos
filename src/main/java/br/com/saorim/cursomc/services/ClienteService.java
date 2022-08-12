package br.com.saorim.cursomc.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saorim.cursomc.dto.ClienteDTO;
import br.com.saorim.cursomc.dto.ClienteNewDTO;
import br.com.saorim.cursomc.entities.Cidade;
import br.com.saorim.cursomc.entities.Cliente;
import br.com.saorim.cursomc.entities.Endereco;
import br.com.saorim.cursomc.entities.enums.TipoCliente;
import br.com.saorim.cursomc.repositories.ClienteRepository;
import br.com.saorim.cursomc.repositories.EnderecoRepository;
import br.com.saorim.cursomc.services.exceptions.DataIntegrityException;
import br.com.saorim.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Cliente> findAll(){
		return repository.findAll();
	}
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		
		Cliente newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Este Cliente possui Pedidos!!!");
		}
	}

	public Page<Cliente> findAllPerPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	
	public Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail());
		
	}
	
	public Cliente fromDto(ClienteNewDTO objDto) {
		String nome = objDto.getNome();
		String email = objDto.getEmail();
		String cpf = objDto.getCpfOuCnpj();
		TipoCliente tipo = TipoCliente.toEnum(objDto.getTipo());
		
		Cliente cli = new Cliente(null, nome, email, cpf, tipo);
		
		String logr = objDto.getLogradouro();
		String num = objDto.getNumero();
		String compl = objDto.getComplemento();
		String bairro = objDto.getBairro();
		String cep = objDto.getCep();
		
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Cliente cliente = cli;
		
		Endereco end = new Endereco(null, logr, num, compl, bairro, cep, cid, cliente);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
