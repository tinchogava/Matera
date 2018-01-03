/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import matera.jooq.tables.pojos.LogEventoRemito;
import matera.jooq.tables.pojos.LogEventoTipo;
import matera.jooq.tables.pojos.Remito;
import matera.jooq.tables.pojos.Usuario;

/**
 *
 * @author h2o
 */
@AllArgsConstructor
public class EventoRemitoDTO {
    @Getter @Setter LogEventoRemito evento;
    @Getter @Setter LogEventoTipo tipo;
    @Getter @Setter Usuario usuario;
    @Getter @Setter Remito remito;
}
