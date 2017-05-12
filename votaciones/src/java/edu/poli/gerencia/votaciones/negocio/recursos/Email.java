/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.recursos;

import edu.poli.gerencia.votacion.negocio.utiles.App;
import edu.poli.gerencia.votacion.negocio.utiles.Util;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.constantes.EmailTemplates;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import jjmailsend.JJMailProperties;
import jjmailsend.JJMailSend;

/**
 *
 * @author jhon1
 */
public class Email {

    private String host;
    private String port;
    private String username;
    private String password;
    private JJMailProperties config;
    private Persona persona;
    private String pathProject;
    private int tipo;

    public Email() {
        App.init();
        this.host = App.hostEmail;
        this.port = App.portEmail;
        this.username = App.usernameEmail;
        this.password = App.passwordEmail;
        this.config = obtenerConfiguracion();
    }

    public boolean recuperarCuenta(Persona persona) throws VotacionesException {
        this.persona = persona;
        return this.emailRecuperacionCuenta();
    }

    public boolean activarCuenta(Persona persona) throws VotacionesException {
        this.persona = persona;
        return this.emailActivarCuenta();
    }

    private JJMailProperties obtenerConfiguracion() {
        JJMailProperties configuracion = new JJMailProperties();
        configuracion.setHost(this.host);
        configuracion.setPort(this.port);
        configuracion.setUsername(this.username);
        configuracion.setPassword(this.password);
        return configuracion;
    }

    private boolean emailRecuperacionCuenta() throws VotacionesException {
        /**
         * Para que este módulo funcione, todas las imagenes deben estar
         * guardadas en una misma carpeta Ej:(images). El sistema también busca
         * una carpeta attachments, si también se desean adjuntar archivos sin
         * complicaciones junto a la plantilla solo se debe crear dicha
         * carpeta(attacments) y guardar allí todos los adjuntos.
         */
        try {
            this.config.init();
            this.config.setFrom(App.from);
            this.config.setFromName(App.fromName);
            this.config.setSubject("Recuperación de cuenta.");
            String[] addresss = {this.persona.getCorreo()};
            System.out.println(this.persona.getCorreo());
            this.config.setAddresses(addresss);
            String pathTemplate = EmailTemplates.RECUPERAR_CUENTA.getPath();
            this.config.setTemplate(new File(pathProject + pathTemplate));
            this.config.replace("#username#", this.persona.getPrimerNombre());
            this.config.replace("#URL#", App.urlApp + "updateaccount/" + Util.md5(this.persona.getCorreo()));
            this.config.replace("#urlapp#", App.urlApp);
            this.config.setHtml(true);
            JJMailSend ms = new JJMailSend();
            return ms.sendMail(this.config);
        } catch (Exception e) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
            throw new VotacionesException(EMensajes.ERROR_FATAL);
        }
    }

    private boolean emailActivarCuenta() throws VotacionesException {
        /**
         * Para que este módulo funcione, todas las imagenes deben estar
         * guardadas en una misma carpeta Ej:(images). El sistema también busca
         * una carpeta attachments, si también se desean adjuntar archivos sin
         * complicaciones junto a la plantilla solo se debe crear dicha
         * carpeta(attacments) y guardar allí todos los adjuntos.
         */
        try {
            this.config.init();
            this.config.setFrom(App.from);
            this.config.setFromName(App.fromName);
            this.config.setSubject("Cuenta activada.");
            String[] addresss = {this.persona.getCorreo()};
            this.config.setAddresses(addresss);
            String pathTemplate = EmailTemplates.CUENTA_ACTIVADA.getPath();
            this.config.setTemplate(new File(pathProject + pathTemplate));
            this.config.replace("#username#", this.persona.getPrimerNombre());
            this.config.replace("#URL#", App.urlApp + "login/");
            this.config.replace("#urlapp#", App.urlApp);
            this.config.setHtml(true);
            JJMailSend ms = new JJMailSend();
            return ms.sendMail(this.config);
        } catch (Exception e) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
            throw new VotacionesException(EMensajes.ERROR_FATAL);
        }
    }

    public void getPath() {
        File folderPath = new File(Email.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/");
        System.out.println(folderPath.getAbsolutePath());
    }

    public String getPathProject() {
        return pathProject;
    }

    public void setPathProject(String pathProject) {
        this.pathProject = pathProject;
    }

}
