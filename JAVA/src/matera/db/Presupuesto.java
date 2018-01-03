/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.formularios.Principal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;
import matera.db.Lists.CustomLazyList;
import matera.jooq.tables.daos.MayorprofesionalDao;
import matera.jooq.tables.pojos.Mayorprofesional;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.RowListenerAdapter;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
import org.jooq.DSLContext;
/**
 *
 * @author h2o
 */
@Table("presupuesto")
@IdName("id_presupuesto")
@BelongsToParents({ 
@BelongsTo(foreignKeyName="id_entidad",parent=Entidad.class),
@BelongsTo(foreignKeyName="id_lugarCirugia",parent=Entidad.class),
@BelongsTo(foreignKeyName="id_zona",parent=Zona.class),
@BelongsTo(foreignKeyName="id_profesional1",parent=Profesional.class),
@BelongsTo(foreignKeyName="id_vendedor",parent=Vendedor.class),
@BelongsTo(foreignKeyName="id_formapago",parent=FormaPago.class),
@BelongsTo(foreignKeyName="id_plazo",parent=Plazo.class),
@BelongsTo(foreignKeyName="id_mantenimiento",parent=Mantenimiento.class),
@BelongsTo(foreignKeyName="id_motivo",parent=Motivo.class),
@BelongsTo(foreignKeyName="id_tecnico",parent=Tecnico.class),
@BelongsTo(foreignKeyName="id_prestacion",parent=Prestacion.class),
@BelongsTo(foreignKeyName="id_tipooperacion",parent=TipoOperacion.class)
})
public class Presupuesto extends Model {
    
    @Override
    public boolean equals(Object o){
        Presupuesto p = (Presupuesto) o;
        return this.getId().equals(p.getId());
    }
    
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }    
    
    public void saveCajasAsignadas(DefaultTableModel model){
        CajaAsignada.delete("id_presupuesto = ?", this.getId());
        for (int i = 0; i < model.getRowCount(); i++){

            CajaAsignada cajaAsignada = new CajaAsignada();
            cajaAsignada.set("id_presupuesto", this.getId());
            cajaAsignada.set("id_cajaDeposito", Utiles.valueAt(model, i, "id_cajadeposito"));
            cajaAsignada.saveIt();
        }    
    }
    
    public Profesional getProfesional(){
        return this.parent(Profesional.class);
    }
    
    public Entidad getEntidad(){
        return this.parent(Entidad.class);
    }
    
    public Entidad getLugarCirugia(List<Entidad> list){
        return list.stream().filter( p -> p.get("id_entidad").equals(this.get("id_lugarcirugia"))).findFirst().orElse(null);
    }    
    
    public Zona getZona(){
        return this.parent(Zona.class);
    }
    
    public Vendedor getVendedor(){
       return this.parent(Vendedor.class); 
    }
    
    public TipoOperacion getTipoOperacion(){
       return this.parent(TipoOperacion.class); 
    }
    
    public Plazo getPlazo(){
       return this.parent(Plazo.class); 
    }
    
    public Mantenimiento getMantenimiento(){
       return this.parent(Mantenimiento.class); 
    }
    
    public Motivo getMotivo(){
       return this.parent(Motivo.class); 
    }
    
    public Tecnico getTecnico(){
        if (this.getInteger("id_tecnico") > 0)
            return this.parent(Tecnico.class); 
        return null;
    }
    
    public Prestacion getPrestacion(){
        if (this.getInteger("id_prestacion") > 0)
            return this.parent(Prestacion.class); 
        return null;
    }    
    
    public Double getMontoFacturado(){
        LazyList<Factura> facturas = this.getAll(Factura.class);
        Double monto = 0.00;
        for (Factura factura : facturas){
            monto += factura.getMonto();
        }
        return monto;
    }
    
    public Double getMontoPresupuesto(){
        LazyList<PresupuestoProducto> presupuestoProductos = this.getAll(PresupuestoProducto.class);
        Double monto = 0.00;
        for (PresupuestoProducto presupuestoProducto : presupuestoProductos){
            monto += presupuestoProducto.getMonto();
        }
        return monto;
    }
    
    public Double getSaldo(){
        return this.getMontoPresupuesto() - this.getMontoFacturado();
    }
    
    public static void addRemitosDetalle(LazyList<Presupuesto> presupuestos){
        ArrayList<Integer> remitosId = new ArrayList<>();
        for (Presupuesto p : presupuestos){
            LazyList<Remito> remitos = p.getAll(Remito.class);
            for (Remito remito : remitos){
                remitosId.add(remito.getInteger("id_remito"));
            }
        }
        LazyList<RemitoDetalle> remitosDetalle = Remito.getRemitoDetalle(remitosId);
        Map<Integer, List<Model>> byRemito = remitosDetalle.parallelStream()
                .collect(Collectors.groupingBy(
                        rd -> rd.getInteger("id_remito"),
                        Collectors.mapping(rd -> rd, Collectors.toCollection(CustomLazyList::new))));        
        for (Presupuesto p : presupuestos){
            LazyList<Remito> remitos = p.getAll(Remito.class);

            for (Remito remito : remitos){
                remito.addChildren(RemitoDetalle.class, byRemito.get(remito.getInteger("id_remito")));
            }
        }    
    }
    
    public static void addConsumido(LazyList<Presupuesto> presupuestos){
        ArrayList<Integer> remitosId = new ArrayList<>();
        for (Presupuesto p : presupuestos){
            LazyList<Remito> remitos = p.getAll(Remito.class);
            for (Remito remito : remitos){
                remitosId.add(remito.getInteger("id_remito"));
            }
        }
        LazyList<Consumido> consumido = Remito.getConsumido(remitosId);
        Map<Integer, List<Model>> byRemito = consumido.parallelStream()
                .collect(Collectors.groupingBy(
                        rd -> rd.getInteger("id_remito"),
                        Collectors.mapping(rd -> rd, Collectors.toCollection(CustomLazyList::new))));        
        for (Presupuesto p : presupuestos){
            LazyList<Remito> remitos = p.getAll(Remito.class);

            for (Remito remito : remitos){
                remito.addChildren(Consumido.class, byRemito.get(remito.getInteger("id_remito")));
            }
        }    
    }
        
    /**
     * Metodo para obtener los datos de la pantalla de comparativas
     */
    public static ArrayList getComparativa(String query,Object[] params){
        ArrayList list = new ArrayList();
        String searchQuery = ""
                + "select p.id_presupuesto, p.fecha, productofact.codigo, productofact.nombre, profesional.nombre as profesional,\n" +
                  " p.paciente, presupuestoprod.pxUnit, presupuestoprod.cantidad, p.estado,"
                + " montoFacturado(p.id_presupuesto) AS montoFacturado, e.nombre as entidad,\n" +
                    " p.competencia, p.lobby, p.observaciones\n" +
                        "       from presupuesto AS p \n" +
                        "	left join presupuestoprod on presupuestoprod.id_presupuesto = p.id_presupuesto\n" +
                        "	left join productofact on presupuestoprod.id_productoFact = productofact.id_productoFact\n" +
                        "	LEFT JOIN profesional on profesional.id_profesional = p.id_profesional1\n" +
                        "	LEFT JOIN entidad as e on e.id_entidad = p.id_entidad" + " WHERE "+ query;
        //System.out.println(searchQuery);
        Base.find(searchQuery, params).with(new RowListenerAdapter() {
                    @Override
                    public void onNext(Map row) {
                        list.add(row);
                    }
                });
        return list;
    }
    
    public static ArrayList getRankingVentas(String query, Object[] params, Boolean grouped){
        ArrayList list = new ArrayList();
        String selectFields = 
                "p.id_presupuesto," +
                "productofact.id_productofact," +
                "productofact.nombre as producto," +
                "profesional.nombre as profesional," +
                "profesional.id_profesional as id_profesional," + 
                "presupuestoprod.pxUnit," +
                "presupuestoprod.cantidad," +
                "montoFacturado(p.id_presupuesto) as montoFacturado," + 
                "montoPresupuesto(p.id_presupuesto) as montoPresupuesto";
        
        if (grouped){
        selectFields += 
                        ",SUM(presupuestoprod.pxUnit * presupuestoprod.cantidad) as precioAgrupado," +
			"SUM(presupuestoprod.cantidad) as cantidadAgrupada";
        }
        
        String joinFields = 
            " left join presupuestoprod on presupuestoprod.id_presupuesto = p.id_presupuesto" +
            " left join productofact on presupuestoprod.id_productoFact = productofact.id_productoFact" +
            " LEFT JOIN profesional on profesional.id_profesional = p.id_profesional1" +
            " LEFT JOIN entidad as e on e.id_entidad = p.id_entidad";
        String searchQuery = 
                "SELECT " +
                selectFields +
                " from presupuesto AS p" +
                joinFields +
                " WHERE " +
                query;
        //System.out.println(searchQuery);
        Base.find(searchQuery, params).with(new RowListenerAdapter() {
                    @Override
                    public void onNext(Map row) {
                        list.add(row);
                    }
                });        
        return list;
    }
    
    public void rmToMayorProfesional(DSLContext dsl) throws Exception{
        MayorprofesionalDao mayorProfesionalDao = new MayorprofesionalDao(dsl.configuration());
        Mayorprofesional mayorprofesional = new Mayorprofesional();
        Date date = new Date(new java.util.Date().getTime());

        mayorprofesional.setFecha(date);
        mayorprofesional.setIdPresupuesto(this.getInteger("id_presupuesto"));
        mayorprofesional.setIdProfesional(this.getInteger("id_profesional1"));
        mayorprofesional.setDc("C");
        mayorprofesional.setImporte(this.getBigDecimal("rm1"));
        mayorprofesional.setObservaciones("");
        mayorprofesional.setIdEmpresa(Principal.getIdEmpresa());
        mayorprofesional.setUsuario(Principal.getUsuarioName());
        mayorprofesional.setTransferencia("N");
        mayorprofesional.setPreliquidacion(0);
        mayorprofesional.setLiquidacion(-1);

        if (this.getBigDecimal("rm1").compareTo(BigDecimal.ZERO) == 1)
            mayorProfesionalDao.insert(mayorprofesional);

        if (this.getBigDecimal("rm2").compareTo(BigDecimal.ZERO) == 1){
            mayorprofesional.setIdProfesional(this.getInteger("id_profesional2"));
            mayorprofesional.setImporte(this.getBigDecimal("rm2"));
            mayorProfesionalDao.insert(mayorprofesional);
        }

        if (this.getBigDecimal("rm3").compareTo(BigDecimal.ZERO) == 1){
            mayorprofesional.setIdProfesional(this.getInteger("id_profesional3"));
            mayorprofesional.setImporte(this.getBigDecimal("rm3"));
            mayorProfesionalDao.insert(mayorprofesional);
        }
    }
}
