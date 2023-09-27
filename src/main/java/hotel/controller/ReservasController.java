package hotel.controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import hotel.dao.ReservaDAO;
import hotel.factory.ConnectionFactory;
import hotel.modelo.Reserva;

//Controller es la intermediaria entre la clase reserva DAO y la View o vista de la aplicacion.

public class ReservasController {
	
	private ReservaDAO reservaDAO;
	
	public ReservasController() {
		Connection connection = new ConnectionFactory().recuperaConexion();
		this.reservaDAO = new ReservaDAO(connection);
	}
	
	public void guardar(Reserva reserva) {
		this.reservaDAO.guardar(reserva);
	}
	
	public List<Reserva> buscar(){
		return this.reservaDAO.buscar();
	}
	
	public List<Reserva> buscarId(String id){
		return this.reservaDAO.buscarId(id);
	}
	
	public void actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
		this.reservaDAO.Actualizar(fechaE, fechaS, valor, formaPago, id);
	}
	
	public void Eliminar(Integer id) {
		this.reservaDAO.eliminar(id);
	}

}


