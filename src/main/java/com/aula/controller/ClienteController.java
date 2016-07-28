package com.aula.controller;
import java.util.Date;

import javax.inject.Inject;
import javax.transaction.Transactional;
import com.aula.dao.ClienteDAO;
import com.aula.model.Cliente;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("/cliente")
public class ClienteController {

	@Inject
	private Result result;
	@Inject
	private ClienteDAO clienteDAO;

	@Post
	@Path("/salvar")
	@Transactional
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void salvar(Cliente cliente) {
		try {

			cliente.setDataNascimento(new Date());
			clienteDAO.salvar(cliente);
			result.use(Results.status()).ok();
		} catch (Exception e) {
			e.printStackTrace();
			result.use(Results.status()).badRequest(e.getMessage());
		}
	}

	@Get
	@Path("/clientes")
	public void listarTodos() {
		try {
			result.use(Results.json()).from(clienteDAO.listAll()).serialize();
		} catch (Exception e) {
			result.use(Results.status()).badRequest(e.getMessage());
			e.printStackTrace();
		}
	}

	@Get
	@Path("/cliente/{codigo}")
	public void listarPorCodigo(Long codigo) {
		try {
			result.use(Results.json()).from(clienteDAO.buscarPorCodigo(codigo)).serialize();
		} catch (Exception e) {
			result.use(Results.status()).badRequest(e.getMessage());
			e.printStackTrace();
		}
	}

	@Delete
	@Path(value = "/{codigo}")
	public void remover(Long codigo) {
		try {
			Cliente cliente = clienteDAO.buscarPorCodigo(codigo);
			clienteDAO.excluir(cliente);
			result.use(Results.status()).ok();
		} catch (Exception e) {
			result.use(Results.status()).badRequest(e.getMessage());
			e.printStackTrace();
		}
	}

	@Put
	@Path(value = "/")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editarUsuario(Cliente cliente) {
		try {
			clienteDAO.merge(cliente);
			result.use(Results.json()).from(clienteDAO.listAll()).serialize();
		} catch (Exception e) {
			result.use(Results.status()).badRequest(e.getMessage());
			e.printStackTrace();
		}

	}

}
