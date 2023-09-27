package hotel.controller;

import java.sql.Connection;

import hotel.dao.UsuarioDAO;
import hotel.factory.ConnectionFactory;
import hotel.modelo.Usuario;

public class UsuarioController {
	private UsuarioDAO usuarioDAO;
	
	public UsuarioController() {
		Connection connection = new ConnectionFactory().recuperaConexion();
		this.usuarioDAO = new UsuarioDAO(connection);
	}

	public Usuario login(String usuario, String contrasena) {
		return usuarioDAO.login(usuario, contrasena);
	}
}
