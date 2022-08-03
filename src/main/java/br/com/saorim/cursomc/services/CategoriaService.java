package br.com.saorim.cursomc.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.saorim.cursomc.entities.Categoria;
import br.com.saorim.cursomc.repositories.CategoriaRepository;
import br.com.saorim.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}
	
	public Categoria findById(Integer id){
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
}
