/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Empresa;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record17;
import org.jooq.Row17;
import org.jooq.impl.UpdatableRecordImpl;


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
public class EmpresaRecord extends UpdatableRecordImpl<EmpresaRecord> implements Record17<Integer, String, String, String, String, String, String, Integer, String, String, String, String, Date, String, String, String, Timestamp> {

	private static final long serialVersionUID = 394257989;

	/**
	 * Setter for <code>matera.empresa.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.empresa.id_empresa</code>.
	 */
	@Id
	@Column(name = "id_empresa", unique = true, nullable = false, precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.empresa.razonSocial</code>.
	 */
	public void setRazonsocial(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.empresa.razonSocial</code>.
	 */
	@Column(name = "razonSocial", nullable = false, length = 35)
	public String getRazonsocial() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.empresa.fantasia</code>.
	 */
	public void setFantasia(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.empresa.fantasia</code>.
	 */
	@Column(name = "fantasia", nullable = false, length = 35)
	public String getFantasia() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>matera.empresa.direccion</code>.
	 */
	public void setDireccion(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.empresa.direccion</code>.
	 */
	@Column(name = "direccion", nullable = false, length = 100)
	public String getDireccion() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>matera.empresa.departamento</code>.
	 */
	public void setDepartamento(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.empresa.departamento</code>.
	 */
	@Column(name = "departamento", nullable = false, length = 50)
	public String getDepartamento() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>matera.empresa.provincia</code>.
	 */
	public void setProvincia(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>matera.empresa.provincia</code>.
	 */
	@Column(name = "provincia", nullable = false, length = 50)
	public String getProvincia() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>matera.empresa.pais</code>.
	 */
	public void setPais(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>matera.empresa.pais</code>.
	 */
	@Column(name = "pais", nullable = false, length = 50)
	public String getPais() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>matera.empresa.posicionIVA</code>.
	 */
	public void setPosicioniva(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>matera.empresa.posicionIVA</code>.
	 */
	@Column(name = "posicionIVA", nullable = false, precision = 10)
	public Integer getPosicioniva() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>matera.empresa.cuit</code>.
	 */
	public void setCuit(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>matera.empresa.cuit</code>.
	 */
	@Column(name = "cuit", nullable = false, length = 11)
	public String getCuit() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>matera.empresa.ingresosBrutos</code>.
	 */
	public void setIngresosbrutos(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>matera.empresa.ingresosBrutos</code>.
	 */
	@Column(name = "ingresosBrutos", nullable = false, length = 7)
	public String getIngresosbrutos() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>matera.empresa.establecimiento</code>.
	 */
	public void setEstablecimiento(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>matera.empresa.establecimiento</code>.
	 */
	@Column(name = "establecimiento", nullable = false, length = 15)
	public String getEstablecimiento() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>matera.empresa.sedeTimbrado</code>.
	 */
	public void setSedetimbrado(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>matera.empresa.sedeTimbrado</code>.
	 */
	@Column(name = "sedeTimbrado", nullable = false, length = 25)
	public String getSedetimbrado() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>matera.empresa.inicioActividades</code>.
	 */
	public void setInicioactividades(Date value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>matera.empresa.inicioActividades</code>.
	 */
	@Column(name = "inicioActividades")
	public Date getInicioactividades() {
		return (Date) getValue(12);
	}

	/**
	 * Setter for <code>matera.empresa.claveAccesos</code>.
	 */
	public void setClaveaccesos(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>matera.empresa.claveAccesos</code>.
	 */
	@Column(name = "claveAccesos", nullable = false, length = 15)
	public String getClaveaccesos() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>matera.empresa.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>matera.empresa.habilita</code>.
	 */
	@Column(name = "habilita", nullable = false, length = 1)
	public String getHabilita() {
		return (String) getValue(14);
	}

	/**
	 * Setter for <code>matera.empresa.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>matera.empresa.usuario</code>.
	 */
	@Column(name = "usuario", nullable = false, length = 45)
	public String getUsuario() {
		return (String) getValue(15);
	}

	/**
	 * Setter for <code>matera.empresa.fechaReal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>matera.empresa.fechaReal</code>.
	 */
	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(16);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record17 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row17<Integer, String, String, String, String, String, String, Integer, String, String, String, String, Date, String, String, String, Timestamp> fieldsRow() {
		return (Row17) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row17<Integer, String, String, String, String, String, String, Integer, String, String, String, String, Date, String, String, String, Timestamp> valuesRow() {
		return (Row17) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Empresa.EMPRESA.ID_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Empresa.EMPRESA.RAZONSOCIAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Empresa.EMPRESA.FANTASIA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Empresa.EMPRESA.DIRECCION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Empresa.EMPRESA.DEPARTAMENTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return Empresa.EMPRESA.PROVINCIA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return Empresa.EMPRESA.PAIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return Empresa.EMPRESA.POSICIONIVA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return Empresa.EMPRESA.CUIT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return Empresa.EMPRESA.INGRESOSBRUTOS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return Empresa.EMPRESA.ESTABLECIMIENTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return Empresa.EMPRESA.SEDETIMBRADO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field13() {
		return Empresa.EMPRESA.INICIOACTIVIDADES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field14() {
		return Empresa.EMPRESA.CLAVEACCESOS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field15() {
		return Empresa.EMPRESA.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field16() {
		return Empresa.EMPRESA.USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field17() {
		return Empresa.EMPRESA.FECHAREAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdEmpresa();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getRazonsocial();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getFantasia();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getDireccion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getDepartamento();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getProvincia();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getPais();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getPosicioniva();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getCuit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getIngresosbrutos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getEstablecimiento();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getSedetimbrado();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value13() {
		return getInicioactividades();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value14() {
		return getClaveaccesos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value15() {
		return getHabilita();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value16() {
		return getUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value17() {
		return getFechareal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value1(Integer value) {
		setIdEmpresa(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value2(String value) {
		setRazonsocial(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value3(String value) {
		setFantasia(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value4(String value) {
		setDireccion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value5(String value) {
		setDepartamento(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value6(String value) {
		setProvincia(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value7(String value) {
		setPais(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value8(Integer value) {
		setPosicioniva(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value9(String value) {
		setCuit(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value10(String value) {
		setIngresosbrutos(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value11(String value) {
		setEstablecimiento(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value12(String value) {
		setSedetimbrado(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value13(Date value) {
		setInicioactividades(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value14(String value) {
		setClaveaccesos(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value15(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value16(String value) {
		setUsuario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord value17(Timestamp value) {
		setFechareal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpresaRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7, Integer value8, String value9, String value10, String value11, String value12, Date value13, String value14, String value15, String value16, Timestamp value17) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		value12(value12);
		value13(value13);
		value14(value14);
		value15(value15);
		value16(value16);
		value17(value17);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached EmpresaRecord
	 */
	public EmpresaRecord() {
		super(Empresa.EMPRESA);
	}

	/**
	 * Create a detached, initialised EmpresaRecord
	 */
	public EmpresaRecord(Integer idEmpresa, String razonsocial, String fantasia, String direccion, String departamento, String provincia, String pais, Integer posicioniva, String cuit, String ingresosbrutos, String establecimiento, String sedetimbrado, Date inicioactividades, String claveaccesos, String habilita, String usuario, Timestamp fechareal) {
		super(Empresa.EMPRESA);

		setValue(0, idEmpresa);
		setValue(1, razonsocial);
		setValue(2, fantasia);
		setValue(3, direccion);
		setValue(4, departamento);
		setValue(5, provincia);
		setValue(6, pais);
		setValue(7, posicioniva);
		setValue(8, cuit);
		setValue(9, ingresosbrutos);
		setValue(10, establecimiento);
		setValue(11, sedetimbrado);
		setValue(12, inicioactividades);
		setValue(13, claveaccesos);
		setValue(14, habilita);
		setValue(15, usuario);
		setValue(16, fechareal);
	}
}