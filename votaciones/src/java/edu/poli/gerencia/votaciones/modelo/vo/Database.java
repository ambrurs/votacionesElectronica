package edu.poli.gerencia.votaciones.modelo.vo;

import edu.poli.gerencia.votaciones.negocio.constantes.DatabaseType;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class Database {

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private DatabaseType type;

    public Database(String host, String port, String database, String username, String password, DatabaseType type) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DatabaseType getType() {
        return type;
    }

    public void setType(DatabaseType type) {
        this.type = type;
    }

    public String getUrl() {
        return this.host
                + ":" + port
                + "/" + database
                + "";
    }

    public String toString() {
        return "Database: " + this.database
                + "Host: " + this.host
                + "Port: " + this.port
                + "Username: " + this.username
                + "Password: " + this.password
                + "Type: " + this.type.getDescription()
                + "";
    }
}
