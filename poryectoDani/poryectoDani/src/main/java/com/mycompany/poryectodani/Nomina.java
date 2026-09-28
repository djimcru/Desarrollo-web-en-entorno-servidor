package com.mycompany.poryectodani;



import java.sql.*;


public class Nomina {


   private static final int[] sueldoBase = {
           50000, 70000, 90000, 110000, 130000,
           150000, 170000, 190000, 210000, 230000
   };


   public double sueldo(Empleado e) {
       return sueldoBase[e.getCategoria() - 1]
               + 5000 * e.getAnyos();
   }


   public void guardarSueldo(Empleado e) throws SQLException {


       double sueldo = sueldo(e);


       String sql = """
               INSERT INTO Nominas (dni, sueldo)
               VALUES (?, ?)
               ON DUPLICATE KEY UPDATE sueldo = ?
               """;


       try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {


           ps.setString(1, e.dni);
           ps.setDouble(2, sueldo);
           ps.setDouble(3, sueldo);


           ps.executeUpdate();
       }
   }


   public double consultarSueldo(String dni) throws SQLException {


       String sql = """
               SELECT sueldo
               FROM Nominas
               WHERE dni = ?
               """;


       try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {


           ps.setString(1, dni);


           try (ResultSet rs = ps.executeQuery()) {


               if (rs.next()) {
                   return rs.getDouble("sueldo");
               }
           }
       }


       return -1;
   }


   public void eliminarNomina(String dni) throws SQLException {


       String sql = "DELETE FROM Nominas WHERE dni = ?";


       try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {


           ps.setString(1, dni);


           ps.executeUpdate();
       }
   }
}
