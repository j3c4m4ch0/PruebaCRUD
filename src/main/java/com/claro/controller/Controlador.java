package com.claro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claro.exception.ResourceNotFoundException;
import com.claro.modelo.Cliente;
import com.claro.repositorio.ClienteRepositorio;

@RestController
@RequestMapping("/api/v1")
public class Controlador {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	
	// Consultar todos los clientes
	@GetMapping("/listarClientes")
	public List<Cliente> getAllClientes() {
		
		return clienteRepositorio.findAll();
		
	}
	
	
	// Consultar Cliente
	@GetMapping("/consultarCliente/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable(value = "id") long clienteId) throws ResourceNotFoundException {
		
		Cliente cliente = clienteRepositorio.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrando con el id :: " + clienteId));
		return ResponseEntity.ok().body(cliente);
		
	}
	
	
	// Crear Cliente
	@PostMapping("/crearCliente")
	public Cliente createCliente(@RequestBody Cliente cliente) {
		
		return clienteRepositorio.save(cliente);
		
	}
	
	
	// Actualizar Cliente
	@PutMapping("/actualizarCliente/{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable(value = "id") long clienteId, @RequestBody Cliente clienteDetalles) throws ResourceNotFoundException {
		
		Cliente cliente = clienteRepositorio.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrando con el id :: " + clienteId));
		
		cliente.setCedula(clienteDetalles.getCedula());
		cliente.setNombre(clienteDetalles.getNombre());
		cliente.setCelular(clienteDetalles.getCelular());
		
		clienteRepositorio.save(cliente);
		
		return ResponseEntity.ok().body(cliente);
		
	}
	
	
	// Eliminar Cliente
	@DeleteMapping("/borrarCliente/{id}")
	public Map<String, Boolean> deleteCliente(@PathVariable(value = "id") long clienteId) throws ResourceNotFoundException {
		
		Cliente cliente = clienteRepositorio.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrando con el id :: " + clienteId));

		clienteRepositorio.delete(cliente);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
		
	}
	

}
