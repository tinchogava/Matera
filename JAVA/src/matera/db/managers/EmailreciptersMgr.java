/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.util.ArrayList;
import java.util.List;
import matera.TableObjects.EmailTableObject;
import matera.jooq.Tables;
import matera.jooq.tables.records.EmailreciptersRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class EmailreciptersMgr {
    
    public static List<EmailTableObject> getTableEmailRecipters(){
        return getTableEmailRecipters(ActiveDatabase.getDSLContext());
    }
    
    public static Result<EmailreciptersRecord> getEmailRecipters(Integer id_zona){
        return getEmailRecipters(ActiveDatabase.getDSLContext(), id_zona);
    }
    
    public static int storeEmailRecipter(EmailreciptersRecord emailreciptersRecord){
        //emailreciptersRecord.attach(ActiveDatabase.getDSLContext().configuration());
        return emailreciptersRecord.store();
    }
    
    private static List<EmailTableObject> getTableEmailRecipters(DSLContext dsl){
        List<EmailTableObject> list = new ArrayList<>();
        dsl.select()
                .from(Tables.EMAILRECIPTERS)
                .leftJoin(Tables.USUARIO)
                .on(Tables.USUARIO.ID_USUARIO.eq(Tables.EMAILRECIPTERS.IDUSUARIO))
                .leftJoin(Tables.ZONA)
                .on(Tables.ZONA.ID_ZONA.eq(Tables.USUARIO.ID_ZONA))
                .fetch()
                    .into((Record r1) -> {
                    list.add(
                            new EmailTableObject(r1)
                            );
                    });
        return list;
    }

    private static Result<EmailreciptersRecord> getEmailRecipters(DSLContext dslContext, Integer id_zona) {
        return dslContext.select()
                .from(Tables.EMAILRECIPTERS)
                .join(Tables.USUARIO).on(Tables.USUARIO.ID_USUARIO.eq(Tables.EMAILRECIPTERS.IDUSUARIO))
                .where(Tables.USUARIO.ID_ZONA.eq(id_zona))
                .or(Tables.USUARIO.ID_ZONA.eq(0))
                .fetchInto(Tables.EMAILRECIPTERS);
    }  
}
