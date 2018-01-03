/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import matera.jooq.tables.pojos.LogEventoPresupuesto;
import matera.jooq.tables.pojos.LogEventoTipo;
import matera.jooq.tables.pojos.Usuario;

/**
 *
 * @author h2o
 */
@AllArgsConstructor
public class EventoPresupuestoDTO {
    @Getter @Setter LogEventoPresupuesto evento;
    @Getter @Setter LogEventoTipo tipo;
    @Getter @Setter Usuario usuario;
}
