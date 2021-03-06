/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.MantenimientoRecord;

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
public class Mantenimiento extends TableImpl<MantenimientoRecord> {

	private static final long serialVersionUID = -1876192364;

	/**
	 * The reference instance of <code>matera.mantenimiento</code>
	 */
	public static final Mantenimiento MANTENIMIENTO = new Mantenimiento();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<MantenimientoRecord> getRecordType() {
		return MantenimientoRecord.class;
	}

	/**
	 * The column <code>matera.mantenimiento.id_mantenimiento</code>.
	 */
	public final TableField<MantenimientoRecord, Integer> ID_MANTENIMIENTO = createField("id_mantenimiento", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.mantenimiento.nombre</code>.
	 */
	public final TableField<MantenimientoRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.mantenimiento.habilita</code>.
	 */
	public final TableField<MantenimientoRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.mantenimiento</code> table reference
	 */
	public Mantenimiento() {
		this("mantenimiento", null);
	}

	/**
	 * Create an aliased <code>matera.mantenimiento</code> table reference
	 */
	public Mantenimiento(String alias) {
		this(alias, MANTENIMIENTO);
	}

	private Mantenimiento(String alias, Table<MantenimientoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Mantenimiento(String alias, Table<MantenimientoRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<MantenimientoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_MANTENIMIENTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<MantenimientoRecord> getPrimaryKey() {
		return Keys.KEY_MANTENIMIENTO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<MantenimientoRecord>> getKeys() {
		return Arrays.<UniqueKey<MantenimientoRecord>>asList(Keys.KEY_MANTENIMIENTO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Mantenimiento as(String alias) {
		return new Mantenimiento(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Mantenimiento rename(String name) {
		return new Mantenimiento(name, null);
	}
}
