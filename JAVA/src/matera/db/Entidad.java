/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.common.Util;
/**
 *
 * @author h2o
 */
@Table("entidad")
@IdName("id_entidad")
public class Entidad extends Model {
    
    public static List findByIds(List ids){
        return Entidad.where("id_entidad IN ('" + Util.join(ids, "', '") + "')");    
    }    
}
