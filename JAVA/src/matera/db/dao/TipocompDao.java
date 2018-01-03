/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dao;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author h2o
 */
public class TipocompDao extends matera.jooq.tables.daos.TipocompDao {
    
    public TipocompDao(){
        super(ActiveDatabase.getDSLContext().configuration());
    }
    
    public static List<Integer> getRecibosIdList(){       
        return Arrays.asList(4, 16, 17, 18, 19);
    }
    
}
