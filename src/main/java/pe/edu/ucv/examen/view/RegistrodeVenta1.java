package pe.edu.ucv.examen.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import pe.edu.ucv.examen.AccesoBD.AccesoBD;
import pe.edu.ucv.examen.model.CajasdeTextoModel;
import pe.edu.ucv.examen.model.ModelTabla;

/**
 *
 * @author User
 */
public class RegistrodeVenta1 extends javax.swing.JPanel {

    ModelTabla model = new ModelTabla();
    private DefaultTableModel modelito;
    CajasdeTextoModel cajasdetexto = new CajasdeTextoModel();
    private int filaSeleccionada;

    public RegistrodeVenta1() {
        initComponents();
        modelito = new DefaultTableModel();
        model.inicializarModelo(modelito);
        cajasdetexto.DesactivarCajas(txtIDCompra, txtIDProducto, txtPrecio, txtSubtotal, txtTotalFinal);

        tblDatos.setModel(modelito);
        CargarComboProductos();
        txtCantidad.getDocument().addDocumentListener(new MyDocumentListener());
        txtPrecio.getDocument().addDocumentListener(new MyDocumentListener());
        txtDescuento.getDocument().addDocumentListener(new MyDocumentListener());
    }

    public void CargarComboProductos() {
        String consultaSql = "SELECT nombre FROM Producto";
        Connection con = null; // Declarar la conexión

        try {
            // Obtener la conexión a la base de datos (Asumiendo que 'AccesoBD' es la clase que gestiona la conexión)
            con = AccesoBD.getConexion();

            if (con != null) {
                try (PreparedStatement statement = con.prepareStatement(consultaSql);
                        ResultSet resultSet = statement.executeQuery()) {
                    // Limpia el ComboBox antes de agregar nuevos elementos
                    cbxproducto.removeAllItems();

                    // Agrega los nombres de los productos al ComboBox
                    while (resultSet.next()) {
                        String nombreProducto = resultSet.getString("nombre");
                        cbxproducto.addItem(nombreProducto);
                    }
                }
            } else {
                System.err.println("No se pudo obtener la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cierra la conexión si se abrió correctamente
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class MyDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            calcularResultado();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            calcularResultado();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            calcularResultado();
        }

        private void calcularResultado() {
            try {
                int num1 = (txtCantidad.getText().isEmpty()) ? 1 : Integer.parseInt(txtCantidad.getText());
                double num2 = (txtPrecio.getText().isEmpty()) ? 1 : Double.parseDouble(txtPrecio.getText());
                double num3 = (txtDescuento.getText().isEmpty() || txtDescuento.getText().equals("0")) ? 1 : Double.parseDouble(txtDescuento.getText());

                double resultado = num1 * num2 * num3;
                txtSubtotal.setText(String.valueOf(resultado));
            } catch (NumberFormatException ex) {
                txtSubtotal.setText("Error");
            }
        }

    }

    private void cargarDatosEnTabla(int idVenta) {   
        modelito.setRowCount(0);
        PreparedStatement psDetalle, psVenta;
        ResultSet rsDetalle, rsVenta;
        ResultSetMetaData rsmdDetalle, rsmdVenta;
        int columnasDetalle, columnasVenta;

        try {
            Connection con = AccesoBD.getConexion();

            // Consulta para cargar los detalles de la venta
            psDetalle = con.prepareStatement("SELECT r_producto, cantidad, precio, descuento, subtotal FROM Detalle WHERE idVenta = ?");
            psDetalle.setInt(1, idVenta);
            rsDetalle = psDetalle.executeQuery();
            rsmdDetalle = rsDetalle.getMetaData();
            columnasDetalle = rsmdDetalle.getColumnCount();

            while (rsDetalle.next()) {
                Object[] fila = new Object[columnasDetalle];
                for (int a = 0; a < columnasDetalle; a++) {
                    fila[a] = rsDetalle.getObject(a + 1);
                }
                modelito.addRow(fila);
            }

            // Consulta para cargar el total de la venta
            psVenta = con.prepareStatement("SELECT total, idVenta FROM Venta WHERE idVenta = ?");
            psVenta.setInt(1, idVenta);
            rsVenta = psVenta.executeQuery();
            rsmdVenta = rsVenta.getMetaData();
            columnasVenta = rsmdVenta.getColumnCount();

            // Verificar si hay algún resultado antes de intentar obtenerlo
            if (rsVenta.next()) {
                double totalVenta = rsVenta.getDouble(1);
                int iden = rsVenta.getInt(2);

                // Ahora puedes hacer algo con el total de la venta, por ejemplo, mostrarlo en un JTextField
                txtTotalFinal.setText(String.valueOf(totalVenta));
                txtIDCompra.setText(String.valueOf(iden));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelFondo = new javax.swing.JPanel();
        PanelUp = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jbtnGuardar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        txtIDCompra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtIDProducto = new javax.swing.JTextField();
        cbxproducto = new javax.swing.JComboBox<>();
        txtFecha = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        txtSubtotal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnAgregarATabla = new javax.swing.JButton();
        PanelDown = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtTotalFinal = new javax.swing.JTextField();
        btnQuitarDeTabla = new javax.swing.JButton();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelFondo.setBackground(new java.awt.Color(204, 204, 204));
        PanelFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelUp.setBackground(new java.awt.Color(153, 0, 255));
        PanelUp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REGISTRO DE VENTAS");
        PanelUp.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));

        PanelFondo.add(PanelUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 70));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 40));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 130, 40));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel2.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 100, 40));

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 90, 40));

        txtIDCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIDCompra.setText("Actual");
        txtIDCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtIDCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, 90, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ID VENTA:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 0, -1, -1));

        PanelFondo.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1160, 60));

        jPanel3.setBackground(new java.awt.Color(153, 102, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Cod. Producto:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Producto:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Cantidad:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Precio:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Descuento:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Subtotal:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, -1, -1));

        txtIDProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(txtIDProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 100, 40));

        cbxproducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----Seleccione----" }));
        cbxproducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxproductoItemStateChanged(evt);
            }
        });
        jPanel3.add(cbxproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 160, 40));

        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 70, 40));

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 90, 40));

        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 100, 40));

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 100, 40));

        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubtotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(txtSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 120, 110, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Fecha:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        btnAgregarATabla.setText("+");
        btnAgregarATabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarATablaActionPerformed(evt);
            }
        });
        jPanel3.add(btnAgregarATabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 120, 50, 40));

        PanelFondo.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 1160, 180));

        PanelDown.setBackground(new java.awt.Color(255, 204, 255));
        PanelDown.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUCT", "CANTIDAD", "PRECIO", "DESCUENTO", "SUBTOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDatos);

        PanelDown.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 1010, 200));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("TOTAL");
        PanelDown.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 240, -1, -1));

        txtTotalFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalFinal.setText("0.0");
        txtTotalFinal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelDown.add(txtTotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 230, 150, 40));

        btnQuitarDeTabla.setText("-");
        btnQuitarDeTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarDeTablaActionPerformed(evt);
            }
        });
        PanelDown.add(btnQuitarDeTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, 50, 40));

        PanelFondo.add(PanelDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 1160, 290));

        jPanel1.add(PanelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 620));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        /*for (int i = 0; i < modelito.getRowCount(); i++) {
            String Datos[] = new String[7];
            Datos[0] = tblDatos.getValueAt(i, 0).toString();
            Datos[1] = tblDatos.getValueAt(i, 1).toString();
            Datos[2] = tblDatos.getValueAt(i, 2).toString();
            Datos[3] = tblDatos.getValueAt(i, 3).toString();
            Datos[4] = tblDatos.getValueAt(i, 4).toString();
            Datos[5] = tblDatos.getValueAt(i, 5).toString();
            Datos[6] = tblDatos.getValueAt(i, 6).toString();

            try {
                Connection con = AccesoBD.getConexion();
                PreparedStatement ps = con.prepareStatement("Insert into PROBANDO values(?,?,?,?,?,?,?)");
                ps.setString(1, Datos[0]);
                ps.setString(2, Datos[1]);
                ps.setString(3, Datos[2]);
                ps.setString(4, Datos[3]);
                ps.setString(5, Datos[4]);
                ps.setString(6, Datos[5]);
                ps.setString(7, Datos[6]);
                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }*/

        try (Connection conexion = AccesoBD.getConexion()) {
            if (conexion != null) {
                // Obtener datos de la tabla
                DefaultTableModel modelo = (DefaultTableModel) tblDatos.getModel();
                Vector<Vector> dataVector = modelo.getDataVector();
                double total = Double.parseDouble(txtTotalFinal.getText());

                // Iniciar la transacción
                conexion.setAutoCommit(false);

                // Registrar la venta y obtener el idVenta ge>nerado
                int idVenta = registrarVenta(conexion, total);

                // Registrar el detalle de venta para cada fila en la tabla
                for (Vector<Object> fila : dataVector) {
                    registrarDetalleVenta(conexion, idVenta, fila);
                }

                // Confirmar la transacción
                conexion.commit();

                JOptionPane.showMessageDialog(this, "Venta y detalle de venta registrados correctamente");
                modelito.setRowCount(0);
                txtTotalFinal.setText("0");
                txtSubtotal.setText("0");
                txtDescuento.setText("0");
                txtCantidad.setText("");
                cbxproducto.setSelectedIndex(0);
                cbxproducto.requestFocus();
            }
        } catch (SQLException e) {
            // Si hay un error, realizar un rollback para deshacer los cambios
            JOptionPane.showMessageDialog(this, "Error al registrar la venta y detalle de venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //conexion.rollback(); // Manejar el error de rollback si es necesario
        }

    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private int registrarVenta(Connection conn, double total) throws SQLException {
        String insertVentaSQL = "INSERT INTO Venta (fecha, total) VALUES (GETDATE(), ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertVentaSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, total);
            preparedStatement.executeUpdate();

            // Obtener el ID generado para la venta
            return obtenerIdGenerado(preparedStatement);
        }
    }

    private void registrarDetalleVenta(Connection conn, int idVenta, Vector<Object> fila) throws SQLException {
        String insertDetalleVentaSQL = "INSERT INTO Detalle (idVenta, r_producto, cantidad, precio, descuento, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertDetalleVentaSQL)) {
            preparedStatement.setInt(1, idVenta);
            preparedStatement.setString(2, fila.get(0).toString()); // Columna PRODUCTO
            preparedStatement.setInt(3, Integer.parseInt(fila.get(1).toString())); // Columna CANTIDAD
            preparedStatement.setDouble(4, Double.parseDouble(fila.get(2).toString())); // Columna PRECIO
            preparedStatement.setDouble(5, Double.parseDouble(fila.get(3).toString())); // Columna DESCUENTO
            preparedStatement.setDouble(6, Double.parseDouble(fila.get(4).toString())); // Columna SUBTOTAL

            preparedStatement.executeUpdate();
        }
    }

    private int obtenerIdGenerado(PreparedStatement preparedStatement) throws SQLException {
        try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID generado.");
            }
        }
    }


    private void btnAgregarATablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarATablaActionPerformed
        String producto = (String) cbxproducto.getSelectedItem();
        String cantidad = txtCantidad.getText();
        String precio = txtPrecio.getText();
        String descuento = txtDescuento.getText();
        String subtotal = txtSubtotal.getText();

        // Agregar la nueva fila a la tabla
        Object[] fila = {producto, cantidad, precio, descuento, subtotal};
        modelito.addRow(fila);

        calcularYMostrarTotal();

        // Limpiar campos
        ModelTabla limpiar = new ModelTabla();
        limpiar.limpiarCampos(txtFecha, txtIDProducto, txtCantidad, txtDescuento, txtSubtotal);

        // Calcular el nuevo total de subtotales
        /*double totalFinal = 0;
        int rowCount = modelito.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            // Sumar el subtotal de cada fila
            double subtotalFila = Double.parseDouble(modelito.getValueAt(i, 4).toString());
            totalFinal += subtotalFila;
        }

        // Mostrar el total en el campo txtTotalFinal
        txtTotalFinal.setText(String.valueOf(totalFinal));

        // Limpiar campos
        ModelTabla limpiar = new ModelTabla();
        limpiar.limpiarCampos(txtFecha, txtIDProducto, txtCantidad, txtDescuento, txtSubtotal);*/

    }//GEN-LAST:event_btnAgregarATablaActionPerformed

    private void btnQuitarDeTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDeTablaActionPerformed
        /*int com;

        com = tblDatos.getSelectedRow();
        if (com == -1) {
            JOptionPane.showMessageDialog(null, "Seleccionar el producto a eliminar");
        } else {
            modelito.removeRow(com);
            

        }*/

        int selectedRow = tblDatos.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) tblDatos.getModel();
            double subtotalEliminar = Double.parseDouble(model.getValueAt(selectedRow, 4).toString());

            // Eliminar la fila seleccionada
            model.removeRow(selectedRow);

            // Calcular y mostrar el nuevo total de subtotales después de eliminar la fila
            calcularYMostrarTotal();
        }

    }//GEN-LAST:event_btnQuitarDeTablaActionPerformed

    private void calcularYMostrarTotal() {
        double totalFinal = 0;
        int rowCount = modelito.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            // Sumar el subtotal de cada fila
            double subtotalFila = Double.parseDouble(modelito.getValueAt(i, 4).toString());
            totalFinal += subtotalFila;
        }

        // Mostrar el total en el campo txtTotalFinal
        txtTotalFinal.setText(String.valueOf(totalFinal));
    }

    private void cbxproductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxproductoItemStateChanged
        String prod = (String) cbxproducto.getSelectedItem();

        String consultaSql = "SELECT idProducto FROM Producto WHERE nombre = ?";
        String consultaprecio = "SELECT precio FROM Producto WHERE nombre = ?";
        Connection con = null; // Declarar la conexión
        try {
            con = AccesoBD.getConexion();
            try (PreparedStatement statement = con.prepareStatement(consultaSql)) {
                statement.setString(1, prod);
                ResultSet resultSet = statement.executeQuery();

                // Muestra el ID del producto en el JTextField
                if (resultSet.next()) {
                    int idProducto = resultSet.getInt("idProducto");
                    txtIDProducto.setText(String.valueOf(idProducto));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try (PreparedStatement statement = con.prepareStatement(consultaprecio)) {
            statement.setString(1, prod);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Double precio = resultSet.getDouble("precio");
                txtPrecio.setText(String.valueOf(precio));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cbxproductoItemStateChanged

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        /*modelito.setValueAt(txtCantidad.getText().trim(), filaSeleccionada, 1);
        modelito.setValueAt(txtDescuento.getText().trim(), filaSeleccionada, 3);
        modelito.setValueAt(txtSubtotal.getText().trim(), filaSeleccionada, 4);
        ModelTabla limpiar = new ModelTabla();
        limpiar.limpiarCampos(txtIDProducto, txtCantidad, txtDescuento, txtSubtotal);*/
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el idVenta para actualizar"));
        cargarDatosEnTabla(id);

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        //txtCantidad.setText(modelito.getValueAt(tblDatos.getSelectedRow(), 1).toString());
        //txtDescuento.setText(modelito.getValueAt(tblDatos.getSelectedRow(), 3).toString());

        int selectedRow = tblDatos.getSelectedRow();

        // Verificar si hay alguna fila seleccionada
        if (selectedRow != -1) {
            // Obtener los datos de la fila seleccionada
            DefaultTableModel model = (DefaultTableModel) tblDatos.getModel();
            String producto = (String) model.getValueAt(selectedRow, 0);
            String cantidad = model.getValueAt(selectedRow, 1).toString();
            String precio = model.getValueAt(selectedRow, 2).toString();
            String descuento = model.getValueAt(selectedRow, 3).toString();
            String subtotal = model.getValueAt(selectedRow, 4).toString();

            // Establecer los datos en los campos
            cbxproducto.setSelectedItem(producto);
            txtCantidad.setText(cantidad);
            txtPrecio.setText(precio);
            txtDescuento.setText(descuento);
            txtSubtotal.setText(subtotal);
        }


    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtIDCompra.setText("Actual");
        cbxproducto.setSelectedIndex(0);
        txtCantidad.setText("0");
        txtDescuento.setText("0");
        modelito.removeRow(0);
        txtTotalFinal.setText("0.0");
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int idVenta = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese ID de la Venta a eliminar"));

        try {
            Connection con = AccesoBD.getConexion();

            // Verificar si el ID de la Venta existe
            PreparedStatement psVerificar = con.prepareStatement("SELECT COUNT(*) FROM Venta WHERE idVenta = ?");
            psVerificar.setInt(1, idVenta);
            ResultSet rsVerificar = psVerificar.executeQuery();
            rsVerificar.next();
            int rowCount = rsVerificar.getInt(1);

            if (rowCount > 0) {
                // El ID de la Venta existe, proceder con la eliminación

                // Iniciar una transacción
                con.setAutoCommit(false);

                try {
                    // Eliminar registros de la tabla Detalle
                    PreparedStatement psDetalle = con.prepareStatement("DELETE FROM Detalle WHERE idVenta = ?");
                    psDetalle.setInt(1, idVenta);
                    psDetalle.executeUpdate();

                    // Eliminar registro de la tabla Venta
                    PreparedStatement psVenta = con.prepareStatement("DELETE FROM Venta WHERE idVenta = ?");
                    psVenta.setInt(1, idVenta);
                    psVenta.executeUpdate();

                    // Confirmar la transacción
                    con.commit();
                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                } catch (SQLException e) {
                    // Si ocurre un error, hacer un rollback para deshacer la transacción
                    con.rollback();
                    JOptionPane.showMessageDialog(null, "Error al eliminar el registro:\n" + e.toString());
                } finally {
                    // Restablecer la configuración de la conexión
                    con.setAutoCommit(true);
                }
            } else {
                // El ID de la Venta no existe
                JOptionPane.showMessageDialog(null, "El ID de la Venta no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }


    }//GEN-LAST:event_btnEliminarActionPerformed

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDown;
    private javax.swing.JPanel PanelFondo;
    private javax.swing.JPanel PanelUp;
    private javax.swing.JButton btnAgregarATabla;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitarDeTabla;
    private javax.swing.JComboBox<String> cbxproducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIDCompra;
    private javax.swing.JTextField txtIDProducto;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotalFinal;
    // End of variables declaration//GEN-END:variables
}
