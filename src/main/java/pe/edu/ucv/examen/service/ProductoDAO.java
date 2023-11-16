package pe.edu.ucv.examen.service;



import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import pe.edu.ucv.examen.AccesoBD.AccesoBD;
import pe.edu.ucv.examen.Controller.Producto;


/*Metodo listar*/
public class ProductoDAO{

  public ArrayList<Producto> MostrarTodosLosProductos() {
    ArrayList<Producto> list = new ArrayList<Producto>();
    String sql = "EXEC MostrarTodosLosProductos"; // Llama al procedimiento almacenado
    try (Connection con = AccesoBD.getConexion();
         CallableStatement cs = con.prepareCall(sql);
         ResultSet rs = cs.executeQuery()) {
        while (rs.next()) {
            Producto vo = new Producto();
            vo.setIdProducto(rs.getInt(1));
            vo.setNombre(rs.getString(2));
            vo.setPrecio(rs.getDouble(3));
            vo.setCategoria(rs.getInt(4));
            vo.setIdProveedor(rs.getInt(5)); // Asume que hay un atributo "proveedor" en Producto
            vo.setReceta(rs.getString(6));
            vo.setFoto(rs.getBytes(7));
            list.add(vo);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.toString());
    }

    return list;
}


/*Metodo agregar*/
   public void AgregarProducto(Producto producto) {
     try {
        Connection con = AccesoBD.getConexion();
        CallableStatement cs = con.prepareCall("{call InsertarProducto(?, ?, ?, ?, ?, ?)}");

        cs.setString(1, producto.getNombre());
        cs.setDouble(2, producto.getPrecio());
        cs.setInt(3, producto.getCategoria());
        cs.setInt(4, producto.getIdProveedor());
        cs.setString(5, producto.getReceta());
        cs.setBytes(6, producto.getFoto());

        cs.executeUpdate();

        JOptionPane.showMessageDialog(null, "Registro de producto guardado");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.toString());
    }
}
}
//
///*Metodo Modificar*/
//    public void Modificar_ProductoVO(ProductoVO vo){
//        Conectar conec = new Conectar();
//        String sql = "UPDATE producto SET nombre = ?, precio = ?, marca = ?, foto = ?\n" +
//"WHERE idProducto = ?;";
//        PreparedStatement ps = null;
//        try{
//            ps = conec.getConnection().prepareStatement(sql);
//            ps.setString(1, vo.getNombre());
//            ps.setDouble(2, vo.getPrecio());
//            ps.setString(3, vo.getMarca());
//            ps.setBytes(4, vo.getFoto());
//            ps.setInt(5, vo.getIdproducto());
//            ps.executeUpdate();
//        }catch(SQLException ex){
//            System.out.println(ex.getMessage());
//        }catch(Exception ex){
//            System.out.println(ex.getMessage());
//        }finally{
//            try{
//                ps.close();
//                conec.desconectar();
//            }catch(Exception ex){}
//        }
//    }
//
//    public void Modificar_ProductoVO2(ProductoVO vo){
//        Conectar conec = new Conectar();
//        String sql = "UPDATE producto SET nombre = ?, precio = ?, marca = ? \n" +
//"WHERE idProducto = ?;";
//        PreparedStatement ps = null;
//        try{
//            ps = conec.getConnection().prepareStatement(sql);
//            ps.setString(1, vo.getNombre());
//            ps.setDouble(2, vo.getPrecio());
//            ps.setString(3, vo.getMarca());
//            ps.setInt(4, vo.getIdproducto());
//            ps.executeUpdate();
//        }catch(SQLException ex){
//            System.out.println(ex.getMessage());
//        }catch(Exception ex){
//            System.out.println(ex.getMessage());
//        }finally{
//            try{
//                ps.close();
//                conec.desconectar();
//            }catch(Exception ex){}
//        }
//    }
//
///*Metodo Eliminar*/
//    public void Eliminar_ProductoVO(ProductoVO vo){
//        Conectar conec = new Conectar();
//        String sql = "DELETE FROM producto WHERE idProducto = ?;";
//        PreparedStatement ps = null;
//        try{
//            ps = conec.getConnection().prepareStatement(sql);
//            ps.setInt(1, vo.getIdproducto());
//            ps.executeUpdate();
//        }catch(SQLException ex){
//            System.out.println(ex.getMessage());
//        }catch(Exception ex){
//            System.out.println(ex.getMessage());
//        }finally{
//            try{
//                ps.close();
//                conec.desconectar();
//            }catch(Exception ex){}
//        }
//    }
//
//
//}
