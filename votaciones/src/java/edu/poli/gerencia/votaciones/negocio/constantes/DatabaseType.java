package edu.poli.gerencia.votaciones.negocio.constantes;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public enum DatabaseType {
    MYSQL(1, "mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://"),
    POSTGRES(2, "POSTGRES", "org.postgresql.Driver", "jdbc:postgress://"),
    SQL(3, "SQL", "org.sql.Driver", "jdbc:sql://"),
    SQLITE(4, "SQLITE", "org.sqlite.JDBC", "jdbc:sqlite:");

    private DatabaseType(int code, String description, String forName, String sentence) {
        this.code = code;
        this.description = description;
        this.forName = forName;
        this.sentence = sentence;
    }

    private int code;
    private String description;
    private String forName;
    private String sentence;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getForName() {
        return forName;
    }

    public String getSentence() {
        return sentence;
    }

}

