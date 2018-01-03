/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.TipooperacionRecord;

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
public class Tipooperacion extends TableImpl<TipooperacionRecord> {

	private static final long serialVersionUID = 1083989362;

	/**
	 * The reference instance of <code>matera.tipooperacion</code>
	 */
	public static final Tipooperacion TIPOOPERACION = new Tipooperacion();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TipooperacionRecord> getRecordType() {
		return TipooperacionRecord.class;
	}

	/**
	 * The column <code>matera.tipooperacion.id_tipoOperacion</code>.
	 */
	public final TableField<TipooperacionRecord, Integer> ID_TIPOOPERACION = createField("id_tipoOperacion", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.tipooperacion.nombre</code>.
	 */
	public final TableField<TipooperacionRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

	/**
	 * The column <code>matera.tipooperacion.habilita</code>.
	 */
	public final TableField<TipooperacionRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1), this, "");

	/**
	 * Create a <code>matera.tipooperacion</code> table reference
	 */
	public Tipooperacion() {
		this("tipooperacion", null);
	}

	/**
	 * Create an aliased <code>matera.tipooperacion</code> table reference
	 */
	public Tipooperacion(String alias) {
		this(alias, TIPOOPERACION);
	}

	private Tipooperacion(String alias, Table<TipooperacionRecord> aliased) {
		this(alias, aliased, null);
	}

	private Tipooperacion(String alias, Table<TipooperacionRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TipooperacionRecord, Integer> getIdentity() {
		return Keys.IDENTITY_TIPOOPERACION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TipooperacionRecord> getPrimaryKey() {
		return Keys.KEY_TIPOOPERACION_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TipooperacionRecord>> getKeys() {
		return Arrays.<UniqueKey<TipooperacionRecord>>asList(Keys.KEY_TIPOOPERACION_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tipooperacion as(String alias) {
		return new Tipooperacion(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Tipooperacion rename(String name) {
		return new Tipooperacion(name, null);
	}
}
