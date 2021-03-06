/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.EmpresaRecord;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class Empresa extends TableImpl<EmpresaRecord> {

	private static final long serialVersionUID = 2129099202;

	/**
	 * The reference instance of <code>matera.empresa</code>
	 */
	public static final Empresa EMPRESA = new Empresa();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<EmpresaRecord> getRecordType() {
		return EmpresaRecord.class;
	}

	/**
	 * The column <code>matera.empresa.id_empresa</code>.
	 */
	public final TableField<EmpresaRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.empresa.razonSocial</code>.
	 */
	public final TableField<EmpresaRecord, String> RAZONSOCIAL = createField("razonSocial", org.jooq.impl.SQLDataType.VARCHAR.length(35).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.fantasia</code>.
	 */
	public final TableField<EmpresaRecord, String> FANTASIA = createField("fantasia", org.jooq.impl.SQLDataType.VARCHAR.length(35).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.direccion</code>.
	 */
	public final TableField<EmpresaRecord, String> DIRECCION = createField("direccion", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.departamento</code>.
	 */
	public final TableField<EmpresaRecord, String> DEPARTAMENTO = createField("departamento", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.provincia</code>.
	 */
	public final TableField<EmpresaRecord, String> PROVINCIA = createField("provincia", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.pais</code>.
	 */
	public final TableField<EmpresaRecord, String> PAIS = createField("pais", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.posicionIVA</code>.
	 */
	public final TableField<EmpresaRecord, Integer> POSICIONIVA = createField("posicionIVA", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.cuit</code>.
	 */
	public final TableField<EmpresaRecord, String> CUIT = createField("cuit", org.jooq.impl.SQLDataType.VARCHAR.length(11).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.ingresosBrutos</code>.
	 */
	public final TableField<EmpresaRecord, String> INGRESOSBRUTOS = createField("ingresosBrutos", org.jooq.impl.SQLDataType.VARCHAR.length(7).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.establecimiento</code>.
	 */
	public final TableField<EmpresaRecord, String> ESTABLECIMIENTO = createField("establecimiento", org.jooq.impl.SQLDataType.VARCHAR.length(15).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.sedeTimbrado</code>.
	 */
	public final TableField<EmpresaRecord, String> SEDETIMBRADO = createField("sedeTimbrado", org.jooq.impl.SQLDataType.VARCHAR.length(25).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.inicioActividades</code>.
	 */
	public final TableField<EmpresaRecord, Date> INICIOACTIVIDADES = createField("inicioActividades", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>matera.empresa.claveAccesos</code>.
	 */
	public final TableField<EmpresaRecord, String> CLAVEACCESOS = createField("claveAccesos", org.jooq.impl.SQLDataType.VARCHAR.length(15).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.habilita</code>.
	 */
	public final TableField<EmpresaRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.usuario</code>.
	 */
	public final TableField<EmpresaRecord, String> USUARIO = createField("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.empresa.fechaReal</code>.
	 */
	public final TableField<EmpresaRecord, Timestamp> FECHAREAL = createField("fechaReal", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * Create a <code>matera.empresa</code> table reference
	 */
	public Empresa() {
		this("empresa", null);
	}

	/**
	 * Create an aliased <code>matera.empresa</code> table reference
	 */
	public Empresa(String alias) {
		this(alias, EMPRESA);
	}

	private Empresa(String alias, Table<EmpresaRecord> aliased) {
		this(alias, aliased, null);
	}

	private Empresa(String alias, Table<EmpresaRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<EmpresaRecord, Integer> getIdentity() {
		return Keys.IDENTITY_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<EmpresaRecord> getPrimaryKey() {
		return Keys.KEY_EMPRESA_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<EmpresaRecord>> getKeys() {
		return Arrays.<UniqueKey<EmpresaRecord>>asList(Keys.KEY_EMPRESA_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Empresa as(String alias) {
		return new Empresa(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Empresa rename(String name) {
		return new Empresa(name, null);
	}
}
