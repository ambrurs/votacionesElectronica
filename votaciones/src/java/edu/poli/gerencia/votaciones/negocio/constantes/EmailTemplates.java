/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this path file, choose Tools | Templates
 * and open the path in the editor.
 */
package edu.poli.gerencia.votaciones.negocio.constantes;

import java.io.File;

/**
 *
 * @author jhon1
 */
public enum EmailTemplates {
    RECUPERAR_CUENTA("pages" + File.separator + "emailtemplates" + File.separator + "account" + File.separator + "recoveraccount.html"),
    CUENTA_ACTIVADA("pages" + File.separator + "emailtemplates" + File.separator + "account" + File.separator + "accountactived.html"),;
    private String path;

    private EmailTemplates(String template) {
        this.path = template;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
