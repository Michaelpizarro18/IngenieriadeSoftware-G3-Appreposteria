/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.ucv.examen.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author User
 */
public class RegistrarVenta {
//     private int registrarVenta(Connection conn, double total)  {
//        String insertVentaSQL = "INSERT INTO Venta (fecha, total) VALUES (GETDATE(), ?)";
//        try (PreparedStatement preparedStatement = conn.prepareStatement(insertVentaSQL, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setDouble(1, total);
//            preparedStatement.executeUpdate();
//
//            // Obtener el ID generado para la venta
//            return obtenerIdGenerado(preparedStatement);
//        }
//    }
//      private int obtenerIdGenerado(PreparedStatement preparedStatement) {
//        try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
//            if (generatedKeys.next()) {
//                return generatedKeys.getInt(1);
//            } else {
//                throw new SQLException("No se pudo obtener el ID generado.");
//            }
//        }
//    }
}
