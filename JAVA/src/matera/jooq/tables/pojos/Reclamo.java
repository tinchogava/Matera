/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.pojos;


import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "reclamo", schema = "matera")
public class Reclamo implements Serializable {

	private static final long serialVersionUID = -1522221173;

	private Integer idReclamo;
	private Date    fechareclamo;
	private Integer idPresupuesto;
	private String  descripcion;
	private String  procedimiento;
	private String  acciones;
	private Date    fechanotificacion;
	private Date    fechadevolucion;
	private Integer idReclamointerno;
	private Integer idReclamoexterno;
	private String  direccion;
	private Integer telefono;
	private String  recibe;
	private Integer idUsuario;
	private Integer idZona;
	private String  destinoreclamo;
	private byte[]  file1;
	private byte[]  file2;
	private byte[]  file3;
	private byte[]  file4;

	public Reclamo() {}

	public Reclamo(Reclamo value) {
		this.idReclamo = value.idReclamo;
		this.fechareclamo = value.fechareclamo;
		this.idPresupuesto = value.idPresupuesto;
		this.descripcion = value.descripcion;
		this.procedimiento = value.procedimiento;
		this.acciones = value.acciones;
		this.fechanotificacion = value.fechanotificacion;
		this.fechadevolucion = value.fechadevolucion;
		this.idReclamointerno = value.idReclamointerno;
		this.idReclamoexterno = value.idReclamoexterno;
		this.direccion = value.direccion;
		this.telefono = value.telefono;
		this.recibe = value.recibe;
		this.idUsuario = value.idUsuario;
		this.idZona = value.idZona;
		this.destinoreclamo = value.destinoreclamo;
		this.file1 = value.file1;
		this.file2 = value.file2;
		this.file3 = value.file3;
		this.file4 = value.file4;
	}

	public Reclamo(
		Integer idReclamo,
		Date    fechareclamo,
		Integer idPresupuesto,
		String  descripcion,
		String  procedimiento,
		String  acciones,
		Date    fechanotificacion,
		Date    fechadevolucion,
		Integer idReclamointerno,
		Integer idReclamoexterno,
		String  direccion,
		Integer telefono,
		String  recibe,
		Integer idUsuario,
		Integer idZona,
		String  destinoreclamo,
		byte[]  file1,
		byte[]  file2,
		byte[]  file3,
		byte[]  file4
	) {
		this.idReclamo = idReclamo;
		this.fechareclamo = fechareclamo;
		this.idPresupuesto = idPresupuesto;
		this.descripcion = descripcion;
		this.procedimiento = procedimiento;
		this.acciones = acciones;
		this.fechanotificacion = fechanotificacion;
		this.fechadevolucion = fechadevolucion;
		this.idReclamointerno = idReclamointerno;
		this.idReclamoexterno = idReclamoexterno;
		this.direccion = direccion;
		this.telefono = telefono;
		this.recibe = recibe;
		this.idUsuario = idUsuario;
		this.idZona = idZona;
		this.destinoreclamo = destinoreclamo;
		this.file1 = file1;
		this.file2 = file2;
		this.file3 = file3;
		this.file4 = file4;
	}

	@Id
	@Column(name = "id_reclamo", unique = true, nullable = false, precision = 10)
	public Integer getIdReclamo() {
		return this.idReclamo;
	}

	public void setIdReclamo(Integer idReclamo) {
		this.idReclamo = idReclamo;
	}

	@Column(name = "fechaReclamo", nullable = false)
	public Date getFechareclamo() {
		return this.fechareclamo;
	}

	public void setFechareclamo(Date fechareclamo) {
		this.fechareclamo = fechareclamo;
	}

	@Column(name = "id_presupuesto", precision = 10)
	public Integer getIdPresupuesto() {
		return this.idPresupuesto;
	}

	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	@Column(name = "descripcion", nullable = false, length = 65535)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "procedimiento", length = 65535)
	public String getProcedimiento() {
		return this.procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	@Column(name = "acciones", nullable = false, length = 65535)
	public String getAcciones() {
		return this.acciones;
	}

	public void setAcciones(String acciones) {
		this.acciones = acciones;
	}

	@Column(name = "fechaNotificacion")
	public Date getFechanotificacion() {
		return this.fechanotificacion;
	}

	public void setFechanotificacion(Date fechanotificacion) {
		this.fechanotificacion = fechanotificacion;
	}

	@Column(name = "fechaDevolucion")
	public Date getFechadevolucion() {
		return this.fechadevolucion;
	}

	public void setFechadevolucion(Date fechadevolucion) {
		this.fechadevolucion = fechadevolucion;
	}

	@Column(name = "id_reclamoInterno", precision = 10)
	public Integer getIdReclamointerno() {
		return this.idReclamointerno;
	}

	public void setIdReclamointerno(Integer idReclamointerno) {
		this.idReclamointerno = idReclamointerno;
	}

	@Column(name = "id_reclamoExterno", precision = 10)
	public Integer getIdReclamoexterno() {
		return this.idReclamoexterno;
	}

	public void setIdReclamoexterno(Integer idReclamoexterno) {
		this.idReclamoexterno = idReclamoexterno;
	}

	@Column(name = "direccion", length = 45)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "telefono", precision = 10)
	public Integer getTelefono() {
		return this.telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	@Column(name = "recibe", length = 45)
	public String getRecibe() {
		return this.recibe;
	}

	public void setRecibe(String recibe) {
		this.recibe = recibe;
	}

	@Column(name = "id_usuario", nullable = false, precision = 10)
	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "id_zona", nullable = false, precision = 10)
	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	@Column(name = "destinoReclamo", length = 45)
	public String getDestinoreclamo() {
		return this.destinoreclamo;
	}

	public void setDestinoreclamo(String destinoreclamo) {
		this.destinoreclamo = destinoreclamo;
	}

	@Column(name = "file1")
	public byte[] getFile1() {
		return this.file1;
	}

	public void setFile1(byte[] file1) {
		this.file1 = file1;
	}

	@Column(name = "file2")
	public byte[] getFile2() {
		return this.file2;
	}

	public void setFile2(byte[] file2) {
		this.file2 = file2;
	}

	@Column(name = "file3")
	public byte[] getFile3() {
		return this.file3;
	}

	public void setFile3(byte[] file3) {
		this.file3 = file3;
	}

	@Column(name = "file4")
	public byte[] getFile4() {
		return this.file4;
	}

	public void setFile4(byte[] file4) {
		this.file4 = file4;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Reclamo (");

		sb.append(idReclamo);
		sb.append(", ").append(fechareclamo);
		sb.append(", ").append(idPresupuesto);
		sb.append(", ").append(descripcion);
		sb.append(", ").append(procedimiento);
		sb.append(", ").append(acciones);
		sb.append(", ").append(fechanotificacion);
		sb.append(", ").append(fechadevolucion);
		sb.append(", ").append(idReclamointerno);
		sb.append(", ").append(idReclamoexterno);
		sb.append(", ").append(direccion);
		sb.append(", ").append(telefono);
		sb.append(", ").append(recibe);
		sb.append(", ").append(idUsuario);
		sb.append(", ").append(idZona);
		sb.append(", ").append(destinoreclamo);
		sb.append(", ").append("[binary...]");
		sb.append(", ").append("[binary...]");
		sb.append(", ").append("[binary...]");
		sb.append(", ").append("[binary...]");

		sb.append(")");
		return sb.toString();
	}
}