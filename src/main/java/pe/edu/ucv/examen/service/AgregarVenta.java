/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.ucv.examen.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pe.edu.ucv.examen.AccesoBD.AccesoBD;

/**
 *
 * @author User
 */
public class AgregarVenta {
    
       public void insertarDatosProcedimientoAlmacenado(String[] datos) {
        try {
            Connection con = AccesoBD.getConexion();
            
            // Llamar al procedimiento almacenado
            String sql = "{CALL InsertarDatosVentas(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cs = con.prepareCall(sql);
            
            // Configurar los parámetros
               cs.setString(1, datos[0]);
               cs.setString(2, datos[1]);
               cs.setString(3, datos[2]);
               cs.setString(4, datos[3]);
               cs.setString(5, datos[4]);
               cs.setString(6, datos[5]);
               cs.setString(7, datos[6]);
               cs.executeUpdate();
               JOptionPane.showMessageDialog(null, "Registro de producto guardado");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }  
}
