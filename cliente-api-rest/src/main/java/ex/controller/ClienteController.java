package ex.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ex.model.Cliente;
import ex.model.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repository;
	
	@PostMapping
	public ResponseEntity<ClienteFormRequest> salvar(@RequestBody ClienteFormRequest request) {
		
		Cliente cliente = request.toModel();
		
		repository.save(cliente);
		
		System.out.println(cliente);//Mostra no console
		
		return ResponseEntity.ok(ClienteFormRequest.fromModel(cliente));
	}
	
//	@GetMapping
//	public List<ClienteFormRequest> getLista() {
//		return repository.findAll()
//				.stream()
//				.map( ClienteFormRequest::fromModel )
//				.collect(Collectors.toList());
//	}
	
	// Método para obter lista paginada com filtro por nome
    @GetMapping
    public ResponseEntity<List<ClienteFormRequest>> getLista(
            @RequestParam Optional<String> search,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> itemsPerPage) {
        
    	// Definindo o número da página (padrão para a página 1)
        int pageNumber = page.orElse(1) - 1; // Page começa de 0 no Spring Data JPA
        int limit = itemsPerPage.orElse(4); // Se não for fornecido, utiliza 4 itens por página (ou qualquer valor padrão)
        
        Pageable pageable = PageRequest.of(pageNumber, limit); // Tamanho da página é passado como o valor de itemsPerPage

        // Se a pesquisa por nome for fornecida, filtra os clientes com base no nome
        Page<Cliente> clientesPage;
        if (search.isPresent()) {
            clientesPage = repository.findByNomeContainingIgnoreCase(search.get(), pageable);
        } else {
            // Caso não haja pesquisa, retorna todos os clientes paginados
            clientesPage = repository.findAll(pageable);
        }

        // Converte a lista de clientes para o formato do DTO (ClienteFormRequest)
        List<ClienteFormRequest> clientes = clientesPage.stream()
                .map(ClienteFormRequest::fromModel)
                .collect(Collectors.toList());

        // Retorna a resposta com o cabeçalho X-Total-Pages
        return ResponseEntity.ok()
                .header("X-Total-Pages", String.valueOf(clientesPage.getTotalPages()))
                .body(clientes);
    }
	
	@GetMapping("{id}")
	public ResponseEntity<ClienteFormRequest> getById(@PathVariable Long id) {
		return repository.findById(id)
				.map( ClienteFormRequest::fromModel )
				.map( clienteFR -> ResponseEntity.ok(clienteFR) )
				.orElseGet( () -> ResponseEntity.notFound().build() );
				//.orElseGet( () -> ResponseEntity.notFound().build() );
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(
			
			@PathVariable Long id,
			@RequestBody ClienteFormRequest request) {
		
		Optional<Cliente> clienteExistente = repository.findById(id);
		
		if (clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente cliente = request.toModel();
		cliente.setId(id);
		repository.save(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return repository.findById(id)
				.map( cliente -> {
					repository.delete(cliente);
					return ResponseEntity.noContent().build();
				})
				.orElseGet( () -> ResponseEntity.notFound().build() );
	}
}