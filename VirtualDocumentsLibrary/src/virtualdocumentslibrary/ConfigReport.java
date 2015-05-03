/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualdocumentslibrary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Gio
 */
public class ConfigReport extends javax.swing.JFrame {

    /**
     * Creates new form ConfigReport
     */
    private String nameChecks[];
    private String fondoChecks[];
    private boolean selectedChecks[];
    
    private String [] camposNombres;
    private Integer [] camposTamanio;
    private String consultaInicio;
    
     
    private Connection connection = DataBaseClass.getConnection();
    
    private ResultSet resultSet = DataBaseClass.getResultSet();
   // private int codeToChange = -1;
    private Statement st = DataBaseClass.getSt();
   // private String preparedQuery;
    
    
    // Variables relacionadas al ordern 
    private boolean order = false;
    private String nombreToOrder="";
    private boolean orderIn = false;
    private int indexOrder = 0; // Empieza en 0 
    private int orderRow = -1;
    
    // Fin varibles ordern
    // Variables relacionadas al desorden
    private boolean disorder = false;
    private String nombreToDisorder="";
    private boolean disorderIn = false;
    private int disorderRow = -1;
    private int indexDisorder =45;
    // Fin desorden
    
    
    private boolean checksCampos[] = null; // Vector donde guardare los checks de campos
    
    public ConfigReport() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png"))); 
        this.setTitle("Virtual Archive");
        labelPanel.setBackground(ConfigClass.getColorApp());
        
        reloadTreeInit();
        nameChecks = new String[fondosR.getRowCount()];
        fondoChecks = new String[fondosR.getRowCount()];
        selectedChecks = new boolean[fondosR.getRowCount()];
        putNamesIntoArrays();
        reloadTree(fondosR);
        
        
        // Intancio todas las tablas del reporte 
        initCamposTable(); // la tabla del los nombres de los campos de la tabla. 
        initDatesTables();
        
    }

    /*
    En este metodo configuro la tabla de las fechas para poder ser usadas
    */
    private void initDatesTables(){
        dateBeforeTable.getColumnModel().getColumn(0).setPreferredWidth(220);
        dateAfterTable.getColumnModel().getColumn(0).setPreferredWidth(220); // Tamanio de la columan primera
        setFilterDate();
    }
    /*
    Para los dias aunque sea verificare que los dias esten entre 0 y 31 dias 
    meses en 0 y 12 y anios que tengan cifras de 4 
    */
    private void setFilterDate(){
        dateBeforeTable.getColumnModel().getColumn(1).setCellEditor(new FilterDaysNumbers(0, 31)); // Dia 
        dateBeforeTable.getColumnModel().getColumn(2).setCellEditor(new FilterDaysNumbers(0, 12)); // mes
        dateBeforeTable.getColumnModel().getColumn(3).setCellEditor(new FilterDaysNumbers(1000,9999)); // anio 
        
        // Otra tabla
        dateAfterTable.getColumnModel().getColumn(1).setCellEditor(new FilterDaysNumbers(0, 31)); // Dia 
        dateAfterTable.getColumnModel().getColumn(2).setCellEditor(new FilterDaysNumbers(0, 12)); // mes
        dateAfterTable.getColumnModel().getColumn(3).setCellEditor(new FilterDaysNumbers(1000,9999)); // anio 
        
        
        // Colocare cero a todos los datos 
        for (int j = 0;j<8;j++){
            dateBeforeTable.getModel().setValueAt(0, j, 1);
            dateBeforeTable.getModel().setValueAt(0, j, 2);
            dateBeforeTable.getModel().setValueAt(0, j, 3);
            // De una la otra tabla
            dateAfterTable.getModel().setValueAt(0, j, 1);
            dateAfterTable.getModel().setValueAt(0, j, 2);
            dateAfterTable.getModel().setValueAt(0, j, 3); 
            
        }
    }
    
    
    
    /*
    En este momento habran varios metodos que seran utilices para cada tabla, 
    la primera tabla sera entre los campos. 
    
    */
    private void initCamposTable(){
        camposTable.setModel(new ResultSetTableModel("tabla campo"));
        camposTable.setRowHeight(30);  // Esteticamente se mira mejor
        setColumnWidthCampos();
        setCheckBoxTableCampos();
        
    }
    private void setColumnWidthCampos(){
        
        camposTable.getColumnModel().getColumn(0).setPreferredWidth(172);
        
        camposTable.getColumnModel().getColumn(1).setPreferredWidth(32);
        camposTable.getColumnModel().getColumn(2).setPreferredWidth(27);
    }
   
    private void setCheckBoxTableCampos (){
        
        int filas = camposTable.getModel().getRowCount(); // cantidad de filas 

        for (int fila = 0; fila < filas ;fila ++){ 
             
                    camposTable.getModel().setValueAt(false, fila, 2);   // Siempre empezara con todos los campos apagados. 
                    
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        camposTable = new javax.swing.JTable();
        checkBoxCampos = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fondosR = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        checkBox1 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        dateBeforeTable = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        dateAfterTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        orderBeforeTable = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        orderAfterTable = new javax.swing.JTable();
        labelPanel = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        camposTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        camposTable.setModel(new ResultSetTableModel("permiso modulos"));
        camposTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(camposTable);

        checkBoxCampos.setText("Seleccionar Todos / Deseleccionar Todos");
        checkBoxCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxCamposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(132, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkBoxCampos)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(checkBoxCampos)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Con los campos", jPanel3);

        fondosR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fondosRMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(fondosR);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("En las series", jPanel1);

        usersTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        usersTable.setModel(new ResultSetTableModel("reporte usuarios"));
        usersTable.setRowHeight(30);
        usersTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(usersTable);

        checkBox1.setText("Seleccionar Todos / Deseleccionar Todos");
        checkBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkBox1)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(114, 114, 114))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkBox1)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Por los usuarios", jPanel2);

        dateBeforeTable.setModel(new DefaultTableModel(
            new Object [][] {
                {"Fecha_Documento", null, null, null},
                {"Fecha_Extrema_Inicial", null, null, null},
                {"Fecha_Extrema_Final", null, null, null},
                {"Fecha_Extrema_Caja_Inicial", null, null, null},
                {"Fecha_Extrema_Caja_Final", null, null, null},
                {"Fecha_Extrema_Legajo_Inicial", null, null, null},
                {"Fecha_Extrema_Legajo_Final", null, null, null},
                {"Otra_Fecha", null, null, null}
            },
            new String [] {
                "Fecha", "Día", "Mes", "Año"
            }

        )// Cosas Extras de mi tabla
        {
            @Override
            public boolean isCellEditable (int row, int col){
                if (col!=0)
                return true;
                else
                return false;
            }

            @Override
            public Class getColumnClass (int col){
                if (col!=0)
                return Integer.class;
                else
                return String.class;
            }
        }

    );
    dateBeforeTable.setRowHeight(30);
    dateBeforeTable.getTableHeader().setReorderingAllowed(false);
    jScrollPane7.setViewportView(dateBeforeTable);

    dateAfterTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {"Fecha_Documento", null, null, null},
            {"Fecha_Extrema_Inicial", null, null, null},
            {"Fecha_Extrema_Final", null, null, null},
            {"Fecha_Extrema_Caja_Inicial", null, null, null},
            {"Fecha_Extrema_Caja_Final", null, null, null},
            {"Fecha_Extrema_Legajo_Inicial", null, null, null},
            {"Fecha_Extrema_Legajo_Final", null, null, null},
            {"Otra_Fecha", null, null, null}
        },
        new String [] {
            "Fecha", "Día", "Mes", "Año"
        }
    )// Cosas Extras de mi tabla
    {
        @Override
        public boolean isCellEditable (int row, int col){
            if (col!=0)
            return true;
            else
            return false;
        }

        @Override
        public Class getColumnClass (int col){
            if (col!=0)
            return Integer.class;
            else
            return String.class;
        }
    }

    );
    dateAfterTable.setRowHeight(30);
    dateAfterTable.getTableHeader().setReorderingAllowed(false);
    jScrollPane8.setViewportView(dateAfterTable);

    jLabel1.setText("Entre");

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(37, 37, 37)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(21, 21, 21))
    );

    jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane7, jScrollPane8});

    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addGap(152, 152, 152)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane7, jScrollPane8});

    jTabbedPane1.addTab("Entre las fechas", jPanel4);

    jLabel2.setText("en Orden");

    orderBeforeTable.setModel(new DefaultTableModel(
        new Object [][] {
            {"idDocumento" },
            {"Path" },
            {"Nombre_Archivo" },
            {"Fecha_Hora_Escaneo" },
            {"Usuario_Escaneo" },
            {"Numero_Folios" },
            {"Tipo_documental.nombre" },
            {"Fecha_Documento_dia" },
            {"Fecha_Documento_mes" },
            {"Fecha_Documento_anio" },
            {"Fecha_Extrema_dia_inicial" },
            {"Fecha_Extrema_mes_inicial" },
            {"Fecha_Extrema_anio_inicial" },
            {"Fecha_Extrema_dia_final" },
            {"Fecha_Extrema_mes_final" },
            {"Fecha_Extrema_anio_final" },
            {"Asunto" },
            {"Campos_Especificos" },
            {"Observaciones" },
            {"Local.nombre" },
            {"Area.nombre" },
            {"Ambiente.nombre" },
            {"Estanteria.nombre" },
            {"Caja" },
            {"Fecha_Extrema_Caja_dia_inicial" },
            {"Fecha_Extrema_Caja_mes_inicial" },
            {"Fecha_Extrema_Caja_anio_inicial" },
            {"Fecha_Extrema_Caja_dia_final" },
            {"Fecha_Extrema_Caja_mes_final" },
            {"Fecha_Extrema_Caja_anio_final" },
            {"Legajo" },
            {"Fecha_Extrema_Legajo_dia_inicial" },
            {"Fecha_Extrema_Legajo_mes_inicial" },
            {"Fecha_Extrema_Legajo_anio_inicial" },
            {"Fecha_Extrema_Legajo_dia_final" },
            {"Fecha_Extrema_Legajo_mes_final" },
            {"Fecha_Extrema_Legajo_anio_final" },
            {"Orden_Alfabetico" },
            {"Orden_Correlativo" },
            {"Otra_Fecha_dia" },
            {"Otra_Fecha_mes" },
            {"Otra_Fecha_anio" },
            {"Nuevo_numero_unico" },
            {"Nuevo_numero_unico_2" },
            {"Codigo_Serie" }

        },
        new String [] {
            "Nombre Campo"
        }

    )// Cosas Extras de mi tabla
    {
        @Override
        public boolean isCellEditable (int row, int col){
            if (col!=0)
            return true;
            else
            return false;
        }

        @Override
        public Class getColumnClass (int col){
            if (col!=0)
            return Integer.class;
            else
            return String.class;
        }
    }

    );
    orderBeforeTable.setRowHeight(30);
    orderBeforeTable.getTableHeader().setReorderingAllowed(false);
    orderBeforeTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            orderBeforeTableMouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            orderBeforeTableMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            orderBeforeTableMousePressed(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            orderBeforeTableMouseReleased(evt);
        }
    });
    orderBeforeTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseDragged(java.awt.event.MouseEvent evt) {
            orderBeforeTableMouseDragged(evt);
        }
    });
    jScrollPane9.setViewportView(orderBeforeTable);

    orderAfterTable.setModel(new DefaultTableModel(
        new Object [][] {
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null},
            {null,null}

        },
        new String [] {
            "Nombre Campo","ASC/DESC"
        }

    )// Cosas Extras de mi tabla
    {
        @Override
        public boolean isCellEditable (int row, int col){
            if (col!=0)
            return true;
            else
            return false;
        }

        @Override
        public Class getColumnClass (int col){
            if (col!=0)
            return Boolean.class;
            else
            return String.class;
        }
    }

    );
    orderAfterTable.setRowHeight(30);
    orderAfterTable.getTableHeader().setReorderingAllowed(false);
    orderAfterTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            orderAfterTableMouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            orderAfterTableMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            orderAfterTableMousePressed(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            orderAfterTableMouseReleased(evt);
        }
    });
    orderAfterTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseDragged(java.awt.event.MouseEvent evt) {
            orderAfterTableMouseDragged(evt);
        }
    });
    jScrollPane10.setViewportView(orderAfterTable);

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(33, 33, 33)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("En el orden", jPanel5);

    labelPanel.setBackground(new java.awt.Color(255, 102, 0));

    labelName.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
    labelName.setText("Configurar Reporte");

    javax.swing.GroupLayout labelPanelLayout = new javax.swing.GroupLayout(labelPanel);
    labelPanel.setLayout(labelPanelLayout);
    labelPanelLayout.setHorizontalGroup(
        labelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(labelPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(labelName, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    labelPanelLayout.setVerticalGroup(
        labelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(labelPanelLayout.createSequentialGroup()
            .addGap(23, 23, 23)
            .addComponent(labelName)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aceptar.png"))); // NOI18N
    saveButton.setText("Aceptar");
    saveButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            saveButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(labelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(labelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(11, 11, 11)
            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        setNames();
        
        String endConsulta = "";
        
        endConsulta += " FROM documento ";
            // Left Join hara que dara importancia a la tabla documento. 
        endConsulta += "INNER JOIN usuario ON usuario.idUsuario = documento.usuario_escaneo\n" +
                        "INNER JOIN tipo_documental ON tipo_documental.idTipo_Documental = documento.Codigo_tipo_documental\n" +
                        "LEFT JOIN local ON local.idLocal = documento.Codigo_Local\n" +
                        "LEFT JOIN area ON area.idArea = documento.Codigo_Area\n" +
                        "LEFT JOIN ambiente ON ambiente.idAmbiente = documento.Codigo_Ambiente\n" +
                        "LEFT JOIN estanteria ON estanteria.idEstanteria = documento.Codigo_Estanteria";
        
        endConsulta +=" WHERE idDocumento IS NOT NULL "; // Siempre llevare where ya que las fechas siempre verificare
        if (!"".equals(this.betweenDates()))
            endConsulta +=" AND ("+this.betweenDates()+")";
        if (!"".equals(this.getCheckedSeries()))
            endConsulta +=" AND ("+this.getCheckedSeries()+")";
        if (!"".equals(this.byUsers()))
            endConsulta +=" AND ("+this.byUsers()+")";
        if (!"".equals(this.orderBy()))
            endConsulta += " ORDER BY "+this.orderBy();
        
       
       
        // Configurare la tabla para que se vea. lo que acabamos de hacer. 
        MasterMainView.getReporteTable().setModel(new ResultSetTableModel("reporte",consultaInicio,camposNombres,endConsulta));
        setColumnWidth();
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed
    /*
    Este Metodo es para darle tamanio a la tabla. 
    */
    
    private  void setColumnWidth(){
        MasterMainView.getReporteTable().setAutoResizeMode(0);
            for (int j=0;j<MasterMainView.getReporteTable().getColumnCount();j++){
                MasterMainView.getReporteTable().getColumnModel().getColumn(j).setPreferredWidth(camposTamanio[j]);
            }
        
    }
    /*
    Lo que se hara en este metodo es ver que datos de la tabla documento 
    quiere el usuario mostrar, puede ser que no sean todos. 
    */
    private void setNames(){
        ArrayList<String> camposLista = new ArrayList<String>();
        ArrayList<Integer> listaTamanio = new ArrayList<Integer>();
        
        consultaInicio = "SELECT idDocumento, Nombre_archivo, Fecha_hora_escaneo, usuario.email, numero_folios,";
        camposLista.add("<html><center><table><tr><td align=\"center\" > Correlativo </td></tr><tr><td align=\"center\"> Programa </td> </tr></table></center></html>");
        camposLista.add("CUI");
        camposLista.add("<html><center><table><tr><td align=\"center\" > Fecha y </td></tr><tr><td align=\"cente\"> Hora Escaneo </td> </tr></table></center></html>");
        camposLista.add("<html><center><table><tr><td align=\"center\" > Usuario que </td></tr><tr><td align=\"center\"> Escaneo </td> </tr></table></center></html>");
        camposLista.add("<html><center><table><tr><td align=\"center\" > Numero de </td></tr><tr><td align=\"center\"> Folios </td> </tr></table></center></html>");
                
        
        // Aqui agrego el tamanio de las tablas. 
        listaTamanio.add(83);
        listaTamanio.add(85);
        listaTamanio.add(144);
        listaTamanio.add(199);
        listaTamanio.add(92);
        
        int filas = camposTable.getModel().getRowCount(); // cantidad de filas 
        boolean value = false;
        
        int acces=-1;

        for (int i = 0; i < filas ;i ++){ 
            try{    
                value = Boolean.valueOf(camposTable.getModel().getValueAt(i, 2).toString()); // obtengo el valor del check
            }catch (Exception e){
                value = false;
            }
                if (value)
                acces=1;
            else 
                acces=0;
            // Solo en caso que sea acceso 1 hare lo siguiente. 
            if (acces==1){
                String nombreColumna = camposTable.getModel().getValueAt(i, 0).toString();
                
                if (nombreColumna.equalsIgnoreCase("Fecha Documento")){
                    
                    consultaInicio+="CONCAT(fecha_documento_dia,'/',fecha_documento_mes,'/',fecha_documento_anio),";
                    camposLista.add("Fecha Documento");             
                }
                else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Inicial")){
                   consultaInicio+="CONCAT(Fecha_extrema_dia_Inicial,'/', fecha_extrema_mes_Inicial,'/', fecha_extrema_anio_Inicial),";
                   camposLista.add("<html>Fecha Extrema<br>Inical"); 
                }
                else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Final")){


                   consultaInicio+="CONCAT(Fecha_extrema_dia_Final,'/', fecha_extrema_mes_Final,'/', fecha_extrema_anio_Final),";
                   camposLista.add("<html>Fecha Extrema<br>Final "); 
                }
                else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Inicial")){

                    consultaInicio+="CONCAT(Fecha_extrema_caja_dia_Inicial,'/', fecha_extrema_caja_mes_Inicial,'/', fecha_extrema_caja_anio_Inicial),";
                    camposLista.add("<html>Fecha Extrema<br>Caja Inicial"); 
                }
                else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Final")){

                     consultaInicio+="CONCAT(Fecha_extrema_caja_dia_Final,'/', fecha_extrema_caja_mes_Final,'/', fecha_extrema_caja_anio_Final),";
                     camposLista.add("<html>Fecha Extrema<br>Cajar Final"); 
                }

                else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Inicial")){


                   consultaInicio+="CONCAT(Fecha_extrema_legajo_dia_Inicial,'/', fecha_extrema_legajo_mes_Inicial,'/', fecha_extrema_legajo_anio_Inicial),";
                   camposLista.add("<html>Fecha Extrema<br>Legajo Inicial"); 
                }
                else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Final")){


                   
                   consultaInicio+="CONCAT(Fecha_extrema_legajo_dia_Final,'/', fecha_extrema_legajo_mes_Final,'/', fecha_extrema_legajo_anio_Final),";
                   camposLista.add("<html>Fecha Extrema<br>Legajo Final"); 
                }
                else if (nombreColumna.equalsIgnoreCase("Otra Fecha")){


                  
                   consultaInicio+="CONCAT(Otra_fecha_dia,'/', Otra_fecha_mes,'/', Otra_fecha_anio),";
                   camposLista.add("Otra Fecha"); 

                }
                else if (nombreColumna.equalsIgnoreCase("codigo_tipo_documental")){
                   
                    consultaInicio+="tipo_documental.nombre,";
                    camposLista.add("Tipo Documental"); 
                }
                else if (nombreColumna.equalsIgnoreCase("codigo_local")){
                    
                    consultaInicio+="local.nombre,";
                    camposLista.add("Local"); 

                }
                else if (nombreColumna.equalsIgnoreCase("codigo_area")){
                    
                    consultaInicio+="area.nombre,";
                    camposLista.add("Area"); 

                }
                else if (nombreColumna.equalsIgnoreCase("codigo_ambiente")){
                    
                    consultaInicio+="ambiente.nombre,";
                    camposLista.add("Ambiente"); 

                }
                else if (nombreColumna.equalsIgnoreCase("codigo_estanteria")){
                    
                    consultaInicio+="estanteria.nombre,";
                    camposLista.add("Estantería"); 

                }
                
                
                else {
                    consultaInicio += camposTable.getModel().getValueAt(i, 0).toString()+",";
                    camposLista.add(String.valueOf(camposTable.getModel().getValueAt(i, 0).toString())); // Nombre
                }
                
                listaTamanio.add(Integer.valueOf(camposTable.getModel().getValueAt(i, 1).toString())); // Tamanio del campo. 
                
            }    
            // Transformando lista a arreglo 
            Object[] ObjectList = camposLista.toArray();
            camposNombres = Arrays.copyOf(ObjectList,ObjectList.length,String[].class);
            
            // Tranformar el tamanio 
            ObjectList = listaTamanio.toArray();
            camposTamanio = Arrays.copyOf(ObjectList,ObjectList.length,Integer[].class);
            
            
            

        } // Fin del for 
        
        // Esto de aca lo hare para que no me de error la consulta lo que hace que le quita el ultimo ,
        consultaInicio = consultaInicio.substring(0, consultaInicio.length()-1);
            
            
            
        
    }
    private void fondosRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fondosRMouseClicked
        
        String name = fondosR.getSelectionPath().getLastPathComponent().toString();
        Object directorios[] = fondosR.getSelectionPath().getPath();
        for(int i=0;i<nameChecks.length;i++){
                    
                    if(directorios.length>2){
                        
                        if(nameChecks[i].equals(name) && fondoChecks[i].equals(directorios[2].toString())){
                            if(selectedChecks[i]){
                                selectedChecks[i] = false;
                                
                            }
                            else{
                                selectedChecks[i] = true;
                              
                            }
                        }
                    }
                    else{
                        if(nameChecks[i].equals(name) && (fondoChecks[i].equals("null") || fondoChecks[i].equals(nameChecks[i]))){
                            if(selectedChecks[i]){
                                selectedChecks[i] = false;
                            }
                            else{
                                selectedChecks[i] = true;
                            }
                        }
                    }
                    

                }
        reloadTree(fondosR);
    }//GEN-LAST:event_fondosRMouseClicked

    private void checkBoxCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxCamposActionPerformed
        int filas = camposTable.getModel().getRowCount(); // cantidad de filas

        for (int fila = 0; fila < filas ;fila ++){
            if (checkBoxCampos.isSelected())
            // Esta True, entoces seleccionare todos los accesos.
            camposTable.getModel().setValueAt(true, fila, 2);
            else
            camposTable.getModel().setValueAt(false, fila, 2);

        }

    }//GEN-LAST:event_checkBoxCamposActionPerformed

    private void checkBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox1ActionPerformed
         int filas = usersTable.getModel().getRowCount(); // cantidad de filas

        for (int fila = 0; fila < filas ;fila ++){
            if (checkBox1.isSelected())
            // Esta True, entoces seleccionare todos los accesos.
            usersTable.getModel().setValueAt(true, fila, 3);
            else
            usersTable.getModel().setValueAt(false, fila, 3);

        }
    }//GEN-LAST:event_checkBox1ActionPerformed

    private void orderBeforeTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderBeforeTableMousePressed
            
            orderRow = orderBeforeTable.rowAtPoint(evt.getPoint());

            order=true; // Aqui con este booleano empieza todo. 

            // accion qee va hacer siempre
            if (orderRow > -1) {

                Object objeto ;
                try{
                    objeto = orderBeforeTable.getModel().getValueAt(orderRow, 0);
                    nombreToOrder = objeto.toString();
                }catch(Exception e){
                    nombreToOrder="";
                }

            }
        
    }//GEN-LAST:event_orderBeforeTableMousePressed

    private void orderBeforeTableMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderBeforeTableMouseDragged
         if(order == true && orderIn == false && !"".equals(nombreToOrder)){

                Toolkit tk = Toolkit.getDefaultToolkit();
                Image imagen = tk.getImage(getClass().getResource("/Imagenes/order.png"));
                Point hotSpot = new Point(0,0);
                Cursor cursor = tk.createCustomCursor(imagen, hotSpot, "Ordenar");
                this.setCursor(cursor);

            }       
    }//GEN-LAST:event_orderBeforeTableMouseDragged

    private void orderAfterTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderAfterTableMouseEntered
       if(order && !"".equals(nombreToOrder)){
                orderIn = true;
                orderAfterTable.setValueAt(nombreToOrder, indexOrder, 0);
      }
    }//GEN-LAST:event_orderAfterTableMouseEntered

    private void orderAfterTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderAfterTableMouseExited
        if (order && !"".equals(nombreToOrder)){    
                
                orderIn = false;
                orderAfterTable.setValueAt("", indexOrder, 0);
        }
    }//GEN-LAST:event_orderAfterTableMouseExited

    private void orderBeforeTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderBeforeTableMouseReleased
        // accion del release
            this.setCursor(null);
            if(order == true && orderIn == true && !"".equals(nombreToOrder)){
                orderAfterTable.setValueAt(nombreToOrder, indexOrder, 0);
                
                if (indexOrder!=45)
                    indexOrder++;
                if (indexDisorder!=0)
                    indexDisorder--;
                
                // Quito esta opcion de la tabla anterior para evitar que se repitan
                
                ((DefaultTableModel)orderBeforeTable.getModel()).removeRow(orderRow);
                ((DefaultTableModel)orderBeforeTable.getModel()).addRow(new Object[]{""});
                
                
            }
            
            order = false;
            orderIn=false;
    }//GEN-LAST:event_orderBeforeTableMouseReleased

    private void orderAfterTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderAfterTableMousePressed
            disorderRow = orderAfterTable.rowAtPoint(evt.getPoint());

            disorder=true; // Aqui con este booleano empieza todo. 

            // accion qee va hacer siempre
            if (disorderRow > -1) {

                Object objeto ;
                try {
                    objeto = orderAfterTable.getModel().getValueAt(disorderRow, 0);
                    nombreToDisorder = objeto.toString();
                }catch(Exception e){
                    nombreToDisorder = "";
                }

            }
    }//GEN-LAST:event_orderAfterTableMousePressed

    private void orderAfterTableMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderAfterTableMouseDragged
        if(disorder == true && disorderIn == false && !"".equals(nombreToDisorder)){

                Toolkit tk = Toolkit.getDefaultToolkit();
                Image imagen = tk.getImage(getClass().getResource("/Imagenes/remove.png"));
                Point hotSpot = new Point(0,0);
                Cursor cursor = tk.createCustomCursor(imagen, hotSpot, "Desordenar");
                this.setCursor(cursor);

            }    
    }//GEN-LAST:event_orderAfterTableMouseDragged

    private void orderBeforeTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderBeforeTableMouseEntered
       if(disorder && !"".equals(nombreToDisorder)){
                disorderIn = true;
                
                orderBeforeTable.setValueAt(nombreToDisorder, indexDisorder, 0);
      }
    }//GEN-LAST:event_orderBeforeTableMouseEntered

    private void orderBeforeTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderBeforeTableMouseExited
        if (disorder && !"".equals(nombreToDisorder)){    
                
                disorderIn = false;
                // Quito la ultima que agregue visualmente
                orderBeforeTable.setValueAt("", indexDisorder, 0);
        }
    }//GEN-LAST:event_orderBeforeTableMouseExited

    private void orderAfterTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderAfterTableMouseReleased
         // accion del release
            this.setCursor(null);
            
            if(disorder == true && disorderIn == true && !"".equals(nombreToDisorder)){
                // Si quiero desordenar y ya entre, debo de quitar la que deje, o ya no ponerla, en este caso ya no la pondre en beforeTable
                orderBeforeTable.setValueAt(nombreToDisorder, indexDisorder, 0);
                
                if (indexOrder!=0)
                    indexOrder--;
                if (indexDisorder!=45)
                    indexDisorder++;
                
                // Para evitar que se repita el desorden, lo quitare y agregare otra al final
                ((DefaultTableModel)orderAfterTable.getModel()).removeRow(disorderRow);
                // Agrego una vacia al final para que tenga siempre 45 filas
                ((DefaultTableModel)orderAfterTable.getModel()).addRow(new Object[]{"",false}); // Esto solo es de muestra
                
                
            }
            disorder = false;
            disorderIn=false;
    }//GEN-LAST:event_orderAfterTableMouseReleased
    /* Este metodo devuelve el rango de todas las fechas, las cuales siempre 
    tendran un valor, lo cual es idonio para el WHERE
    */
    private String betweenDates(){
        String between="";
        // Fecha Documento 
        if ((int)dateBeforeTable.getValueAt(0, 1)!=0 || (int)dateAfterTable.getValueAt(0, 1)!=0)
            between+=" Fecha_Documento_dia BETWEEN "+dateBeforeTable.getValueAt(0, 1)+" AND "+dateAfterTable.getValueAt(0, 1)+"OR";
        if ((int)dateBeforeTable.getValueAt(0, 2)!=0 || (int)dateAfterTable.getValueAt(0, 2)!=0)
            between+="  Fecha_Documento_mes BETWEEN "+dateBeforeTable.getValueAt(0, 2)+" AND "+dateAfterTable.getValueAt(0, 2)+"OR";
        if ((int)dateBeforeTable.getValueAt(0, 3)!=0 || (int)dateAfterTable.getValueAt(0, 3)!=0)    
            between+="  Fecha_Documento_anio BETWEEN "+dateBeforeTable.getValueAt(0, 3)+" AND "+dateAfterTable.getValueAt(0, 3)+"OR";
        
        // Fecha Extrema Inicial
        if ((int)dateBeforeTable.getValueAt(1, 1)!=0 || (int)dateAfterTable.getValueAt(1, 1)!=0)
            between+="  Fecha_Extrema_dia_inicial BETWEEN "+dateBeforeTable.getValueAt(1, 1)+" AND "+dateAfterTable.getValueAt(1, 1)+"OR";
        if ((int)dateBeforeTable.getValueAt(1, 2)!=0 || (int)dateAfterTable.getValueAt(1, 2)!=0)
            between+="  Fecha_Extrema_mes_inicial BETWEEN "+dateBeforeTable.getValueAt(1, 2)+" AND "+dateAfterTable.getValueAt(1, 2)+"OR";
        if ((int)dateBeforeTable.getValueAt(1, 3)!=0 || (int)dateAfterTable.getValueAt(1, 3)!=0)
            between+="  Fecha_Extrema_anio_inicial BETWEEN "+dateBeforeTable.getValueAt(1, 3)+" AND "+dateAfterTable.getValueAt(1, 3)+"OR";
        
        // Fecha Extrema Final
        if ((int)dateBeforeTable.getValueAt(2, 1)!=0 || (int)dateAfterTable.getValueAt(2, 1)!=0)
            between+="  Fecha_Extrema_dia_final BETWEEN "+dateBeforeTable.getValueAt(2, 1)+" AND "+dateAfterTable.getValueAt(2, 1)+"OR";
        if ((int)dateBeforeTable.getValueAt(2, 2)!=0 || (int)dateAfterTable.getValueAt(2, 2)!=0)
            between+="  Fecha_Extrema_mes_final BETWEEN "+dateBeforeTable.getValueAt(2, 2)+" AND "+dateAfterTable.getValueAt(2, 2)+"OR";
        if ((int)dateBeforeTable.getValueAt(2, 3)!=0 || (int)dateAfterTable.getValueAt(2, 3)!=0)
            between+="  Fecha_Extrema_anio_final BETWEEN "+dateBeforeTable.getValueAt(2, 3)+" AND "+dateAfterTable.getValueAt(2, 3)+"OR";
        
        // Fecha Extrema Caja Inicial
        if ((int)dateBeforeTable.getValueAt(3, 1)!=0 || (int)dateAfterTable.getValueAt(3, 1)!=0)
            between+="  Fecha_Extrema_Caja_dia_inicial BETWEEN "+dateBeforeTable.getValueAt(3, 1)+" AND "+dateAfterTable.getValueAt(3, 1)+"OR";
        if ((int)dateBeforeTable.getValueAt(3, 2)!=0 || (int)dateAfterTable.getValueAt(3, 2)!=0)
            between+="  Fecha_Extrema_Caja_mes_inicial BETWEEN "+dateBeforeTable.getValueAt(3, 2)+" AND "+dateAfterTable.getValueAt(3, 2)+"OR";
        if ((int)dateBeforeTable.getValueAt(3, 3)!=0 || (int)dateAfterTable.getValueAt(3, 3)!=0)
            between+="  Fecha_Extrema_Caja_anio_inicial BETWEEN "+dateBeforeTable.getValueAt(3, 3)+" AND "+dateAfterTable.getValueAt(3, 3)+"OR";
        
        // Fecha Extrema Caja Final
        if ((int)dateBeforeTable.getValueAt(4, 1)!=0 || (int)dateAfterTable.getValueAt(4, 1)!=0)
            between+="  Fecha_Extrema_Caja_dia_final BETWEEN "+dateBeforeTable.getValueAt(4, 1)+" AND "+dateAfterTable.getValueAt(4, 1)+"OR";
        if ((int)dateBeforeTable.getValueAt(4, 2)!=0 || (int)dateAfterTable.getValueAt(4, 2)!=0)
            between+="  Fecha_Extrema_Caja_mes_final BETWEEN "+dateBeforeTable.getValueAt(4, 2)+" AND "+dateAfterTable.getValueAt(4, 2)+"OR";
        if ((int)dateBeforeTable.getValueAt(4, 3)!=0 || (int)dateAfterTable.getValueAt(4, 3)!=0)
            between+="  Fecha_Extrema_Caja_anio_final BETWEEN "+dateBeforeTable.getValueAt(4, 3)+" AND "+dateAfterTable.getValueAt(4, 3)+"OR";
        
        // Fecha Extrema Legajo Inicial
        if ((int)dateBeforeTable.getValueAt(5, 1)!=0 || (int)dateAfterTable.getValueAt(5, 1)!=0)
            between+="  Fecha_Extrema_Legajo_dia_inicial BETWEEN "+dateBeforeTable.getValueAt(5, 1)+" AND "+dateAfterTable.getValueAt(5, 1)+"OR";
        if ((int)dateBeforeTable.getValueAt(5, 2)!=0 || (int)dateAfterTable.getValueAt(5, 2)!=0)
            between+="  Fecha_Extrema_Legajo_mes_inicial BETWEEN "+dateBeforeTable.getValueAt(5, 2)+" AND "+dateAfterTable.getValueAt(5, 2)+"OR";
        if ((int)dateBeforeTable.getValueAt(5, 3)!=0 || (int)dateAfterTable.getValueAt(5, 3)!=0)
            between+="  Fecha_Extrema_Legajo_anio_inicial BETWEEN "+dateBeforeTable.getValueAt(5, 3)+" AND "+dateAfterTable.getValueAt(5, 3)+"OR";
        
        // Fecha Extrema Legajo Final 
        if ((int)dateBeforeTable.getValueAt(6, 1)!=0 || (int)dateAfterTable.getValueAt(6, 1)!=0)
        between+="  Fecha_Extrema_Legajo_dia_final BETWEEN "+dateBeforeTable.getValueAt(6, 1)+" AND "+dateAfterTable.getValueAt(6, 1)+"OR";
        if ((int)dateBeforeTable.getValueAt(6, 2)!=0 || (int)dateAfterTable.getValueAt(6, 2)!=0)
        between+="  Fecha_Extrema_Legajo_mes_final BETWEEN "+dateBeforeTable.getValueAt(6, 2)+" AND "+dateAfterTable.getValueAt(6, 2)+"OR";
        if ((int)dateBeforeTable.getValueAt(6, 3)!=0 || (int)dateAfterTable.getValueAt(6, 3)!=0)
        between+="  Fecha_Extrema_Legajo_anio_final BETWEEN "+dateBeforeTable.getValueAt(6, 3)+" AND "+dateAfterTable.getValueAt(6, 3)+"OR";
        
        // Otra Fecha
        if ((int)dateBeforeTable.getValueAt(7, 1)!=0 || (int)dateAfterTable.getValueAt(7, 1)!=0)
        between+="  Otra_Fecha_dia BETWEEN "+dateBeforeTable.getValueAt(7, 1)+" AND "+dateAfterTable.getValueAt(7, 1)+"OR";
        if ((int)dateBeforeTable.getValueAt(7, 2)!=0 || (int)dateAfterTable.getValueAt(7, 2)!=0)
        between+="  Otra_Fecha_mes BETWEEN "+dateBeforeTable.getValueAt(7, 2)+" AND "+dateAfterTable.getValueAt(7, 2)+"OR";
        if ((int)dateBeforeTable.getValueAt(7, 3)!=0 || (int)dateAfterTable.getValueAt(7, 3)!=0)
        between+="  Otra_Fecha_anio BETWEEN "+dateBeforeTable.getValueAt(7, 3)+" AND "+dateAfterTable.getValueAt(7, 3)+"OR";
        
        if (between.length()>2)
            return between.substring(0, between.length()-2);
        else
            return "";
    }
    /* Este metodo sirve para ordenar segun no los pidan */
    private String orderBy(){
        String orderby ="";
        int filas = orderAfterTable.getModel().getRowCount(); // cantidad de filas 
        boolean value = false;
        String campo = "";
        
        for (int i = 0; i < filas ;i ++){ 
            try{    
                campo = String.valueOf(orderAfterTable.getModel().getValueAt(i, 0).toString()); // obtengo el nombre del campo de la tabla a ordenar
            }catch (Exception e){
                campo = "";
            }
            
            if (!"".equals(campo)){ // si no es vacio el nombre del campo obtendre el valor si es desendente o ascendente 
                try{    
                    value = Boolean.valueOf(orderAfterTable.getModel().getValueAt(i, 1).toString()); // obtengo el valor del check
                }catch (Exception e){
                    value = false;
                }
                if (value)
                    orderby +=campo+" DESC ,";
                else 
                    orderby +=campo+" ASC ,";
            }
        } // Fin del for  
        if (orderby.length()>1)
            return orderby.substring(0, orderby.length()-1); // Le quito la ultima coma
        else
        return "";
        
    }
    /* Ahora verificare los usuarios */
    private String byUsers(){
        String users = "";
        
        int filas = usersTable.getModel().getRowCount(); // cantidad de filas 
        boolean value = false;
        String user = "";
        
        for (int i = 0; i < filas ;i ++){ 
            try{    
                value = Boolean.valueOf(usersTable.getModel().getValueAt(i, 3).toString()); // obtengo el valor del check
            }catch (Exception e){
                value = false;
            }
            if (value){
                try {
                    user = String.valueOf(usersTable.getModel().getValueAt(i, 2).toString());
                }catch (Exception e){
                    user = "";
                }
                
                users+=" usuario.email LIKE '"+user+"' OR";
            }
                
        } // fin del for         
        
        if (users.length()>2)
            return users.substring(0, users.length()-2);
        else
        return "";
    }
    
    
    /*Este metodo sirve para obtener las series que tiene cheque */
    private String getCheckedSeries(){
        String checkedSeries = "";
        for(int i=0;i<nameChecks.length;i++){
            if(BDEstructura.isSerie(nameChecks[i], fondoChecks[i]) == 1 && selectedChecks[i]){
                checkedSeries += " codigo_serie = "+BDEstructura.getIdSub_Fondo(nameChecks[i], fondoChecks[i])+" OR"; 
            }
        }
        if (checkedSeries.length()>2)    
            return checkedSeries.substring(0, checkedSeries.length()-2);
        else return "";
    }
    
    private void putNamesIntoArrays(){
        
        for (int i = 0; i < nameChecks.length; i++) {
            fondosR.setSelectionRow(i);
            this.nameChecks[i] = fondosR.getSelectionPath().getLastPathComponent().toString();
            this.selectedChecks[i] = false;
            Object[] directorios = fondosR.getSelectionPath().getPath();
            if(directorios.length>2){
                this.fondoChecks[i] = directorios[2].toString();
            }
            else{
                this.fondoChecks[i] = "null";
            }
        }
       
    }
    
    public void reloadTreeInit() {
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Fondos");
        
        List<String[]> data = BDEstructura.getDataFromSub_Fondo();

        createTreeNodesForElement(root, getElementTreeFromPlainList(data));

        fondosR.setModel(new DefaultTreeModel(root));
        fondosR.setRootVisible(false);
        for (int i = 0; i < fondosR.getRowCount(); i++) {
            fondosR.expandRow(i);
        }
        
    }
    
    public void reloadTree(javax.swing.JTree arbol) {
        
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Fondos");
        
        List<String[]> data = BDEstructura.getDataFromSub_Fondo();
        createTreeNodesForElement(root, getElementTreeFromPlainList(data));
        arbol.setModel(new DefaultTreeModel(root));
        arbol.setRootVisible(false);
        for (int i = 0; i < arbol.getRowCount(); i++) {
            arbol.expandRow(i);
            
        }
        
        
        
        //pongo los iconos
        
        arbol.setCellRenderer(new DefaultTreeCellRenderer() {
            private Icon checked = new ImageIcon(System.getProperty("user.dir") +File.separator+"src"+File.separator+"Imagenes"+File.separator+"checked.png");
            private Icon unchecked = new ImageIcon(System.getProperty("user.dir") +File.separator+"src"+File.separator+"Imagenes"+File.separator+"unchecked.png");
            private Icon subFondo = new ImageIcon(System.getProperty("user.dir") +File.separator+"src"+File.separator+"Imagenes"+File.separator+"fondoSubfondo.png");
            
            @Override
            public Component getTreeCellRendererComponent(javax.swing.JTree jTreeSelection,
                    Object value, boolean selected, boolean expanded,
                    boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(jTreeSelection, value,
                        selected, expanded, isLeaf, row, focused);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                String s = node.getUserObject().toString();
                TreeNode[] directorios = node.getPath();
                int isSerie = 0;
                if(directorios.length>2){
                    isSerie = BDEstructura.isSerie(s,directorios[2].toString());
                }
                else{
                    isSerie = BDEstructura.isSerie(s,"null");
                }
                
                if(isSerie == 0){
                    setIcon(subFondo);
                }
                else{
                    
                    for(int i=0;i<nameChecks.length;i++){
                        if(directorios.length>2){
                            if(nameChecks[i].equals(s) && fondoChecks[i].equals(directorios[2].toString())){
                                if(selectedChecks[i]){
                                   setIcon(checked);
                                }
                                else{
                                    setIcon(unchecked);
                                }
                            }
                            
                        }
                        else{
                            if(nameChecks[i].equals(s) && fondoChecks[i].equals("null")){
                                if(selectedChecks[i]){
                                   setIcon(checked);
                                }
                                else{
                                    setIcon(unchecked);
                                }
                            }
                            
                        }
                        
                        
                    }
                }
                return c;
            }
        });
        
    }
    
    
    Collection<Element> getElementTreeFromPlainList(List<String[]> data) {
        // builds a map of elements object returned from store
        Map<String, Element> values = new HashMap<String, Element>();
        // get the ids to get their permiso
        
        for (String[] s : data) {
            values.put(s[0], new Element(s[2], s[1], s[0]));
        }

        // creates a result list
        Collection<Element> result = new ArrayList<Element>();
        
        

        // for each element in the result list that has a parent, put it into it
        // otherwise it is added to the result list
        for (Element e : values.values()) {
            if (e.parent != null) {
                values.get(e.parent).getChildren().add(e);
            } else {
                result.add(e);
            }
        }
        
        return result;
    }
    
    void createTreeNodesForElement(final DefaultMutableTreeNode dmtn, final Collection<Element> elements) {
        // for each element a tree node is created
        
        for (Element child : elements) {
            //aqui debe de verificar si tiene permiso el usuario actual para crear el nodo, sino no lo crea
            boolean guaranteedAccess = checkNodePermiso(child.getId());
            
            if(child.getName().equals(BDEstructura.getName("Fondos"))){
                DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
                dmtn.add(created);
                createTreeNodesForElement(created, child.getChildren());
            }
            if (guaranteedAccess){
                DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
                dmtn.add(created);
                createTreeNodesForElement(created, child.getChildren());
               
                
            }
            
        }
    }
    
    public boolean checkNodePermiso(String idIn){
        
        if(BDEstructura.getPermisoWithId(MasterMainView.getUserIn().getCodigo(),idIn)==1){
            return true;
        }
        else{
            return false;
        }
            
        
        
    }
    
    public static class Element {
        private final String parent;
        private final String name;
        private final Collection<Element> children = new ArrayList<Element>();
        private final String id;

        public Element(final String parent, final String name, final String id) {
            super();
            this.parent = parent;
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public Collection<Element> getChildren() {
            return children;
        }
        
        public String getId(){
            return id;
        }

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfigReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigReport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable camposTable;
    private javax.swing.JCheckBox checkBox1;
    private javax.swing.JCheckBox checkBoxCampos;
    private javax.swing.JTable dateAfterTable;
    private javax.swing.JTable dateBeforeTable;
    private javax.swing.JTree fondosR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelName;
    private javax.swing.JPanel labelPanel;
    private javax.swing.JTable orderAfterTable;
    private javax.swing.JTable orderBeforeTable;
    private javax.swing.JButton saveButton;
    private javax.swing.JTable usersTable;
    // End of variables declaration//GEN-END:variables


    


}
