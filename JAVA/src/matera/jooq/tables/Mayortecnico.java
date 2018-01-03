/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.MayortecnicoRecord;

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
public class Mayortecnico extends TableImpl<MayortecnicoRecord> {

	private static final long serialVersionUID = -702832247;

	/**
	 * The reference instance of <code>matera.mayortecnico</code>
	 */
	public static final Mayortecnico MAYORTECNICO = new Mayortecnico();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<MayortecnicoRecord> getRecordType() {
		return MayortecnicoRecord.class;
	}

	/**
	 * The column <code>matera.mayortecnico.id_mayorTecnico</code>.
	 */
	public final TableField<MayortecnicoRecord, Integer> ID_MAYORTECNICO = createField("id_mayorTecnico", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.mayortecnico.fecha</code>.
	 */
	public final TableField<MayortecnicoRecord, Date> FECHA = createField("fecha", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>matera.mayortecnico.id_presupuesto</code>.
	 */
	public final TableField<MayortecnicoRecord, Integer> ID_PRESUPUESTO = createField("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.mayortecnico.id_tecnico</code>.
	 */
	public final TableField<MayortecnicoRecord, Integer> ID_TECNICO = createField("id_tecnico", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.mayortecnico.dc</code>.
	 */
	public final TableField<MayortecnicoRecord, String> DC = createField("dc", org.jooq.impl.SQLDataType.VARCHAR.length(1).defaulted(true), this, "");

	/**
	 * The column <code>matera.mayortecnico.importe</code>.
	 */
	public final TableField<MayortecnicoRecord, BigDecimal> IMPORTE = createField("importe", org.jooq.impl.SQLDataType.DECIMAL.precision(7, 2).defaulted(true), this, "");

	/**
	 * The column <code>matera.mayortecnico.observaciones</code>.
	 */
	public final TableField<MayortecnicoRecord, String> OBSERVACIONES = createField("observaciones", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>matera.mayortecnico.liquidacion</code>.
	 */
	public final TableField<MayortecnicoRecord, Integer> LIQUIDACION = createField("liquidacion", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.mayortecnico.id_empresa</code>.
	 */
	public final TableField<MayortecnicoRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.mayortecnico.usuario</code>.
	 */
	public final TableField<MayortecnicoRecord, String> USUARIO = createField("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * The column <code>matera.mayortecnico.fechaReal</code>.
	 */
	public final TableField<MayortecnicoRecord, Timestamp> FECHAREAL = createField("fechaReal", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * Create a <code>matera.mayortecnico</code> table reference
	 */
	public Mayortecnico() {
		this("mayortecnico", null);
	}

	/**
	 * Create an aliased <code>matera.mayortecnico</code> table reference
	 */
	public Mayortecnico(String alias) {
		this(alias, MAYORTECNICO);
	}

	private Mayortecnico(String alias, Table<MayortecnicoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Mayortecnico(String alias, Table<MayortecnicoRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<MayortecnicoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_MAYORTECNICO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<MayortecnicoRecord> getPrimaryKey() {
		return Keys.KEY_MAYORTECNICO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<MayortecnicoRecord>> getKeys() {
		return Arrays.<UniqueKey<MayortecnicoRecord>>asList(Keys.KEY_MAYORTECNICO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Mayortecnico as(String alias) {
		return new Mayortecnico(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Mayortecnico rename(String name) {
		return new Mayortecnico(name, null);
	}
}
