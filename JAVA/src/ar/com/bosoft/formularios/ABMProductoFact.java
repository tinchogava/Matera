/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EspecialidadCRUD;
import ar.com.bosoft.crud.ForecastGrupoCRUD;
import ar.com.bosoft.crud.OpcionCRUD;
import ar.com.bosoft.crud.ProductoFactCRUD;
import ar.com.bosoft.crud.SubespecialidadCRUD;
import ar.com.bosoft.data.EspecialidadData;
import ar.com.bosoft.data.ForecastGrupoData;
import ar.com.bosoft.data.OpcionData;
import ar.com.bosoft.data.ProductoFactData;
import ar.com.bosoft.data.SubespecialidadData;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ABMProductoFact extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    OpcionCRUD opcionCRUD;
    EspecialidadCRUD especialidadCRUD;
    SubespecialidadCRUD subespecialidadCRUD;
    ForecastGrupoCRUD forecastGrupoCRUD;
    ProductoFactCRUD productoFactCRUD;
    ArrayList arrayOpcion, arrayEspecialidad, arraySubespecialidad, arrayForecastGrupo, arrayProductoFact, arrayId_subespecialidad;
    
    String empresa;
    
    public ABMProductoFact(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.opcionCRUD = new OpcionCRUD(conexion, empresa);
        this.especialidadCRUD = new EspecialidadCRUD(conexion, empresa);
        this.subespecialidadCRUD = new SubespecialidadCRUD(conexion, empresa);
        this.forecastGrupoCRUD = new ForecastGrupoCRUD(conexion, 1, empresa);
        this.productoFactCRUD = new ProductoFactCRUD(conexion, empresa);
        
        this.arrayEspecialidad = especialidadCRUD.consulta(true);
        this.arraySubespecialidad = subespecialidadCRUD.consulta(0, true);
        this.arrayForecastGrupo = forecastGrupoCRUD.consulta(true);
        
        this.arrayId_subespecialidad = new ArrayList();
        
        initComponents();
        
        this.modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        jTabla.setRowSorter (new TableRowSorter(modelo));
        sorter = new TableRowSorter(modelo);
        llenaComboHabilita();
        llenaComboEspecialidad();
        llenaComboForecastGrupo();
        limpia();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblId_productoFact = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jTxtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jBtnGuardar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jComboHabilita = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jComboEspecialidad = new javax.swing.JComboBox();
        jComboSubespecialidad = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jTxtCodigo = new javax.swing.JTextField();
        jTxtCodigo.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(10,true));
        jLabel3 = new javax.swing.JLabel();
        jComboForecastGrupo = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTxtPrecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtDescripcion = new javax.swing.JTextArea();

        setTitle("ABM Producto para facturar");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLblId_productoFact.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_productoFact.setText("---");

        jLabel2.setText("* Nombre");

        jTxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtNombreFocusGained(evt);
            }
        });

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("(*)Datos Obligatorios");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel6.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Hab.", "id_productoFact", "id_especialidad", "id_subespecialidad", "Descripcion", "Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class
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
        jTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setMinWidth(50);
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(2).setMinWidth(50);
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(3).setMinWidth(0);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(4).setMinWidth(0);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(5).setMinWidth(0);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(6).setMinWidth(0);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(7).setMinWidth(0);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        jLabel5.setText("* Habilitado");

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salida"));

        jBtnScr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pantalla.png"))); // NOI18N
        jBtnScr.setBorder(null);
        jBtnScr.setBorderPainted(false);
        jBtnScr.setContentAreaFilled(false);
        jBtnScr.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnScr.setFocusPainted(false);
        jBtnScr.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pantalla.png"))); // NOI18N
        jBtnScr.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pantalla.png"))); // NOI18N
        jBtnScr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnScrActionPerformed(evt);
            }
        });

        jBtnImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/impresora.png"))); // NOI18N
        jBtnImp.setBorder(null);
        jBtnImp.setBorderPainted(false);
        jBtnImp.setContentAreaFilled(false);
        jBtnImp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnImp.setFocusPainted(false);
        jBtnImp.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/impresora.png"))); // NOI18N
        jBtnImp.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/impresora.png"))); // NOI18N
        jBtnImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnImpActionPerformed(evt);
            }
        });

        jBtnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pdf.png"))); // NOI18N
        jBtnPdf.setBorder(null);
        jBtnPdf.setBorderPainted(false);
        jBtnPdf.setContentAreaFilled(false);
        jBtnPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnPdf.setFocusPainted(false);
        jBtnPdf.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pdf.png"))); // NOI18N
        jBtnPdf.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pdf.png"))); // NOI18N
        jBtnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPdfActionPerformed(evt);
            }
        });

        jBtnXls.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/xls.png"))); // NOI18N
        jBtnXls.setBorder(null);
        jBtnXls.setBorderPainted(false);
        jBtnXls.setContentAreaFilled(false);
        jBtnXls.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnXls.setFocusPainted(false);
        jBtnXls.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/xls.png"))); // NOI18N
        jBtnXls.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/xls.png"))); // NOI18N
        jBtnXls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnXlsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jBtnScr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnImp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnXls, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBtnScr)
            .addComponent(jBtnImp)
            .addComponent(jBtnPdf)
            .addComponent(jBtnXls)
        );

        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/salir.png"))); // NOI18N
        jBtnSalir.setText("Salir");
        jBtnSalir.setBorderPainted(false);
        jBtnSalir.setContentAreaFilled(false);
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnSalir.setFocusPainted(false);
        jBtnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBtnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        jLabel8.setText("Especialidad");

        jComboEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEspecialidadActionPerformed(evt);
            }
        });
        jComboEspecialidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboEspecialidadKeyReleased(evt);
            }
        });

        jComboSubespecialidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboSubespecialidadKeyReleased(evt);
            }
        });

        jLabel9.setText("Subespecialidad");

        jTxtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtCodigoFocusGained(evt);
            }
        });

        jLabel3.setText("* Código");

        jComboForecastGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboForecastGrupoKeyReleased(evt);
            }
        });

        jLabel10.setText("Grupo FORECAST");

        jLabel1.setText("Precio");

        jTxtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtPrecioActionPerformed(evt);
            }
        });

        jLabel4.setText("Descripción");

        jTxtDescripcion.setColumns(20);
        jTxtDescripcion.setRows(5);
        jScrollPane1.setViewportView(jTxtDescripcion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnGuardar))
                            .addComponent(jLblId_productoFact)
                            .addComponent(jTxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboEspecialidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboSubespecialidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboForecastGrupo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTxtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(81, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnSalir)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLblId_productoFact)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboSubespecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboForecastGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnGuardar)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void llenaComboHabilita(){
        arrayOpcion = opcionCRUD.consulta(2);
        Iterator i = arrayOpcion.iterator();
        while (i.hasNext()){
            OpcionData tmp = (OpcionData) i.next();
            jComboHabilita.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboEspecialidad(){
        Iterator i = arrayEspecialidad.iterator();
        while (i.hasNext()){
            EspecialidadData tmp = (EspecialidadData) i.next();
            this.jComboEspecialidad.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboSubespecialidad(int id_especialidad){
        this.arrayId_subespecialidad.clear();
        this.jComboSubespecialidad.removeAllItems();
        if (id_especialidad != 0) {
            Iterator i = arraySubespecialidad.iterator();
            while (i.hasNext()){
                SubespecialidadData item = (SubespecialidadData)i.next();
                if (item.getId_especialidad() == id_especialidad){
                    this.jComboSubespecialidad.addItem(item.getNombre());
                    this.arrayId_subespecialidad.add(item.getId_subespecialidad());
                }
            }
        }
        this.jComboSubespecialidad.setSelectedIndex(-1);
    }
    
    private void llenaComboForecastGrupo(){
        Iterator i = arrayForecastGrupo.iterator();
        while (i.hasNext()){
            ForecastGrupoData tmp = (ForecastGrupoData) i.next();
            this.jComboForecastGrupo.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jLblId_productoFact.setText("---");
        this.jTxtCodigo.setText("");
        this.jTxtNombre.setText("");
        this.jComboEspecialidad.setSelectedIndex(-1);
        this.jComboSubespecialidad.setSelectedIndex(-1);
        this.jComboForecastGrupo.setSelectedIndex(-1);
        this.jComboHabilita.setSelectedItem("SI");
        this.jTxtBusca.setText("");
    }
    
    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        arrayProductoFact = productoFactCRUD.consulta(false, 1);
        Iterator i = arrayProductoFact.iterator();
        while (i.hasNext()){
            ProductoFactData tmp = (ProductoFactData) i.next();
            int id_productoFact = tmp.getId_productoFact();
            String nombre = tmp.getNombre();
            int id_especialidad = tmp.getId_especialidad();
            int id_subespecialidad = tmp.getId_subespecialidad();
            String habilitado = tmp.getHabilita().equals("S") ? "SI" : "NO";
            String desc = tmp.getDescripcion();
            Double prec = tmp.getPrecio();
                    
            Object [] nuevo = {id_productoFact, nombre, habilitado, id_especialidad, id_subespecialidad, desc, prec};
            modelo.addRow(nuevo);
        }
    }
    
    private void setJTexFieldChanged(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printIt(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaChanged();
        }
    }
 
    private void JTxtBuscaChanged(){
        String text = jTxtBusca.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTabla.setRowSorter(sorter);
        }
    }
    
    private boolean validaObligatorios(){
        return !this.jTxtCodigo.getText().isEmpty() && 
                !this.jTxtNombre.getText().isEmpty();
    }
    
    private void jTxtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNombreFocusGained
        this.jTxtNombre.select(0, this.jTxtNombre.getText().length());
    }//GEN-LAST:event_jTxtNombreFocusGained

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            ProductoFactData nuevo = new ProductoFactData();
            int id_productoFact = 0;
            if (!this.jLblId_productoFact.getText().equals("---")) {
                id_productoFact = Integer.parseInt(this.jLblId_productoFact.getText());
            }
            nuevo.setId_productoFact(id_productoFact);
            
            nuevo.setCodigo(this.jTxtCodigo.getText());
            nuevo.setNombre(this.jTxtNombre.getText());
            nuevo.setDescripcion(this.jTxtDescripcion.getText());
            nuevo.setPrecio(Double.valueOf(this.jTxtPrecio.getText()));
            
            int id_especialidad = 0;
            if (this.jComboEspecialidad.getSelectedIndex() >= 0) {
                EspecialidadData e = (EspecialidadData) arrayEspecialidad.get(this.jComboEspecialidad.getSelectedIndex());
                id_especialidad = e.getId_especialidad();
            }
            nuevo.setId_especialidad(id_especialidad);
            
            int id_subespecialidad = 0;
            if (this.jComboSubespecialidad.getSelectedIndex() >= 0) {
                id_subespecialidad = (int) arrayId_subespecialidad.get(this.jComboSubespecialidad.getSelectedIndex());
            }            
            nuevo.setId_subespecialidad(id_subespecialidad);
            
            int id_forecastGrupo = 0;
            if (this.jComboForecastGrupo.getSelectedIndex() >= 0) {
                ForecastGrupoData f = (ForecastGrupoData) arrayForecastGrupo.get(this.jComboForecastGrupo.getSelectedIndex());
                id_forecastGrupo = f.getId_forecastGrupo();
            }
            nuevo.setId_forecastGrupo(id_forecastGrupo);
            
            nuevo.setHabilita(this.jComboHabilita.getSelectedItem().toString().substring(0, 1));
            
            productoFactCRUD.insert(nuevo);
            
            this.limpia();
            this.consulta();
            this.jTxtCodigo.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        //        if (producto()){
            //            imprimir(0);
            //        }
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        //        if(producto()){
            //            seleccionImp.setVisible(true);
            //            if (seleccionImp.getSino()){
                //                this.impresora = seleccionImp.getImpresora();
                //                imprimir(1);
                //            }
            //        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jBtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPdfActionPerformed
        //        if (producto()){
            //            //Crear un objeto FileChooser
            //            JFileChooser fc = new JFileChooser();
            //            //Mostrar la ventana para abrir archivo y recoger la respuesta
            //            int respuesta = fc.showSaveDialog(null);
            //            //Comprobar si se ha pulsado Aceptar
            //            if (respuesta == JFileChooser.APPROVE_OPTION){
                //                //Crear un objeto File con el archivo elegido
                //                File archivoElegido = fc.getSelectedFile();
                //                try {
                    //                    this.rutaArchivo = archivoElegido.getCanonicalPath() + ".pdf";
                    //                    imprimir(2);
                    //                } catch (IOException ex) {
                    //                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                    //                }
                //            }
            //        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

    private void jBtnXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXlsActionPerformed
        //        if (producto()){
            //            //Crear un objeto FileChooser
            //            JFileChooser fc = new JFileChooser();
            //            //Mostrar la ventana para abrir archivo y recoger la respuesta
            //            int respuesta = fc.showSaveDialog(null);
            //            //Comprobar si se ha pulsado Aceptar
            //            if (respuesta == JFileChooser.APPROVE_OPTION){
                //                //Crear un objeto File con el archivo elegido
                //                File archivoElegido = fc.getSelectedFile();
                //                try {
                    //                    this.rutaArchivo = archivoElegido.getCanonicalPath() + ".xlsx";
                    //                    imprimir(3);
                    //                } catch (IOException ex) {
                    //                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                    //                }
                //            }
            //        }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        int indice = jTabla.convertRowIndexToModel (jTabla.getSelectedRow());
        ProductoFactData tmp = (ProductoFactData) arrayProductoFact.get(indice);
        this.jLblId_productoFact.setText(String.valueOf(tmp.getId_productoFact()));
        this.jTxtCodigo.setText(tmp.getCodigo());
        this.jTxtNombre.setText(tmp.getNombre());
        this.jTxtDescripcion.setText(tmp.getDescripcion());
        this.jTxtPrecio.setText(tmp.getPrecio().toString());
        
        this.jComboEspecialidad.setSelectedIndex(-1);
        this.jComboEspecialidad.setSelectedItem(tmp.getEspecialidad());
        
        this.llenaComboSubespecialidad(tmp.getId_especialidad());
        this.jComboSubespecialidad.setSelectedIndex(-1);
        this.jComboSubespecialidad.setSelectedItem(tmp.getSubespecialidad());
        
        this.jComboForecastGrupo.setSelectedIndex(-1);
        this.jComboForecastGrupo.setSelectedItem(tmp.getForecastGrupo());
        
        this.jComboHabilita.setSelectedItem(tmp.getHabilita().equals("S") ? "SI" : "NO");
        
        this.jTxtNombre.requestFocus();
    }//GEN-LAST:event_jTablaMouseClicked

    private void jTxtCodigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCodigoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtCodigoFocusGained

    private void jComboEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEspecialidadActionPerformed
        int id_especialidad = 0;
        if (this.jComboEspecialidad.getSelectedIndex() >= 0) {
            EspecialidadData tmp = (EspecialidadData) arrayEspecialidad.get(this.jComboEspecialidad.getSelectedIndex());
            id_especialidad = tmp.getId_especialidad();
        }
        llenaComboSubespecialidad(id_especialidad);
    }//GEN-LAST:event_jComboEspecialidadActionPerformed

    private void jComboEspecialidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboEspecialidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboEspecialidad.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboEspecialidadKeyReleased

    private void jComboSubespecialidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboSubespecialidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboSubespecialidad.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboSubespecialidadKeyReleased

    private void jComboForecastGrupoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboForecastGrupoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboForecastGrupo.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboForecastGrupoKeyReleased

    private void jTxtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPrecioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboEspecialidad;
    private javax.swing.JComboBox jComboForecastGrupo;
    private javax.swing.JComboBox jComboHabilita;
    private javax.swing.JComboBox jComboSubespecialidad;
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
    private javax.swing.JLabel jLblId_productoFact;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtCodigo;
    private javax.swing.JTextArea jTxtDescripcion;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtPrecio;
    // End of variables declaration//GEN-END:variables
}
