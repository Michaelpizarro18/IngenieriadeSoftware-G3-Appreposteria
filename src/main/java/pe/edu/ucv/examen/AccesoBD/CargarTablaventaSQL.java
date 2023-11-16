/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.ucv.examen.AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class CargarTablaventaSQL {

    public CargarTablaventaSQL() {
    }
    
     public void cargarDatosEnTabla(DefaultTableModel modelito, JTextField txtTotalFinal, JTextField txtIDCompra) {
        int idVenta = 0;
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
}