package hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hotel.modelo.Reserva;

//DAO Hace el puente entre la app y la BD

public class ReservaDAO {
	
	private Connection connection;
	
	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	
	public void guardar(Reserva reserva) {
	
		try {
		String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_pago) VALUES (?,?,?,?)";
	
		try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstm.setDate(1, reserva.getFechaE());
			pstm.setDate(2, reserva.getFechaS());
			pstm.setString(3, reserva.getValor());
			pstm.setString(4, reserva.getFormaPago());
			
			pstm.executeUpdate();
			
			try (ResultSet rst = pstm.getGeneratedKeys()){
				while(rst.next()) {
					reserva.setId(rst.getInt(1));
				}
			}
		}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Reserva> buscar(){
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas";
			
			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.execute();
				transformarResultSetEnReserva(reservas, pstm);//recibe la lusta reservas y el pstm para funcionar
			}
			return reservas;
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Reserva> buscarId(String id){
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		try {
			
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas WHERE id = ?";
		
			try (PreparedStatement pstm = connection.prepareStatement(sql)){
					pstm.setString(1, id);
					pstm.execute();
					
					transformarResultSetEnReserva(reservas, pstm);
			}
			return reservas;
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void eliminar(Integer id) {
		try (PreparedStatement stm = connection.prepareStatement("DELETE FROM reservas WHERE id = ?")) {
			stm.setInt(1, id);
			stm.execute();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void Actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
		try(PreparedStatement stm = connection
				.prepareStatement("UPDATE reservas SET fecha_entrada = ?, fecha_salida = ?, valor = ?, forma_pago = ? WHERE id = ?")) {
			stm.setDate(1, fechaE);
			stm.setDate(2, fechaS);
			stm.setString(3, valor);
			stm.setString(4, formaPago);
			stm.setInt(5, id);
			stm.execute();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	private void transformarResultSetEnReserva(List<Reserva> reservas, PreparedStatement pstm) {
		try(ResultSet rst = pstm.getResultSet()){
			while(rst.next()) {
				Reserva reserva = new Reserva(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getString(4), rst.getString(5)); //se le envia el tipo de dato de los atributos que espera reservas.
				//se le envia los tipos de datos que la reserva espera: rst.getInt(1)= id, rst.getDate(2), rst.getDate(3)=fecha de entrada y salida. y  rst.getString(4), rst.getString(5) = valor y forma de pago  
				reservas.add(reserva); //agrega la reserva a la lista. 
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	
	
}



