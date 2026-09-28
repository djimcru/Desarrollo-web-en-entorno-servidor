package com.mycompany.poryectodani;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class Empleado extends Persona {


   private int categoria;
   private int anyos;


   public Empleado(int categoria, int anyos, String nombre,
                   String dni, String sexo)
           throws DatosNoCorrectosException {


       super(nombre, dni, sexo);


       if (categoria < 1 || categoria > 10 || anyos < 0) {
           throw new DatosNoCorrectosException();
       }


       this.categoria = categoria;
       this.anyos = anyos;
   }


   public Empleado(String nombre, String dni, String sexo)
           throws DatosNoCorrectosException {


       super(nombre, dni, sexo);


       this.categoria = 1;
       this.anyos = 0;
   }


   public int getCategoria() {
       return categoria;
   }


   public void setCategoria(int categoria)
           throws DatosNoCorrectosException {


       if (categoria < 1 || categoria > 10) {
           throw new DatosNoCorrectosException();
       }


       this.categoria = categoria;
   }


   public int getAnyos() {
       return anyos;
   }


   public void setAnyos(int anyos)
           throws DatosNoCorrectosException {


       if (anyos < 0) {
           throw new DatosNoCorrectosException();
       }


       this.anyos = anyos;
   }


   public void incrAnyo() {
       anyos++;
   }


   public void altaEmpleado() throws SQLException {


       String sql = """
               INSERT INTO Empleados
               (dni, nombre, sexo, categoria, anyos)
               VALUES (?, ?, ?, ?, ?)
               """;


       try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {


           ps.setString(1, dni);
           ps.setString(2, nombre);
           ps.setString(3, sexo);
           ps.setInt(4, categoria);
           ps.setInt(5, anyos);


           ps.executeUpdate();
       }


       Nomina nomina = new Nomina();
       nomina.guardarSueldo(this);
   }


   public void altaEmpleado(String nombreFichero)
           throws SQLException, IOException,
           DatosNoCorrectosException {


       try (BufferedReader br =
                    new BufferedReader(new FileReader(nombreFichero))) {


           String linea;


           while ((linea = br.readLine()) != null) {


               if (linea.trim().isEmpty()) {
                   continue;
               }


               String[] datos = linea.split(";");


               int categoria = Integer.parseInt(datos[0]);
               int anyos = Integer.parseInt(datos[1]);
               String nombre = datos[2];
               String dni = datos[3];
               String sexo = datos[4];


               Empleado empleado = new Empleado(
                       categoria,
                       anyos,
                       nombre,
                       dni,
                       sexo
               );


               empleado.altaEmpleado();
           }
       }
   }


   public static Empleado buscarEmpleado(String dni)
           throws SQLException, DatosNoCorrectosException {


       String sql = """
               SELECT dni, nombre, sexo, categoria, anyos
               FROM Empleados
               WHERE dni = ?
               """;


       try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {


           ps.setString(1, dni);


           try (ResultSet rs = ps.executeQuery()) {


               if (rs.next()) {


                   return new Empleado(
                           rs.getInt("categoria"),
                           rs.getInt("anyos"),
                           rs.getString("nombre"),
                           rs.getString("dni"),
                           rs.getString("sexo")
                   );
               }
           }
       }


       return null;
   }


   public static ArrayList<Empleado> listarEmpleados()
           throws SQLException, DatosNoCorrectosException {


       ArrayList<Empleado> lista = new ArrayList<>();


       String sql = """
               SELECT dni, nombre, sexo, categoria, anyos
               FROM Empleados
               """;


       try (Connection con = ConexionBD.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {


           while (rs.next()) {


               Empleado e = new Empleado(
                       rs.getInt("categoria"),
                       rs.getInt("anyos"),
                       rs.getString("nombre"),
                       rs.getString("dni"),
                       rs.getString("sexo")
               );


               lista.add(e);
           }
       }


       return lista;
   }


   public void modificarEmpleado()
           throws SQLException {


       String sql = """
               UPDATE Empleados
               SET nombre = ?,
                   dni = ?,
                   sexo = ?,
                   categoria = ?,
                   anyos = ?
               WHERE dni = ?
               """;


       try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {


           ps.setString(1, nombre);
           ps.setString(2, dni);
           ps.setString(3, sexo);
           ps.setInt(4, categoria);
           ps.setInt(5, anyos);
           ps.setString(6, dni);


           ps.executeUpdate();
       }
   }


   @Override
   public void imprime() {


       System.out.println("Nombre: " + nombre);
       System.out.println("DNI: " + dni);
       System.out.println("Sexo: " + sexo);
       System.out.println("Categoría: " + categoria);
       System.out.println("Años trabajados: " + anyos);
   }
}
