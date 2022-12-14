package br.com.saorim.cursomc.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.saorim.cursomc.dto.CategoriaDTO;
import br.com.saorim.cursomc.entities.Categoria;
import br.com.saorim.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> lista = service.findAll();
		List<CategoriaDTO> listaDto = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Integer id){
		Categoria obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = service.fromDto(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDto(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> findAllPerPage(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value="direction", defaultValue = "ASC") String direction){
		
		Page<Categoria> pageList = service.findAllPerPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> pageDto = pageList.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(pageDto);
	}
}
