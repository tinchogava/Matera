/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.Utilidades;

import org.javalite.activejdbc.Model;

/**
 *
 * @author h2o
 */
public class QueryBuilder {
    private String query;
    
    public QueryBuilder(){
        query = "";
    }
    
    public void select(Model m){
        query = "SELECT " + m.getMetaModel().getTableName() + ".*"; 
    }
    
    public void join(Model m){
        query += " JOIN " + m.getMetaModel().getTableName();
    }
    
    public void on(String str){
        query += " ON "+str;
    }
    
    public void And(String str){
        if("".equals(query)) query = str; else query += " AND " + str;
    }
    
    public void Or(String str){
        if("".equals(query)) query = str; else query += " AND " + str;
    }  
    
    public void groupBy(String str){
        query += " GROUP BY " + str;
    }
    
    public void orderBy(String str){
        query += " ORDER BY " + str;
    }    
    
    public String getQuery(){
        return query;
    }
}
