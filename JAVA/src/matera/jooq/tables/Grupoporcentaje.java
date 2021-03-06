/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.GrupoporcentajeRecord;

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
public class Grupoporcentaje extends TableImpl<GrupoporcentajeRecord> {

	private static final long serialVersionUID = 1334480160;

	/**
	 * The reference instance of <code>matera.grupoporcentaje</code>
	 */
	public static final Grupoporcentaje GRUPOPORCENTAJE = new Grupoporcentaje();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<GrupoporcentajeRecord> getRecordType() {
		return GrupoporcentajeRecord.class;
	}

	/**
	 * The column <code>matera.grupoporcentaje.id_grupoPorcentaje</code>.
	 */
	public final TableField<GrupoporcentajeRecord, Integer> ID_GRUPOPORCENTAJE = createField("id_grupoPorcentaje", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.grupoporcentaje.nombre</code>.
	 */
	public final TableField<GrupoporcentajeRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * The column <code>matera.grupoporcentaje.habilita</code>.
	 */
	public final TableField<GrupoporcentajeRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).defaulted(true), this, "");

	/**
	 * The column <code>matera.grupoporcentaje.id_empresa</code>.
	 */
	public final TableField<GrupoporcentajeRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * Create a <code>matera.grupoporcentaje</code> table reference
	 */
	public Grupoporcentaje() {
		this("grupoporcentaje", null);
	}

	/**
	 * Create an aliased <code>matera.grupoporcentaje</code> table reference
	 */
	public Grupoporcentaje(String alias) {
		this(alias, GRUPOPORCENTAJE);
	}

	private Grupoporcentaje(String alias, Table<GrupoporcentajeRecord> aliased) {
		this(alias, aliased, null);
	}

	private Grupoporcentaje(String alias, Table<GrupoporcentajeRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<GrupoporcentajeRecord, Integer> getIdentity() {
		return Keys.IDENTITY_GRUPOPORCENTAJE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<GrupoporcentajeRecord> getPrimaryKey() {
		return Keys.KEY_GRUPOPORCENTAJE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<GrupoporcentajeRecord>> getKeys() {
		return Arrays.<UniqueKey<GrupoporcentajeRecord>>asList(Keys.KEY_GRUPOPORCENTAJE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Grupoporcentaje as(String alias) {
		return new Grupoporcentaje(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Grupoporcentaje rename(String name) {
		return new Grupoporcentaje(name, null);
	}
}
