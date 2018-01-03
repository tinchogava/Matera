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
import matera.jooq.tables.records.UsuarioRecord;

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
public class Usuario extends TableImpl<UsuarioRecord> {

	private static final long serialVersionUID = 384983769;

	/**
	 * The reference instance of <code>matera.usuario</code>
	 */
	public static final Usuario USUARIO = new Usuario();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UsuarioRecord> getRecordType() {
		return UsuarioRecord.class;
	}

	/**
	 * The column <code>matera.usuario.id_usuario</code>.
	 */
	public final TableField<UsuarioRecord, UInteger> ID_USUARIO = createField("id_usuario", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>matera.usuario.nombre</code>.
	 */
	public final TableField<UsuarioRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.documento</code>.
	 */
	public final TableField<UsuarioRecord, String> DOCUMENTO = createField("documento", org.jooq.impl.SQLDataType.VARCHAR.length(10).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.cuil</code>.
	 */
	public final TableField<UsuarioRecord, String> CUIL = createField("cuil", org.jooq.impl.SQLDataType.VARCHAR.length(11).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.fechaNac</code>.
	 */
	public final TableField<UsuarioRecord, Date> FECHANAC = createField("fechaNac", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>matera.usuario.direccion</code>.
	 */
	public final TableField<UsuarioRecord, String> DIRECCION = createField("direccion", org.jooq.impl.SQLDataType.VARCHAR.length(100).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.codPostal</code>.
	 */
	public final TableField<UsuarioRecord, String> CODPOSTAL = createField("codPostal", org.jooq.impl.SQLDataType.VARCHAR.length(10).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.id_provincia</code>.
	 */
	public final TableField<UsuarioRecord, Integer> ID_PROVINCIA = createField("id_provincia", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.id_departamento</code>.
	 */
	public final TableField<UsuarioRecord, Integer> ID_DEPARTAMENTO = createField("id_departamento", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.id_localidad</code>.
	 */
	public final TableField<UsuarioRecord, Integer> ID_LOCALIDAD = createField("id_localidad", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.fechaIngreso</code>.
	 */
	public final TableField<UsuarioRecord, Date> FECHAINGRESO = createField("fechaIngreso", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>matera.usuario.fechaEgreso</code>.
	 */
	public final TableField<UsuarioRecord, Date> FECHAEGRESO = createField("fechaEgreso", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>matera.usuario.usaSistema</code>.
	 */
	public final TableField<UsuarioRecord, String> USASISTEMA = createField("usaSistema", org.jooq.impl.SQLDataType.VARCHAR.length(1).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.contraseña</code>.
	 */
	public final TableField<UsuarioRecord, String> CONTRASEÑA = createField("contraseña", org.jooq.impl.SQLDataType.VARCHAR.length(15).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.email</code>.
	 */
	public final TableField<UsuarioRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.id_zona</code>.
	 */
	public final TableField<UsuarioRecord, Integer> ID_ZONA = createField("id_zona", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.id_zonaSistema</code>.
	 */
	public final TableField<UsuarioRecord, Integer> ID_ZONASISTEMA = createField("id_zonaSistema", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.habilita</code>.
	 */
	public final TableField<UsuarioRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).defaulted(true), this, "");

	/**
	 * The column <code>matera.usuario.id_empresa</code>.
	 */
	public final TableField<UsuarioRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * Create a <code>matera.usuario</code> table reference
	 */
	public Usuario() {
		this("usuario", null);
	}

	/**
	 * Create an aliased <code>matera.usuario</code> table reference
	 */
	public Usuario(String alias) {
		this(alias, USUARIO);
	}

	private Usuario(String alias, Table<UsuarioRecord> aliased) {
		this(alias, aliased, null);
	}

	private Usuario(String alias, Table<UsuarioRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<UsuarioRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<UsuarioRecord> getPrimaryKey() {
		return Keys.KEY_USUARIO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<UsuarioRecord>> getKeys() {
		return Arrays.<UniqueKey<UsuarioRecord>>asList(Keys.KEY_USUARIO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usuario as(String alias) {
		return new Usuario(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Usuario rename(String name) {
		return new Usuario(name, null);
	}
}