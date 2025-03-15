package ex.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ex.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	// Método de consulta para pesquisar clientes pelo nome (ignorando maiúsculas/minúsculas)
    Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}