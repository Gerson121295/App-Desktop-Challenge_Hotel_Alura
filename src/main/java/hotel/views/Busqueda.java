package hotel.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import hotel.controller.HuespedesController;
import hotel.controller.ReservasController;
import hotel.modelo.Huespedes;
import hotel.modelo.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	
	String reserva;
	String huespedes;
	
	//variable del tipo ReservasController
	private ReservasController reservaController;
	private HuespedesController huespedesController;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		
		//usar la variable reservaController para Instanciar una clase del tipo ReservaController
		this.reservaController = new ReservasController();
		this.huespedesController = new HuespedesController();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();//DefaultTableModel ayuda a configurar como vamos a mostrar los datos a los usuarios.
		modelo.addColumn("Numero de Reserva"); //por medio del metodo getModel() agregamos las columnas.
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //tabla se ajuste a cada columna dependiendo de la informacion
		
		JScrollPane scroll_table = new JScrollPane(tbReservas); //scroll para poder visualizar los datos.
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		tbHuespedes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //tabla se ajuste a cada columna dependiendo de la informacion
		
		
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		final JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		final JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		
		JPanel btnbuscar = new JPanel();
		
		//Agregar evento: Abrir con windows builder y dar clic derecho en el boton BUSCAR -->Clic en add event handler --> mouse --> mouseClicked
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				if(txtBuscar.getText().equals("")) {
					LlenarTablaReservas(); //al darle clic al boton BUSCAR llama al metodo LlenarTablaReservas(); de las reservas
					LlenarTablaHuespedes();//Muestra todos los huespedes
				}else {
					LlenarTablaReservasId();//Muestra reserva por id
					LlenarTablaHuespedId();//Buscar huesperd por id
				}
				
			}
		});
		
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		
		
		JPanel btnEditar = new JPanel();
		
		//Agregar evento: Abrir con windows builder y dar clic derecho en el boton EDITAR -->Clic en add event handler --> mouse --> mouseClicked
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow(); //la variable filaReserva guarda el valor de cual fila de la tabla reservas fue seleccionada.
				int filaHuespedes = tbHuespedes.getSelectedRow(); //la variable filaHuespedes guarda el valor de cual fila de la tabla huespedes fue seleccionada.
				
				if(filaReservas >= 0) {
					actualizarReserva();
					limpiarTabla();
					LlenarTablaReservas();
					LlenarTablaHuespedes();
				}else if(filaHuespedes >= 0) {
					actualizarHuesped();
					limpiarTabla();
					LlenarTablaReservas();
					LlenarTablaHuespedes();
				}
			}
		});
		
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();
				
				if(filaReservas >= 0) {
					
					reserva = tbReservas.getValueAt(filaReservas, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?");
					
					if(confirmar == JOptionPane.YES_OPTION) {
						String valor = tbReservas.getValueAt(filaReservas, 0).toString(); //se comvierte el dato a String
						reservaController.Eliminar(Integer.valueOf(valor));//pasamos el id a eliminar en entero
						JOptionPane.showMessageDialog(contentPane, "Registro Eliminado");
						limpiarTabla();
						LlenarTablaReservas();
						LlenarTablaHuespedes();
					}
					
					}else if(filaHuespedes >= 0) {
						huespedes = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
						int confirmar2 = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?");
						
						if(confirmar2 == JOptionPane.YES_OPTION) {
							String valor2 = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
							huespedesController.Eliminar(Integer.valueOf(valor2));
							JOptionPane.showMessageDialog(contentPane, "Registro Eliminado");
							limpiarTabla();
							LlenarTablaReservas();
							LlenarTablaHuespedes();
						}
					
					}else {
						JOptionPane.showMessageDialog(null, "Error fila no seleccionada, por favor realice una busqueda y seleccione una fila para eliminar");
					}
			}
		});
		
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
	
	//Metodos
		//Metodo Buscar Reservas - Mostrar las reservas
	private List<Reserva> buscarReservas(){
		return this.reservaController.buscar();//metodo buscar dentro de ReservaController
	}
	
	//Metodo - Buscar reserva por id
	private List<Reserva> buscarReservasId(){
		return this.reservaController.buscarId(txtBuscar.getText());
	}
	
	//Metodo Buscar Huesped - Mostrar las huespedes
	private List<Huespedes> buscarHuespedes(){
		return this.huespedesController.listarHuespedes();
	}
	
	//Metodo - Buscar huesped por id
	private List<Huespedes> buscarHuespedesId(){
		return this.huespedesController.listarHuespedesId(txtBuscar.getText());
	}
	
	
	//Metodo para limpiar la tabla
	private void limpiarTabla() {
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}
	
	
	
	//Agrega la reserva con los datos de la reservas en la tabla
	private void LlenarTablaReservas() {
		
		//Llenar tabla
		List<Reserva> reserva = buscarReservas(); //Una lista de Tipo Reserva llamada reserva que es = al metodo buscarReservas que sera otra lista.
		try {
			for(Reserva reservas : reserva) {//El modelo Reserva llamado reservas es la lista a iterar : reserva es el elemento unico a obtener
					modelo.addRow(new Object[] {reservas.getId(), reservas.getFechaE(), //llamamos al modelo y se dice que adicione una Fila(horizontal), y que cree un objeto que contendra los datos de la reserva. 
					reservas.getFechaS(), reservas.getValor(), reservas.getFormaPago()	
				});
			}
		}catch(Exception e) {
			throw new RuntimeException(e); //throw e;
		}
	}
	
	//Muestra en la tabla la reserva buscada por id
	private void LlenarTablaReservasId() {
		//Llenar tabla
		List<Reserva> reserva = buscarReservasId();
		try {
			for(Reserva reservas : reserva) {
				modelo.addRow(new Object[] {reservas.getId(), reservas.getFechaE(), reservas.getFechaS(), reservas.getValor(), reservas.getFormaPago()});
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	//Agrega el huesped con sus datos en la tabla
	private void LlenarTablaHuespedes() {
		//Llenar tabla
		List<Huespedes> huesped = buscarHuespedes();
		try {
			for(Huespedes huespedes : huesped) {
				modeloHuesped.addRow(new Object[] {huespedes.getId(), huespedes.getNombre(), huespedes.getApellido(), huespedes.getFechaNacimiento(), huespedes.getNacionalidad(), huespedes.getTelefono(), huespedes.getIdReserva()});
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	//Busca el huesped por Id
	private void LlenarTablaHuespedId() {
		//Llenar tabla
		List<Huespedes> huesped = buscarHuespedesId();
		try {
			for(Huespedes huespedes : huesped) {
				modeloHuesped.addRow(new Object[] {huespedes.getId(), huespedes.getNombre(), huespedes.getApellido(), huespedes.getFechaNacimiento(), huespedes.getNacionalidad(), huespedes.getTelefono(), huespedes.getIdReserva()});
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	

	//Metodo Actualiza Reserva
	private void actualizarReserva() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn())) 
	.ifPresentOrElse(fila -> {
			
			Date fechaE = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString());
			Date fechaS = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString());
				String valor = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 3);
				String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
				Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
				
				this.reservaController.actualizar(fechaE, fechaS, valor, formaPago, id);
				JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
		}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));
	}
	
	

	//Metodo Actualiza Huesped
	private void actualizarHuesped() {
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
	.ifPresentOrElse(filaHuesped -> {
		
		String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
		String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
		Date fechaN = Date.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
			String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
			String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
			Integer idReserva = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
			Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
			this.huespedesController.actualizar(nombre, apellido, fechaN, nacionalidad, telefono, idReserva, id);
			JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
	}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));		
	}
	
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
	    }

}















