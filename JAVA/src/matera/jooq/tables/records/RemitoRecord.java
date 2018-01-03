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

import matera.jooq.tables.Remito;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record18;
import org.jooq.Row18;
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
@Table(name = "remito", schema = "matera")
public class RemitoRecord extends UpdatableRecordImpl<RemitoRecord> implements Record18<Integer, Integer, Date, Integer, String, Integer, Integer, Integer, Integer, Integer, String, String, String, String, Date, Integer, String, Timestamp> {

	private static final long serialVersionUID = 259741841;

	/**
	 * Setter for <code>matera.remito.id_remito</code>.
	 */
	public void setIdRemito(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.remito.id_remito</code>.
	 */
	@Id
	@Column(name = "id_remito", unique = true, nullable = false, precision = 10)
	public Integer getIdRemito() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.remito.id_presupuesto</code>.
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.remito.id_presupuesto</code>.
	 */
	@Column(name = "id_presupuesto", precision = 10)
	public Integer getIdPresupuesto() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>matera.remito.fecha</code>.
	 */
	public void setFecha(Date value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.remito.fecha</code>.
	 */
	@Column(name = "fecha")
	public Date getFecha() {
		return (Date) getValue(2);
	}

	/**
	 * Setter for <code>matera.remito.id_tipoComp</code>.
	 */
	public void setIdTipocomp(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.remito.id_tipoComp</code>.
	 */
	@Column(name = "id_tipoComp", precision = 10)
	public Integer getIdTipocomp() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>matera.remito.numComp</code>.
	 */
	public void setNumcomp(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.remito.numComp</code>.
	 */
	@Column(name = "numComp", length = 12)
	public String getNumcomp() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>matera.remito.id_entidad</code>.
	 */
	public void setIdEntidad(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>matera.remito.id_entidad</code>.
	 */
	@Column(name = "id_entidad", precision = 10)
	public Integer getIdEntidad() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>matera.remito.id_proveedor</code>.
	 */
	public void setIdProveedor(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>matera.remito.id_proveedor</code>.
	 */
	@Column(name = "id_proveedor", precision = 10)
	public Integer getIdProveedor() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>matera.remito.id_destino</code>. Refleja el id_profesional. No se cambia el nombre para no afectar el funcionamiento de otros procedimientos ni funciones.
	 */
	public void setIdDestino(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>matera.remito.id_destino</code>. Refleja el id_profesional. No se cambia el nombre para no afectar el funcionamiento de otros procedimientos ni funciones.
	 */
	@Column(name = "id_destino", precision = 10)
	public Integer getIdDestino() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>matera.remito.id_zonaDestino</code>.
	 */
	public void setIdZonadestino(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>matera.remito.id_zonaDestino</code>.
	 */
	@Column(name = "id_zonaDestino", precision = 10)
	public Integer getIdZonadestino() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>matera.remito.id_zona</code>.
	 */
	public void setIdZona(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>matera.remito.id_zona</code>.
	 */
	@Column(name = "id_zona", precision = 10)
	public Integer getIdZona() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>matera.remito.observaciones</code>.
	 */
	public void setObservaciones(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>matera.remito.observaciones</code>.
	 */
	@Column(name = "observaciones", length = 65535)
	public String getObservaciones() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>matera.remito.cajas</code>.
	 */
	public void setCajas(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>matera.remito.cajas</code>.
	 */
	@Column(name = "cajas", length = 65535)
	public String getCajas() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>matera.remito.sets</code>.
	 */
	public void setSets(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>matera.remito.sets</code>.
	 */
	@Column(name = "sets", length = 65535)
	public String getSets() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>matera.remito.recibido</code>.
	 */
	public void setRecibido(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>matera.remito.recibido</code>.
	 */
	@Column(name = "recibido", length = 1)
	public String getRecibido() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>matera.remito.fechaConsumido</code>.
	 */
	public void setFechaconsumido(Date value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>matera.remito.fechaConsumido</code>.
	 */
	@Column(name = "fechaConsumido")
	public Date getFechaconsumido() {
		return (Date) getValue(14);
	}

	/**
	 * Setter for <code>matera.remito.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>matera.remito.id_empresa</code>.
	 */
	@Column(name = "id_empresa", precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(15);
	}

	/**
	 * Setter for <code>matera.remito.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>matera.remito.usuario</code>.
	 */
	@Column(name = "usuario", length = 45)
	public String getUsuario() {
		return (String) getValue(16);
	}

	/**
	 * Setter for <code>matera.remito.fechaReal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>matera.remito.fechaReal</code>.
	 */
	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(17);
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
	// Record18 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row18<Integer, Integer, Date, Integer, String, Integer, Integer, Integer, Integer, Integer, String, String, String, String, Date, Integer, String, Timestamp> fieldsRow() {
		return (Row18) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row18<Integer, Integer, Date, Integer, String, Integer, Integer, Integer, Integer, Integer, String, String, String, String, Date, Integer, String, Timestamp> valuesRow() {
		return (Row18) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Remito.REMITO.ID_REMITO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return Remito.REMITO.ID_PRESUPUESTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field3() {
		return Remito.REMITO.FECHA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Remito.REMITO.ID_TIPOCOMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Remito.REMITO.NUMCOMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return Remito.REMITO.ID_ENTIDAD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return Remito.REMITO.ID_PROVEEDOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return Remito.REMITO.ID_DESTINO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return Remito.REMITO.ID_ZONADESTINO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return Remito.REMITO.ID_ZONA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return Remito.REMITO.OBSERVACIONES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return Remito.REMITO.CAJAS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field13() {
		return Remito.REMITO.SETS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field14() {
		return Remito.REMITO.RECIBIDO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field15() {
		return Remito.REMITO.FECHACONSUMIDO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field16() {
		return Remito.REMITO.ID_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field17() {
		return Remito.REMITO.USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field18() {
		return Remito.REMITO.FECHAREAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdRemito();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getIdPresupuesto();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value3() {
		return getFecha();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getIdTipocomp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getNumcomp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getIdEntidad();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getIdProveedor();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getIdDestino();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getIdZonadestino();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getIdZona();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getObservaciones();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getCajas();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value13() {
		return getSets();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value14() {
		return getRecibido();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value15() {
		return getFechaconsumido();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value16() {
		return getIdEmpresa();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value17() {
		return getUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value18() {
		return getFechareal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value1(Integer value) {
		setIdRemito(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value2(Integer value) {
		setIdPresupuesto(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value3(Date value) {
		setFecha(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value4(Integer value) {
		setIdTipocomp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value5(String value) {
		setNumcomp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value6(Integer value) {
		setIdEntidad(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value7(Integer value) {
		setIdProveedor(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value8(Integer value) {
		setIdDestino(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value9(Integer value) {
		setIdZonadestino(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value10(Integer value) {
		setIdZona(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value11(String value) {
		setObservaciones(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value12(String value) {
		setCajas(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value13(String value) {
		setSets(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value14(String value) {
		setRecibido(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value15(Date value) {
		setFechaconsumido(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value16(Integer value) {
		setIdEmpresa(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value17(String value) {
		setUsuario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord value18(Timestamp value) {
		setFechareal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RemitoRecord values(Integer value1, Integer value2, Date value3, Integer value4, String value5, Integer value6, Integer value7, Integer value8, Integer value9, Integer value10, String value11, String value12, String value13, String value14, Date value15, Integer value16, String value17, Timestamp value18) {
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
		value18(value18);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached RemitoRecord
	 */
	public RemitoRecord() {
		super(Remito.REMITO);
	}

	/**
	 * Create a detached, initialised RemitoRecord
	 */
	public RemitoRecord(Integer idRemito, Integer idPresupuesto, Date fecha, Integer idTipocomp, String numcomp, Integer idEntidad, Integer idProveedor, Integer idDestino, Integer idZonadestino, Integer idZona, String observaciones, String cajas, String sets, String recibido, Date fechaconsumido, Integer idEmpresa, String usuario, Timestamp fechareal) {
		super(Remito.REMITO);

		setValue(0, idRemito);
		setValue(1, idPresupuesto);
		setValue(2, fecha);
		setValue(3, idTipocomp);
		setValue(4, numcomp);
		setValue(5, idEntidad);
		setValue(6, idProveedor);
		setValue(7, idDestino);
		setValue(8, idZonadestino);
		setValue(9, idZona);
		setValue(10, observaciones);
		setValue(11, cajas);
		setValue(12, sets);
		setValue(13, recibido);
		setValue(14, fechaconsumido);
		setValue(15, idEmpresa);
		setValue(16, usuario);
		setValue(17, fechareal);
	}
        
        @Override
        public String toString() {
            return getNumcomp();
        }
}