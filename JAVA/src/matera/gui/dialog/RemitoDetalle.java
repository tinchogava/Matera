/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package matera.gui.dialog;

import ar.com.bosoft.DataSources.ConsumidoDataSource;
import ar.com.bosoft.DataSources.RemitoDataSource;
import ar.com.bosoft.Utilidades.DateUtil;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.formularios.Principal;
import ar.com.matera.TableModels.RemitoDetalleTableModel;
import com.google.common.base.Optional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import matera.db.ClasiProducto;
import matera.gui.helper.ExcelAdapter;
import matera.gui.renderers.TrazabilidadRenderer;
import matera.db.dao.PresupuestoDao;
import matera.db.dao.RemitoDao;
import matera.jooq.tables.pojos.Presupuesto;
import matera.jooq.tables.pojos.Remito;
import matera.workers.ActionWorker;

import org.javalite.activejdbc.LazyList;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class RemitoDetalle extends javax.swing.JDialog {
    String numComp;
    DefaultTableModel modeloRemito;
    RemitoDetalleTableModel rdModel;
    SimpleDateFormat formatter;
    Salida salida;
    Integer id_presupuesto;
    Presupuesto presupuesto;
    
    public RemitoDetalle(java.awt.Frame parent, Boolean modal, Integer id_presupuesto){
        super(parent, modal);
        
        this.id_presupuesto = id_presupuesto;
        presupuesto = new PresupuestoDao().fetchOneByIdPresupuesto(id_presupuesto);
        
        initComponents();
        
        setTitle("Detalle de remitos");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png")).getImage());
        setLocationRelativeTo(parent);
        
        rdModel = new RemitoDetalleTableModel();
        rdModel.getPropertiesFromDefaultModel(rdTable.getModel());
        rdTable.setModel(rdModel);
        rdTable.setDefaultRenderer(Object.class, new TrazabilidadRenderer());
        ExcelAdapter adapter = new ExcelAdapter(rdTable);        
        
        this.modeloRemito  = (DefaultTableModel) jTablaRemito.getModel();
        this.jTablaRemito.setModel(modeloRemito);
        
        this.formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.salida = new Salida(null, true);
    }
    
    public void setDatos(){
        try {
            try{
                this.jLblTurno.setText(String.valueOf(id_presupuesto));
                this.jLblFechaCx.setText(presupuesto.getFechacirugia().toString());
                this.jLblEntidad.setText(presupuesto.getEntidad().getNombre());
                this.jLblLugarCx.setText(presupuesto.getLugarCx().getNombre());
                this.jLblPaciente.setText(presupuesto.getPaciente());
                this.jLblProfesional.setText(presupuesto.getProfesional().getNombre());
                this.jLblTipoPrestacion.setText(presupuesto.getPrestacion().getNombre());

                this.jLblTecnico.setText(presupuesto.getTecnico().getNombre());         
            }
            catch(Exception e){
            
            }

            
            List<Remito> remitos = new RemitoDao().fetchByIdPresupuesto(id_presupuesto);
            
            remitos.forEach(r->{
                Object[] remito = {r.getFecha(), r.getNumcomp(), r.getIdRemito(), r.getCajas(), r.getFechaconsumido(), r.getObservaciones()};
                modeloRemito.addRow(remito);
            });
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }

    private void imprime(){
        try {
            Map param=new HashMap();
            
            Date fecha = DateUtil.stringToDate(this.jLblFechaCx.getText());
            param.put("id_presupuesto", Integer.parseInt(this.jLblTurno.getText()));
            param.put("fechaCx", fecha);
            param.put("entidad", this.jLblEntidad.getText());
            param.put("lugarCx", this.jLblLugarCx.getText());
            param.put("paciente", this.jLblPaciente.getText());
            param.put("profesional", this.jLblProfesional.getText());
            param.put("remito", this.numComp);
            param.put("tipoPrestacion", this.jLblTipoPrestacion.getText());
            param.put("tecnico", this.jLblTecnico.getText());
            param.put("observaciones", presupuesto.getObservaciones());
            param.put("empresa", Principal.getEmpresaName());
            param.put("previa", false);
            
            Date fechaConsumido = this.jLblFechaConsumido.getText().isEmpty() ? null : DateUtil.stringToDate(jLblFechaConsumido.getText());
            param.put("fechaCarga", fechaConsumido);
            param.put("cajas", this.jLblCajas.getText());
            
            ConsumidoDataSource dataConsumido = new ConsumidoDataSource();
            
            /*
            Agrupo los productos con mismo SERIAL para imprimir sus cantidades agrupadas
            */
            rdModel.getRowsAsList().stream().collect(groupingBy(
                r -> r,
                    Collectors.summingInt(r-> 1)
                )
            ).forEach((k,v)->{
                k.setCantidad(v);
                dataConsumido.add(k.getConsumidoData());
            });
            
            if (!dataConsumido.isEmpty()) {
                Reporte reporte = new Reporte();
                reporte.startReport("Consumido", param, dataConsumido, salida.getTipoSalida(), salida.getRutaArchivo(), salida.getImpresora(), salida.getCopias());
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }  
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }
    
    private void verDetalle(){
        try {
            rdModel.removeAllRows();
            rdModel.fireTableDataChanged();

            int fila = jTablaRemito.convertRowIndexToModel(jTablaRemito.getSelectedRow());
            int id_remito = (int) modeloRemito.getValueAt(fila, 2);

            new RemitoDao().getConsumidoDetalle(id_remito).forEach(r->rdModel.addRow(r));

            this.numComp = modeloRemito.getValueAt(fila, 1).toString();
            this.jLblFechaConsumido.setText(Utiles.valueAt(modeloRemito, fila, "fecha_consumido").toString());
            this.jLblCajas.setText(Utiles.valueAt(modeloRemito, fila, "cajas").toString());
            
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }
    
    private void imprimeRemito(){
        try {
            if (this.jTablaRemito.getSelectedRow() >= 0) {                
                int fila = jTablaRemito.convertRowIndexToModel(this.jTablaRemito.getSelectedRow());
                Date fecha = DateUtil.stringToDate(Utiles.valueAt(modeloRemito, fila, "fecha"));
                String lugarCirugia = this.jLblLugarCx.getText();
                String dirLugarCirugia = "";
                String entidad = this.jLblEntidad.getText();
                String profesional = this.jLblProfesional.getText();
                String paciente = this.jLblPaciente.getText();
                String telefono = "";
                String obs = Optional.fromNullable(Utiles.valueAt(modeloRemito, fila, "observaciones")).or("").toString();
                String cajas = modeloRemito.getValueAt(fila, 3).toString();
                
                String tipo = "";
                Map param=new HashMap();
            
                param.put("numComp", numComp);
                param.put("fecha", fecha);
                param.put("lugarCirugia", lugarCirugia);
                param.put("dirLugarCirugia", dirLugarCirugia);
                param.put("entidad", entidad);
                param.put("profesional", profesional);
                param.put("paciente", paciente);
                param.put("id_presupuesto", presupuesto.getIdPresupuesto());
                param.put("telefono", telefono);
                param.put("observaciones", presupuesto.getObservaciones() + "\n" + obs);
                param.put("cajas", cajas);
                
                Integer id_remito = Integer.parseInt(Utiles.valueAt(modeloRemito, fila, "id_remito").toString());
                
                matera.db.Remito remito = matera.db.Remito.findById(id_remito);
                
                List<matera.db.RemitoDetalle> enviado = remito.getEnviado();
                
                if (enviado.size() > 0){
                    RemitoDataSource data = new RemitoDataSource();

                    LazyList<ClasiProducto> clasiProductos = ClasiProducto.findAll();

                    for(ClasiProducto cp : clasiProductos){
                        param.put("tipo", cp.get("nombre"));
                        List filteredByClass = new ArrayList();
                        filteredByClass.addAll(enviado);
                        filteredByClass = matera.db.RemitoDetalle.filterByProductoField(filteredByClass, "id_clasiproducto", cp.getId());
                        if (filteredByClass.size() > 0){
                            matera.db.Remito.fillRemitoDetalleReport(data, filteredByClass);
                            String nombreReporte = "Remito";
                            Reporte reporte = new Reporte();
                            reporte.startReport(nombreReporte, param, data, salida.getTipoSalida(), salida.getRutaArchivo(), salida.getImpresora(), salida.getCopias());                        
                        }

                    }                
                }

            }else{
                JOptionPane.showMessageDialog(this, "Seleccione el remito a imprimir", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jMenuReimprime = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLblTurno = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaRemito = new javax.swing.JTable();
        jScrollSeleccion = new javax.swing.JScrollPane();
        rdTable = new ar.com.bosoft.RenderTablas.RXTable();
        rdTable.setSelectAllForEdit(true);
        jLabel2 = new javax.swing.JLabel();
        jLblFechaCx = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLblTipoPrestacion = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLblTecnico = new javax.swing.JLabel();
        jBtnSalir = new javax.swing.JButton();
        jBtnConsumido = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLblEntidad = new javax.swing.JLabel();
        jLblLugarCx = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLblPaciente = new javax.swing.JLabel();
        jLblProfesional = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLblFechaConsumido = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLblCajas = new javax.swing.JLabel();

        jMenuReimprime.setText("Reimprime remito");
        jMenuReimprime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuReimprimeActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuReimprime);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Turno");

        jLblTurno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTurno.setText("99999");

        jTablaRemito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Número", "id_remito", "cajas", "fecha_consumido", "observaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaRemito.setComponentPopupMenu(jPopupMenu);
        jTablaRemito.getTableHeader().setReorderingAllowed(false);
        jTablaRemito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaRemitoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaRemito);
        if (jTablaRemito.getColumnModel().getColumnCount() > 0) {
            jTablaRemito.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaRemito.getColumnModel().getColumn(0).setMaxWidth(150);
            jTablaRemito.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaRemito.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaRemito.getColumnModel().getColumn(2).setMaxWidth(0);
            jTablaRemito.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaRemito.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaRemito.getColumnModel().getColumn(3).setMaxWidth(0);
            jTablaRemito.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaRemito.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaRemito.getColumnModel().getColumn(4).setMaxWidth(0);
            jTablaRemito.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaRemito.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaRemito.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        rdTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codigo", "Nombre", "PM", "tipo", "Lote", "serie", "vencimiento", "cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rdTable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jScrollSeleccion.setViewportView(rdTable);
        if (rdTable.getColumnModel().getColumnCount() > 0) {
            rdTable.getColumnModel().getColumn(0).setPreferredWidth(75);
            rdTable.getColumnModel().getColumn(0).setMaxWidth(150);
            rdTable.getColumnModel().getColumn(2).setPreferredWidth(55);
            rdTable.getColumnModel().getColumn(2).setMaxWidth(100);
            rdTable.getColumnModel().getColumn(4).setPreferredWidth(100);
            rdTable.getColumnModel().getColumn(4).setMaxWidth(200);
            rdTable.getColumnModel().getColumn(5).setMinWidth(100);
            rdTable.getColumnModel().getColumn(5).setPreferredWidth(100);
            rdTable.getColumnModel().getColumn(5).setMaxWidth(100);
            rdTable.getColumnModel().getColumn(6).setPreferredWidth(75);
            rdTable.getColumnModel().getColumn(6).setMaxWidth(100);
            rdTable.getColumnModel().getColumn(7).setPreferredWidth(50);
            rdTable.getColumnModel().getColumn(7).setMaxWidth(75);
        }

        jLabel2.setText("Fecha Cx.");

        jLblFechaCx.setText("dd/MM/yyyy");

        jLabel3.setText("Tipo de prestación");

        jLblTipoPrestacion.setText("tipoPrestacion");

        jLabel4.setText("Técnico");

        jLblTecnico.setText("tecnico");

        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/salir.png"))); // NOI18N
        jBtnSalir.setText("Salir");
        jBtnSalir.setBorderPainted(false);
        jBtnSalir.setContentAreaFilled(false);
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnSalir.setFocusPainted(false);
        jBtnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBtnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        jBtnConsumido.setText("Imprimir consumido");
        jBtnConsumido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnConsumidoActionPerformed(evt);
            }
        });

        jLabel5.setText("Entidad");

        jLabel6.setText("Lugar Cx.");

        jLblEntidad.setText("entidad");

        jLblLugarCx.setText("lugarCx");

        jLabel7.setText("Paciente");

        jLabel8.setText("Profesional");

        jLblPaciente.setText("paciente");

        jLblProfesional.setText("profesional");

        jLabel9.setText("Fecha de carga de consumido");

        jLabel10.setText("Cajas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollSeleccion))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLblEntidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblLugarCx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblProfesional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLblTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLblFechaCx, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLblTipoPrestacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLblTecnico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(0, 148, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLblFechaConsumido, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jLblCajas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnConsumido)
                        .addGap(185, 185, 185)
                        .addComponent(jBtnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLblTurno)
                            .addComponent(jLabel2)
                            .addComponent(jLblFechaCx))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLblEntidad))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLblLugarCx))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLblPaciente))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLblProfesional))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLblTipoPrestacion))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLblTecnico))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblFechaConsumido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblCajas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSalir)
                    .addComponent(jBtnConsumido))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablaRemitoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaRemitoMouseClicked
        if (evt.getClickCount() == 1) {
            
            LoadingDialog loadingDialog = new LoadingDialog();

            SwingWorker sw = new ActionWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    verDetalle();
                    return null;
                }

                @Override
                public void done(){
                    loadingDialog.finish();
                }
            };
            sw.execute();
            loadingDialog.getDialog().setVisible(true);
        }
    }//GEN-LAST:event_jTablaRemitoMouseClicked

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnConsumidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConsumidoActionPerformed
        this.salida.setVisible(true);
        if (this.salida.getSiNo()) {
            this.imprime();
        }
    }//GEN-LAST:event_jBtnConsumidoActionPerformed

    private void jMenuReimprimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuReimprimeActionPerformed
        this.salida.setVisible(true);
        if (this.salida.getSiNo()) {
            this.imprimeRemito();
        }
    }//GEN-LAST:event_jMenuReimprimeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnConsumido;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblCajas;
    private javax.swing.JLabel jLblEntidad;
    private javax.swing.JLabel jLblFechaConsumido;
    private javax.swing.JLabel jLblFechaCx;
    private javax.swing.JLabel jLblLugarCx;
    private javax.swing.JLabel jLblPaciente;
    private javax.swing.JLabel jLblProfesional;
    private javax.swing.JLabel jLblTecnico;
    private javax.swing.JLabel jLblTipoPrestacion;
    private javax.swing.JLabel jLblTurno;
    private javax.swing.JMenuItem jMenuReimprime;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollSeleccion;
    private javax.swing.JTable jTablaRemito;
    private ar.com.bosoft.RenderTablas.RXTable rdTable;
    // End of variables declaration//GEN-END:variables
}
