/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.recursos;

import java.io.File;
import jjmailsend.JJMailProperties;
import jjmailsend.JJMailSend;

/**
 *
 * @author jhon1
 */
public class Email extends Thread {

    private String host;
    private String port;
    private String username;
    private String password;
    private JJMailProperties config;
    private String address;
    private int tipo;

    public Email() {
        this.host = "smtp.gmail.com";
        this.port = "587";
        this.username = "jhonjaider100015@gmail.com";
        this.password = "jhon8513";
        this.config = obtenerConfiguracion();
    }

    @Override
    public void run() {
        switch (this.tipo) {
            case 1: //Recuperar cuenta.
                this.emailRecuperacionCuenta();
                break;
            case 2:
                break;
        }
    }

    public void recuperarCuenta(String email) {
        this.tipo = 1;
        this.address = email;
        this.start();
    }

    private JJMailProperties obtenerConfiguracion() {
        JJMailProperties configuracion = new JJMailProperties();
        configuracion.setHost(this.host);
        configuracion.setPort(this.port);
        configuracion.setUsername(this.username);
        configuracion.setPassword(this.password);
        return configuracion;
    }

    private void emailRecuperacionCuenta() {
        /**
         * Para que este módulo funcione, todas las imagenes deben estar
         * guardadas en una misma carpeta Ej:(images). El sistema también busca
         * una carpeta attachments, si también se desean adjuntar archivos sin
         * complicaciones junto a la plantilla solo se debe crear dicha
         * carpeta(attacments) y guardar allí todos los adjuntos.
         */
        this.config.init();
        this.config.setSubject("Recuperación de cuenta.");
        String[] addresss = {this.address};
        this.config.setAddresses(addresss);
        this.config.setTemplate(new File("C:\\Users\\jhon1\\Documents\\NetBeansProjects\\votaciones\\web\\pages\\emailtemplates\\recoveraccount\\index.html"));
        this.config.replace("#username#", "¡Hola John!,");
        this.config.setHtml(true);
        JJMailSend ms = new JJMailSend();
        ms.sendMail(this.config);
    }

}
