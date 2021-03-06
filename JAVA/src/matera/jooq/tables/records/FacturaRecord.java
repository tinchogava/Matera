/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Factura;

import org.jooq.Record1;
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
@Table(name = "factura", schema = "matera")
public class FacturaRecord extends UpdatableRecordImpl<FacturaRecord> {

	private static final long serialVersionUID = -524348241;

	/**
	 * Setter for <code>matera.factura.id_factura</code>.
	 */
	public void setIdFactura(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.factura.id_factura</code>.
	 */
	@Id
	@Column(name = "id_factura", unique = true, nullable = false, precision = 10)
	public Integer getIdFactura() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.factura.fecha</code>.
	 */
	public void setFecha(Date value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.factura.fecha</code>.
	 */
	@Column(name = "fecha")
	public Date getFecha() {
		return (Date) getValue(1);
	}

	/**
	 * Setter for <code>matera.factura.id_presupuesto</code>.
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.factura.id_presupuesto</code>.
	 */
	@Column(name = "id_presupuesto", precision = 10)
	public Integer getIdPresupuesto() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>matera.factura.id_tipoComp</code>.
	 */
	public void setIdTipocomp(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.factura.id_tipoComp</code>.
	 */
	@Column(name = "id_tipoComp", precision = 10)
	public Integer getIdTipocomp() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>matera.factura.numComp</code>.
	 */
	public void setNumcomp(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.factura.numComp</code>.
	 */
	@Column(name = "numComp", length = 12)
	public String getNumcomp() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>matera.factura.id_tipoCompRel</code>.
	 */
	public void setIdTipocomprel(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>matera.factura.id_tipoCompRel</code>.
	 */
	@Column(name = "id_tipoCompRel", precision = 10)
	public Integer getIdTipocomprel() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>matera.factura.numCompRel</code>.
	 */
	public void setNumcomprel(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>matera.factura.numCompRel</code>.
	 */
	@Column(name = "numCompRel", length = 12)
	public String getNumcomprel() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>matera.factura.vencimiento</code>.
	 */
	public void setVencimiento(Date value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>matera.factura.vencimiento</code>.
	 */
	@Column(name = "vencimiento")
	public Date getVencimiento() {
		return (Date) getValue(7);
	}

	/**
	 * Setter for <code>matera.factura.id_formaPago</code>.
	 */
	public void setIdFormapago(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>matera.factura.id_formaPago</code>.
	 */
	@Column(name = "id_formaPago", precision = 10)
	public Integer getIdFormapago() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>matera.factura.dc</code>.
	 */
	public void setDc(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>matera.factura.dc</code>.
	 */
	@Column(name = "dc", length = 1)
	public String getDc() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>matera.factura.bonificacion</code>.
	 */
	public void setBonificacion(BigDecimal value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>matera.factura.bonificacion</code>.
	 */
	@Column(name = "bonificacion", precision = 9, scale = 2)
	public BigDecimal getBonificacion() {
		return (BigDecimal) getValue(10);
	}

	/**
	 * Setter for <code>matera.factura.neto</code>.
	 */
	public void setNeto(BigDecimal value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>matera.factura.neto</code>.
	 */
	@Column(name = "neto", precision = 9, scale = 2)
	public BigDecimal getNeto() {
		return (BigDecimal) getValue(11);
	}

	/**
	 * Setter for <code>matera.factura.aliPercIIBB</code>.
	 */
	public void setAliperciibb(BigDecimal value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>matera.factura.aliPercIIBB</code>.
	 */
	@Column(name = "aliPercIIBB", precision = 5, scale = 2)
	public BigDecimal getAliperciibb() {
		return (BigDecimal) getValue(12);
	}

	/**
	 * Setter for <code>matera.factura.percIIBB</code>.
	 */
	public void setPerciibb(BigDecimal value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>matera.factura.percIIBB</code>.
	 */
	@Column(name = "percIIBB", precision = 9, scale = 2)
	public BigDecimal getPerciibb() {
		return (BigDecimal) getValue(13);
	}

	/**
	 * Setter for <code>matera.factura.aliIva</code>.
	 */
	public void setAliiva(BigDecimal value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>matera.factura.aliIva</code>.
	 */
	@Column(name = "aliIva", precision = 5, scale = 2)
	public BigDecimal getAliiva() {
		return (BigDecimal) getValue(14);
	}

	/**
	 * Setter for <code>matera.factura.iva</code>.
	 */
	public void setIva(BigDecimal value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>matera.factura.iva</code>.
	 */
	@Column(name = "iva", precision = 9, scale = 2)
	public BigDecimal getIva() {
		return (BigDecimal) getValue(15);
	}

	/**
	 * Setter for <code>matera.factura.observaciones</code>.
	 */
	public void setObservaciones(String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>matera.factura.observaciones</code>.
	 */
	@Column(name = "observaciones", length = 65535)
	public String getObservaciones() {
		return (String) getValue(16);
	}

	/**
	 * Setter for <code>matera.factura.id_vendedor</code>.
	 */
	public void setIdVendedor(Integer value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>matera.factura.id_vendedor</code>.
	 */
	@Column(name = "id_vendedor", precision = 10)
	public Integer getIdVendedor() {
		return (Integer) getValue(17);
	}

	/**
	 * Setter for <code>matera.factura.comparte</code>.
	 */
	public void setComparte(String value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>matera.factura.comparte</code>.
	 */
	@Column(name = "comparte", length = 1)
	public String getComparte() {
		return (String) getValue(18);
	}

	/**
	 * Setter for <code>matera.factura.id_vendedorComparte</code>.
	 */
	public void setIdVendedorcomparte(Integer value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>matera.factura.id_vendedorComparte</code>.
	 */
	@Column(name = "id_vendedorComparte", precision = 10)
	public Integer getIdVendedorcomparte() {
		return (Integer) getValue(19);
	}

	/**
	 * Setter for <code>matera.factura.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>matera.factura.id_empresa</code>.
	 */
	@Column(name = "id_empresa", precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(20);
	}

	/**
	 * Setter for <code>matera.factura.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>matera.factura.usuario</code>.
	 */
	@Column(name = "usuario", length = 45)
	public String getUsuario() {
		return (String) getValue(21);
	}

	/**
	 * Setter for <code>matera.factura.fechaReal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>matera.factura.fechaReal</code>.
	 */
	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(22);
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
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached FacturaRecord
	 */
	public FacturaRecord() {
		super(Factura.FACTURA);
	}

	/**
	 * Create a detached, initialised FacturaRecord
	 */
	public FacturaRecord(Integer idFactura, Date fecha, Integer idPresupuesto, Integer idTipocomp, String numcomp, Integer idTipocomprel, String numcomprel, Date vencimiento, Integer idFormapago, String dc, BigDecimal bonificacion, BigDecimal neto, BigDecimal aliperciibb, BigDecimal perciibb, BigDecimal aliiva, BigDecimal iva, String observaciones, Integer idVendedor, String comparte, Integer idVendedorcomparte, Integer idEmpresa, String usuario, Timestamp fechareal) {
		super(Factura.FACTURA);

		setValue(0, idFactura);
		setValue(1, fecha);
		setValue(2, idPresupuesto);
		setValue(3, idTipocomp);
		setValue(4, numcomp);
		setValue(5, idTipocomprel);
		setValue(6, numcomprel);
		setValue(7, vencimiento);
		setValue(8, idFormapago);
		setValue(9, dc);
		setValue(10, bonificacion);
		setValue(11, neto);
		setValue(12, aliperciibb);
		setValue(13, perciibb);
		setValue(14, aliiva);
		setValue(15, iva);
		setValue(16, observaciones);
		setValue(17, idVendedor);
		setValue(18, comparte);
		setValue(19, idVendedorcomparte);
		setValue(20, idEmpresa);
		setValue(21, usuario);
		setValue(22, fechareal);
	}
        
        @Override
        public String toString() {
            return getNumcomp();
        }
}
