/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.utiles;

/**
 *
 * @author jhon1
 */
public class App {

    //URL de la aplicación.
    public static String urlApp;
    //Email.
    public static String from;
    public static String fromName;
    public static String hostEmail;
    public static String portEmail;
    public static String usernameEmail;
    public static String passwordEmail;

    public static void init() {
        urlApp = "http://localhost:8084/votaciones/";
        from = "info@votaciones.com";
        fromName = "Votaciones";
        hostEmail = "smtp.gmail.com";
        portEmail = "587";
        usernameEmail = "john.vanegas67@gmail.com";
        passwordEmail = "jhon1512211452";
    }

}
