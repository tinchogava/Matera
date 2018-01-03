/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dao;

import ar.com.bosoft.conexion.ActiveDatabase;

/**
 *
 * @author h2o
 */
public class RemitoConsumidoDao extends matera.jooq.tables.daos.RemitoConsumidoDao {
    public RemitoConsumidoDao(){
        super(ActiveDatabase.getDSLContext().configuration());
    }    
}
