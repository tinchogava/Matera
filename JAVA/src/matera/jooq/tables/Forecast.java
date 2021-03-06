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
import matera.jooq.tables.records.ForecastRecord;

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
public class Forecast extends TableImpl<ForecastRecord> {

	private static final long serialVersionUID = -149941881;

	/**
	 * The reference instance of <code>matera.forecast</code>
	 */
	public static final Forecast FORECAST = new Forecast();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ForecastRecord> getRecordType() {
		return ForecastRecord.class;
	}

	/**
	 * The column <code>matera.forecast.id_forecast</code>.
	 */
	public final TableField<ForecastRecord, Integer> ID_FORECAST = createField("id_forecast", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.forecast.anio</code>.
	 */
	public final TableField<ForecastRecord, Integer> ANIO = createField("anio", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>matera.forecast.id_vendedor</code>.
	 */
	public final TableField<ForecastRecord, Integer> ID_VENDEDOR = createField("id_vendedor", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>matera.forecast.id_entidad</code>.
	 */
	public final TableField<ForecastRecord, Integer> ID_ENTIDAD = createField("id_entidad", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>matera.forecast.id_productoFact</code>.
	 */
	public final TableField<ForecastRecord, Integer> ID_PRODUCTOFACT = createField("id_productoFact", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>matera.forecast.cantidad</code>.
	 */
	public final TableField<ForecastRecord, Double> CANTIDAD = createField("cantidad", org.jooq.impl.SQLDataType.DOUBLE, this, "");

	/**
	 * The column <code>matera.forecast.usuario</code>.
	 */
	public final TableField<ForecastRecord, String> USUARIO = createField("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

	/**
	 * The column <code>matera.forecast.fechaReal</code>.
	 */
	public final TableField<ForecastRecord, Timestamp> FECHAREAL = createField("fechaReal", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * Create a <code>matera.forecast</code> table reference
	 */
	public Forecast() {
		this("forecast", null);
	}

	/**
	 * Create an aliased <code>matera.forecast</code> table reference
	 */
	public Forecast(String alias) {
		this(alias, FORECAST);
	}

	private Forecast(String alias, Table<ForecastRecord> aliased) {
		this(alias, aliased, null);
	}

	private Forecast(String alias, Table<ForecastRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ForecastRecord, Integer> getIdentity() {
		return Keys.IDENTITY_FORECAST;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ForecastRecord> getPrimaryKey() {
		return Keys.KEY_FORECAST_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ForecastRecord>> getKeys() {
		return Arrays.<UniqueKey<ForecastRecord>>asList(Keys.KEY_FORECAST_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forecast as(String alias) {
		return new Forecast(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Forecast rename(String name) {
		return new Forecast(name, null);
	}
}
