package com.mycompany.poryectodani;


public class CalculaNominas {


   public static void main(String[] args) {


       try {


           Empleado empleado = new Empleado(
                   4,
                   7,
                   "Pepito Perez",
                   "12345678g",
                   "M"
           );
           empleado.altaEmpleado();
           System.out.println("Empleado dado de alta correctamente.");
           empleado.altaEmpleado("empleadosNuevos.txt");
           System.out.println(
                   "Empleados del fichero dados de alta correctamente."
           );


       } catch (DatosNoCorrectosException e) {
           System.out.println("Datos no correctos.");
       } catch (Exception e) {

           System.out.println("Error: " + e.getMessage());
       }
   }
}
