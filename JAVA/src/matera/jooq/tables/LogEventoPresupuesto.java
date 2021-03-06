/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.LogEventoPresupuestoRecord;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


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
public class LogEventoPresupuesto extends TableImpl<LogEventoPresupuestoRecord> {

	private static final long serialVersionUID = 1852686438;

	/**
	 * The reference instance of <code>matera.log_evento_presupuesto</code>
	 */
	public static final LogEventoPresupuesto LOG_EVENTO_PRESUPUESTO = new LogEventoPresupuesto();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<LogEventoPresupuestoRecord> getRecordType() {
		return LogEventoPresupuestoRecord.class;
	}

	/**
	 * The column <code>matera.log_evento_presupuesto.id</code>.
	 */
	public final TableField<LogEventoPresupuestoRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.log_evento_presupuesto.timestamp</code>.
	 */
	public final TableField<LogEventoPresupuestoRecord, Timestamp> TIMESTAMP = createField("timestamp", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.log_evento_presupuesto.id_log_evento_tipo</code>.
	 */
	public final TableField<LogEventoPresupuestoRecord, Integer> ID_LOG_EVENTO_TIPO = createField("id_log_evento_tipo", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.log_evento_presupuesto.id_usuario</code>.
	 */
	public final TableField<LogEventoPresupuestoRecord, UInteger> ID_USUARIO = createField("id_usuario", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>matera.log_evento_presupuesto.id_presupuesto</code>.
	 */
	public final TableField<LogEventoPresupuestoRecord, Integer> ID_PRESUPUESTO = createField("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>matera.log_evento_presupuesto</code> table reference
	 */
	public LogEventoPresupuesto() {
		this("log_evento_presupuesto", null);
	}

	/**
	 * Create an aliased <code>matera.log_evento_presupuesto</code> table reference
	 */
	public LogEventoPresupuesto(String alias) {
		this(alias, LOG_EVENTO_PRESUPUESTO);
	}

	private LogEventoPresupuesto(String alias, Table<LogEventoPresupuestoRecord> aliased) {
		this(alias, aliased, null);
	}

	private LogEventoPresupuesto(String alias, Table<LogEventoPresupuestoRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<LogEventoPresupuestoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_LOG_EVENTO_PRESUPUESTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<LogEventoPresupuestoRecord> getPrimaryKey() {
		return Keys.KEY_LOG_EVENTO_PRESUPUESTO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<LogEventoPresupuestoRecord>> getKeys() {
		return Arrays.<UniqueKey<LogEventoPresupuestoRecord>>asList(Keys.KEY_LOG_EVENTO_PRESUPUESTO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogEventoPresupuesto as(String alias) {
		return new LogEventoPresupuesto(alias, this);
	}

	/**
	 * Rename this table
	 */
	public LogEventoPresupuesto rename(String name) {
		return new LogEventoPresupuesto(name, null);
	}
}
