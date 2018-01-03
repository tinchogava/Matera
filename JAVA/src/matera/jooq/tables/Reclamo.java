/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.ReclamoRecord;

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
public class Reclamo extends TableImpl<ReclamoRecord> {

	private static final long serialVersionUID = 1914016928;

	/**
	 * The reference instance of <code>matera.reclamo</code>
	 */
	public static final Reclamo RECLAMO = new Reclamo();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ReclamoRecord> getRecordType() {
		return ReclamoRecord.class;
	}

	/**
	 * The column <code>matera.reclamo.id_reclamo</code>.
	 */
	public final TableField<ReclamoRecord, Integer> ID_RECLAMO = createField("id_reclamo", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.reclamo.fechaReclamo</code>.
	 */
	public final TableField<ReclamoRecord, Date> FECHARECLAMO = createField("fechaReclamo", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

	/**
	 * The column <code>matera.reclamo.id_presupuesto</code>.
	 */
	public final TableField<ReclamoRecord, Integer> ID_PRESUPUESTO = createField("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>matera.reclamo.descripcion</code>.
	 */
	public final TableField<ReclamoRecord, String> DESCRIPCION = createField("descripcion", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>matera.reclamo.procedimiento</code>.
	 */
	public final TableField<ReclamoRecord, String> PROCEDIMIENTO = createField("procedimiento", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>matera.reclamo.acciones</code>.
	 */
	public final TableField<ReclamoRecord, String> ACCIONES = createField("acciones", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>matera.reclamo.fechaNotificacion</code>.
	 */
	public final TableField<ReclamoRecord, Date> FECHANOTIFICACION = createField("fechaNotificacion", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>matera.reclamo.fechaDevolucion</code>.
	 */
	public final TableField<ReclamoRecord, Date> FECHADEVOLUCION = createField("fechaDevolucion", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>matera.reclamo.id_reclamoInterno</code>.
	 */
	public final TableField<ReclamoRecord, Integer> ID_RECLAMOINTERNO = createField("id_reclamoInterno", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>matera.reclamo.id_reclamoExterno</code>.
	 */
	public final TableField<ReclamoRecord, Integer> ID_RECLAMOEXTERNO = createField("id_reclamoExterno", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>matera.reclamo.direccion</code>.
	 */
	public final TableField<ReclamoRecord, String> DIRECCION = createField("direccion", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

	/**
	 * The column <code>matera.reclamo.telefono</code>.
	 */
	public final TableField<ReclamoRecord, Integer> TELEFONO = createField("telefono", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>matera.reclamo.recibe</code>.
	 */
	public final TableField<ReclamoRecord, String> RECIBE = createField("recibe", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

	/**
	 * The column <code>matera.reclamo.id_usuario</code>.
	 */
	public final TableField<ReclamoRecord, Integer> ID_USUARIO = createField("id_usuario", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.reclamo.id_zona</code>.
	 */
	public final TableField<ReclamoRecord, Integer> ID_ZONA = createField("id_zona", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.reclamo.destinoReclamo</code>.
	 */
	public final TableField<ReclamoRecord, String> DESTINORECLAMO = createField("destinoReclamo", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

	/**
	 * The column <code>matera.reclamo.file1</code>.
	 */
	public final TableField<ReclamoRecord, byte[]> FILE1 = createField("file1", org.jooq.impl.SQLDataType.BLOB, this, "");

	/**
	 * The column <code>matera.reclamo.file2</code>.
	 */
	public final TableField<ReclamoRecord, byte[]> FILE2 = createField("file2", org.jooq.impl.SQLDataType.BLOB, this, "");

	/**
	 * The column <code>matera.reclamo.file3</code>.
	 */
	public final TableField<ReclamoRecord, byte[]> FILE3 = createField("file3", org.jooq.impl.SQLDataType.BLOB, this, "");

	/**
	 * The column <code>matera.reclamo.file4</code>.
	 */
	public final TableField<ReclamoRecord, byte[]> FILE4 = createField("file4", org.jooq.impl.SQLDataType.BLOB, this, "");

	/**
	 * Create a <code>matera.reclamo</code> table reference
	 */
	public Reclamo() {
		this("reclamo", null);
	}

	/**
	 * Create an aliased <code>matera.reclamo</code> table reference
	 */
	public Reclamo(String alias) {
		this(alias, RECLAMO);
	}

	private Reclamo(String alias, Table<ReclamoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Reclamo(String alias, Table<ReclamoRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ReclamoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_RECLAMO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ReclamoRecord> getPrimaryKey() {
		return Keys.KEY_RECLAMO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ReclamoRecord>> getKeys() {
		return Arrays.<UniqueKey<ReclamoRecord>>asList(Keys.KEY_RECLAMO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reclamo as(String alias) {
		return new Reclamo(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Reclamo rename(String name) {
		return new Reclamo(name, null);
	}
}