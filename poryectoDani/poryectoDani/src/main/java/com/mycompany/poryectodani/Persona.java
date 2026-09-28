/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poryectodani;

/**
 *
 * @author usuario26
 */
public class Persona {
   public String nombre, dni, sexo;
  
 


   public Persona(String nombre, String dni, String sexo) {
       this.nombre = nombre;
       this.dni = dni;
       this.sexo = sexo;
   }


   public Persona(String nombre, String sexo) {
       this.nombre = nombre;
       this.sexo = sexo;
   }
  
   public void setDni (String nuevoDni) {
       dni = nuevoDni;
   }
  
   public void imprime() {
       System.out.println("Nombre: " + nombre);
       System.out.println("DNI: " + dni);
   }
  
  
}
