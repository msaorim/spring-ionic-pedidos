package br.com.saorim.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.saorim.cursomc.entities.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
