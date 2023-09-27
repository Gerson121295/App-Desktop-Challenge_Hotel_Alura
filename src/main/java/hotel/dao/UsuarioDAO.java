package hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hotel.modelo.Usuario;

public class UsuarioDAO {
	private Connection connection;
	
	public UsuarioDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Usuario login(String usuario, String contrasena) {
		Usuario user = null;
		
		try{
			final PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?");
					
			try(statement){
				statement.setString(1, usuario);
				statement.setString(2, contrasena);
				
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				try(resultSet){
					while(resultSet.next()) {
						user = new Usuario();
						user.setId(resultSet.getInt("ID"));
						user.setUsuario(resultSet.getString("USUARIO"));
						user.setContrasena(resultSet.getString("CONTRASENA"));
						user.setNombre(resultSet.getString("NOMBRE"));
						user.setApellido(resultSet.getString("EMAIL"));
						user.setEmail(resultSet.getString("EMAIL"));
						user.setTelefono(resultSet.getString("TELEFONO"));
					}
				}
				return user;
			}
		}catch(SQLException e) {
			throw new RuntimeException (e);
		}
	}

}
