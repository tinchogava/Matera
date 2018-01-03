/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.PorcentajeRecord;

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
public class Porcentaje extends TableImpl<PorcentajeRecord> {

	private static final long serialVersionUID = 1020736570;

	/**
	 * The reference instance of <code>matera.porcentaje</code>
	 */
	public static final Porcentaje PORCENTAJE = new Porcentaje();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<PorcentajeRecord> getRecordType() {
		return PorcentajeRecord.class;
	}

	/**
	 * The column <code>matera.porcentaje.id_porcentaje</code>.
	 */
	public final TableField<PorcentajeRecord, Integer> ID_PORCENTAJE = createField("id_porcentaje", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.porcentaje.id_grupoPorcentaje</code>.
	 */
	public final TableField<PorcentajeRecord, Integer> ID_GRUPOPORCENTAJE = createField("id_grupoPorcentaje", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.porcentaje.id_especialidad</code>.
	 */
	public final TableField<PorcentajeRecord, Integer> ID_ESPECIALIDAD = createField("id_especialidad", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.porcentaje.id_zona</code>.
	 */
	public final TableField<PorcentajeRecord, Integer> ID_ZONA = createField("id_zona", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.porcentaje.valor</code>.
	 */
	public final TableField<PorcentajeRecord, BigDecimal> VALOR = createField("valor", org.jooq.impl.SQLDataType.DECIMAL.precision(5, 2).defaulted(true), this, "");

	/**
	 * The column <code>matera.porcentaje.id_empresa</code>.
	 */
	public final TableField<PorcentajeRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.porcentaje.usuario</code>.
	 */
	public final TableField<PorcentajeRecord, String> USUARIO = createField("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * The column <code>matera.porcentaje.fechaReal</code>.
	 */
	public final TableField<PorcentajeRecord, Timestamp> FECHAREAL = createField("fechaReal", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * Create a <code>matera.porcentaje</code> table reference
	 */
	public Porcentaje() {
		this("porcentaje", null);
	}

	/**
	 * Create an aliased <code>matera.porcentaje</code> table reference
	 */
	public Porcentaje(String alias) {
		this(alias, PORCENTAJE);
	}

	private Porcentaje(String alias, Table<PorcentajeRecord> aliased) {
		this(alias, aliased, null);
	}

	private Porcentaje(String alias, Table<PorcentajeRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<PorcentajeRecord, Integer> getIdentity() {
		return Keys.IDENTITY_PORCENTAJE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<PorcentajeRecord> getPrimaryKey() {
		return Keys.KEY_PORCENTAJE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<PorcentajeRecord>> getKeys() {
		return Arrays.<UniqueKey<PorcentajeRecord>>asList(Keys.KEY_PORCENTAJE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Porcentaje as(String alias) {
		return new Porcentaje(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Porcentaje rename(String name) {
		return new Porcentaje(name, null);
	}
}