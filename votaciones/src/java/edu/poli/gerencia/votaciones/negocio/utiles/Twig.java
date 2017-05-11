/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votaciones.negocio.utiles;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author jhon1
 */
public class Twig {
    
    private static String sContext;
    
    public static String load(String file) {
        try {
            String content = "", linea;
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            while ((linea = in.readLine()) != null) {
                content += linea;
            }
            in.close();
            sContext = content;
            return content;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(Twig.load("C:\\Users\\jhon1\\Documents\\NetBeansProjects\\votacionesapp\\votacionesElectronica\\votaciones\\web\\dashboard_page.html"));
    }
}
