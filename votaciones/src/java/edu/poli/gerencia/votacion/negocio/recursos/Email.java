/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.recursos;

import edu.poli.gerencia.votacion.modelo.vo.Persona;
import edu.poli.gerencia.votacion.negocio.util.App;
import edu.poli.gerencia.votacion.negocio.util.Util;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Persona persona;
    private int tipo;

    public Email() {
        App.init();
        this.host = App.hostEmail;
        this.port = App.portEmail;
        this.username = App.usernameEmail;
        this.password = App.passwordEmail;
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

    public void recuperarCuenta(Persona persona) {
        this.tipo = 1;
        this.persona = persona;
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
        try {
            this.config.init();
            this.config.setSubject("Recuperación de cuenta.");
            String[] addresss = {this.persona.getCorreo()};
            System.out.println(addresss);
            this.config.setAddresses(addresss);
            this.config.setTemplate(new File("C:\\Users\\jhon1\\Documents\\NetBeansProjects\\votacionesapp\\votacionesElectronica\\votaciones\\web\\pages\\emailtemplates\\recoveraccount\\index.html"));
            this.config.replace("#username#", this.persona.getPrimerNombre());
            this.config.replace("#URL#", App.urlApp + "updateaccount/" + Util.md5(this.persona.getCorreo()));
            this.config.replace("#urlapp#", App.urlApp);
            this.config.setHtml(true);
            JJMailSend ms = new JJMailSend();
            ms.sendMail(this.config);
        } catch (Exception e) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
