/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.EstadosistemaRecord;

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
public class Estadosistema extends TableImpl<EstadosistemaRecord> {

	private static final long serialVersionUID = 1096748273;

	/**
	 * The reference instance of <code>matera.estadosistema</code>
	 */
	public static final Estadosistema ESTADOSISTEMA = new Estadosistema();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<EstadosistemaRecord> getRecordType() {
		return EstadosistemaRecord.class;
	}

	/**
	 * The column <code>matera.estadosistema.id_estadoSistema</code>.
	 */
	public final TableField<EstadosistemaRecord, Integer> ID_ESTADOSISTEMA = createField("id_estadoSistema", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.estadosistema.estado</code>.
	 */
	public final TableField<EstadosistemaRecord, String> ESTADO = createField("estado", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

	/**
	 * The column <code>matera.estadosistema.enProceso</code>.
	 */
	public final TableField<EstadosistemaRecord, String> ENPROCESO = createField("enProceso", org.jooq.impl.SQLDataType.VARCHAR.length(1), this, "");

	/**
	 * Create a <code>matera.estadosistema</code> table reference
	 */
	public Estadosistema() {
		this("estadosistema", null);
	}

	/**
	 * Create an aliased <code>matera.estadosistema</code> table reference
	 */
	public Estadosistema(String alias) {
		this(alias, ESTADOSISTEMA);
	}

	private Estadosistema(String alias, Table<EstadosistemaRecord> aliased) {
		this(alias, aliased, null);
	}

	private Estadosistema(String alias, Table<EstadosistemaRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<EstadosistemaRecord, Integer> getIdentity() {
		return Keys.IDENTITY_ESTADOSISTEMA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<EstadosistemaRecord> getPrimaryKey() {
		return Keys.KEY_ESTADOSISTEMA_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<EstadosistemaRecord>> getKeys() {
		return Arrays.<UniqueKey<EstadosistemaRecord>>asList(Keys.KEY_ESTADOSISTEMA_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Estadosistema as(String alias) {
		return new Estadosistema(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Estadosistema rename(String name) {
		return new Estadosistema(name, null);
	}
}
