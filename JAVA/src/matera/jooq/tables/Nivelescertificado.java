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
import matera.jooq.tables.records.NivelescertificadoRecord;

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
public class Nivelescertificado extends TableImpl<NivelescertificadoRecord> {

	private static final long serialVersionUID = -2133886568;

	/**
	 * The reference instance of <code>matera.nivelescertificado</code>
	 */
	public static final Nivelescertificado NIVELESCERTIFICADO = new Nivelescertificado();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<NivelescertificadoRecord> getRecordType() {
		return NivelescertificadoRecord.class;
	}

	/**
	 * The column <code>matera.nivelescertificado.id_nivelesCertificado</code>.
	 */
	public final TableField<NivelescertificadoRecord, Integer> ID_NIVELESCERTIFICADO = createField("id_nivelesCertificado", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.nivelescertificado.id_presupuesto</code>.
	 */
	public final TableField<NivelescertificadoRecord, Integer> ID_PRESUPUESTO = createField("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.nivelescertificado.alternativas</code>.
	 */
	public final TableField<NivelescertificadoRecord, Integer> ALTERNATIVAS = createField("alternativas", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.nivelescertificado.instrumental</code>.
	 */
	public final TableField<NivelescertificadoRecord, Integer> INSTRUMENTAL = createField("instrumental", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.nivelescertificado.asistencia</code>.
	 */
	public final TableField<NivelescertificadoRecord, Integer> ASISTENCIA = createField("asistencia", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.nivelescertificado.puntualidad</code>.
	 */
	public final TableField<NivelescertificadoRecord, Integer> PUNTUALIDAD = createField("puntualidad", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.nivelescertificado.controlado</code>.
	 */
	public final TableField<NivelescertificadoRecord, String> CONTROLADO = createField("controlado", org.jooq.impl.SQLDataType.VARCHAR.length(1).defaulted(true), this, "");

	/**
	 * The column <code>matera.nivelescertificado.usuario</code>.
	 */
	public final TableField<NivelescertificadoRecord, String> USUARIO = createField("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * The column <code>matera.nivelescertificado.fechaReal</code>.
	 */
	public final TableField<NivelescertificadoRecord, Timestamp> FECHAREAL = createField("fechaReal", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * Create a <code>matera.nivelescertificado</code> table reference
	 */
	public Nivelescertificado() {
		this("nivelescertificado", null);
	}

	/**
	 * Create an aliased <code>matera.nivelescertificado</code> table reference
	 */
	public Nivelescertificado(String alias) {
		this(alias, NIVELESCERTIFICADO);
	}

	private Nivelescertificado(String alias, Table<NivelescertificadoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Nivelescertificado(String alias, Table<NivelescertificadoRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<NivelescertificadoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_NIVELESCERTIFICADO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<NivelescertificadoRecord> getPrimaryKey() {
		return Keys.KEY_NIVELESCERTIFICADO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<NivelescertificadoRecord>> getKeys() {
		return Arrays.<UniqueKey<NivelescertificadoRecord>>asList(Keys.KEY_NIVELESCERTIFICADO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Nivelescertificado as(String alias) {
		return new Nivelescertificado(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Nivelescertificado rename(String name) {
		return new Nivelescertificado(name, null);
	}
}
