/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

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
@Table(name = "empresa", schema = "matera")
public class Empresa implements Serializable {

	private static final long serialVersionUID = -893129210;

	private Integer   idEmpresa;
	private String    razonsocial;
	private String    fantasia;
	private String    direccion;
	private String    departamento;
	private String    provincia;
	private String    pais;
	private Integer   posicioniva;
	private String    cuit;
	private String    ingresosbrutos;
	private String    establecimiento;
	private String    sedetimbrado;
	private Date      inicioactividades;
	private String    claveaccesos;
	private String    habilita;
	private String    usuario;
	private Timestamp fechareal;

	public Empresa() {}

	public Empresa(Empresa value) {
		this.idEmpresa = value.idEmpresa;
		this.razonsocial = value.razonsocial;
		this.fantasia = value.fantasia;
		this.direccion = value.direccion;
		this.departamento = value.departamento;
		this.provincia = value.provincia;
		this.pais = value.pais;
		this.posicioniva = value.posicioniva;
		this.cuit = value.cuit;
		this.ingresosbrutos = value.ingresosbrutos;
		this.establecimiento = value.establecimiento;
		this.sedetimbrado = value.sedetimbrado;
		this.inicioactividades = value.inicioactividades;
		this.claveaccesos = value.claveaccesos;
		this.habilita = value.habilita;
		this.usuario = value.usuario;
		this.fechareal = value.fechareal;
	}

	public Empresa(
		Integer   idEmpresa,
		String    razonsocial,
		String    fantasia,
		String    direccion,
		String    departamento,
		String    provincia,
		String    pais,
		Integer   posicioniva,
		String    cuit,
		String    ingresosbrutos,
		String    establecimiento,
		String    sedetimbrado,
		Date      inicioactividades,
		String    claveaccesos,
		String    habilita,
		String    usuario,
		Timestamp fechareal
	) {
		this.idEmpresa = idEmpresa;
		this.razonsocial = razonsocial;
		this.fantasia = fantasia;
		this.direccion = direccion;
		this.departamento = departamento;
		this.provincia = provincia;
		this.pais = pais;
		this.posicioniva = posicioniva;
		this.cuit = cuit;
		this.ingresosbrutos = ingresosbrutos;
		this.establecimiento = establecimiento;
		this.sedetimbrado = sedetimbrado;
		this.inicioactividades = inicioactividades;
		this.claveaccesos = claveaccesos;
		this.habilita = habilita;
		this.usuario = usuario;
		this.fechareal = fechareal;
	}

	@Id
	@Column(name = "id_empresa", unique = true, nullable = false, precision = 10)
	public Integer getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@Column(name = "razonSocial", nullable = false, length = 35)
	public String getRazonsocial() {
		return this.razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}

	@Column(name = "fantasia", nullable = false, length = 35)
	public String getFantasia() {
		return this.fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	@Column(name = "direccion", nullable = false, length = 100)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "departamento", nullable = false, length = 50)
	public String getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Column(name = "provincia", nullable = false, length = 50)
	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Column(name = "pais", nullable = false, length = 50)
	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(name = "posicionIVA", nullable = false, precision = 10)
	public Integer getPosicioniva() {
		return this.posicioniva;
	}

	public void setPosicioniva(Integer posicioniva) {
		this.posicioniva = posicioniva;
	}

	@Column(name = "cuit", nullable = false, length = 11)
	public String getCuit() {
		return this.cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	@Column(name = "ingresosBrutos", nullable = false, length = 7)
	public String getIngresosbrutos() {
		return this.ingresosbrutos;
	}

	public void setIngresosbrutos(String ingresosbrutos) {
		this.ingresosbrutos = ingresosbrutos;
	}

	@Column(name = "establecimiento", nullable = false, length = 15)
	public String getEstablecimiento() {
		return this.establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	@Column(name = "sedeTimbrado", nullable = false, length = 25)
	public String getSedetimbrado() {
		return this.sedetimbrado;
	}

	public void setSedetimbrado(String sedetimbrado) {
		this.sedetimbrado = sedetimbrado;
	}

	@Column(name = "inicioActividades")
	public Date getInicioactividades() {
		return this.inicioactividades;
	}

	public void setInicioactividades(Date inicioactividades) {
		this.inicioactividades = inicioactividades;
	}

	@Column(name = "claveAccesos", nullable = false, length = 15)
	public String getClaveaccesos() {
		return this.claveaccesos;
	}

	public void setClaveaccesos(String claveaccesos) {
		this.claveaccesos = claveaccesos;
	}

	@Column(name = "habilita", nullable = false, length = 1)
	public String getHabilita() {
		return this.habilita;
	}

	public void setHabilita(String habilita) {
		this.habilita = habilita;
	}

	@Column(name = "usuario", nullable = false, length = 45)
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return this.fechareal;
	}

	public void setFechareal(Timestamp fechareal) {
		this.fechareal = fechareal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Empresa (");

		sb.append(idEmpresa);
		sb.append(", ").append(razonsocial);
		sb.append(", ").append(fantasia);
		sb.append(", ").append(direccion);
		sb.append(", ").append(departamento);
		sb.append(", ").append(provincia);
		sb.append(", ").append(pais);
		sb.append(", ").append(posicioniva);
		sb.append(", ").append(cuit);
		sb.append(", ").append(ingresosbrutos);
		sb.append(", ").append(establecimiento);
		sb.append(", ").append(sedetimbrado);
		sb.append(", ").append(inicioactividades);
		sb.append(", ").append(claveaccesos);
		sb.append(", ").append(habilita);
		sb.append(", ").append(usuario);
		sb.append(", ").append(fechareal);

		sb.append(")");
		return sb.toString();
	}
}