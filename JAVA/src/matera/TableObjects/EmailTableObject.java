/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import matera.jooq.Tables;
import matera.jooq.tables.records.EmailreciptersRecord;
import matera.jooq.tables.records.UsuarioRecord;
import matera.jooq.tables.records.ZonaRecord;
import org.jooq.Record;

/**
 *
 * @author tinchogava
 */
public class EmailTableObject {
    EmailreciptersRecord emailrecipters;
    UsuarioRecord usuario;
    ZonaRecord zona;

    public ZonaRecord getZona() {
        return zona;
    }

    public void setZona(ZonaRecord zona) {
        this.zona = zona;
    }

    public EmailTableObject(EmailreciptersRecord emailrecipters, UsuarioRecord usuario, ZonaRecord zona) {
        this.emailrecipters = emailrecipters;
        this.usuario = usuario;
        this.zona = zona;
    }

    public EmailTableObject(Record r) {
        this.emailrecipters = r.into(Tables.EMAILRECIPTERS);
        this.usuario = r.into(Tables.USUARIO);
        this.zona = r.into(Tables.ZONA);
    }

    public EmailreciptersRecord getEmailrecipters() {
        return emailrecipters;
    }

    public void setEmailrecipters(EmailreciptersRecord emailrecipters) {
        this.emailrecipters = emailrecipters;
    }

    public UsuarioRecord getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioRecord usuario) {
        this.usuario = usuario;
    }

}
