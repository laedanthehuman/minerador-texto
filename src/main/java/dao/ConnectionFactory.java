package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by manoel on 29/10/15.
 */
public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;
    private Connection connection;
    private  Properties prop;

    private ConnectionFactory() {
        String arquivoBanco = choiseDBForConnection("ic");
        loadResourcesFromFile(arquivoBanco);
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"),
                    prop.getProperty("password"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getConnectionFactory() {
        if (connectionFactory == null) {
            return new ConnectionFactory();
        }
        return connectionFactory;
    }

    public Connection getConnection() {
        return connection;
    }

    private  String choiseDBForConnection(String db) {
        switch (db) {
            case "IC":
                return "icDB.properties";
            case "Jano":
                return "db_connect_jano.properties";
            case "ic":
                return "icDB.properties";
            case "jano":
                return "db_connect_jano.properties";
            default:
                break;
        }
        return "";

    }

    private  void loadResourcesFromFile(String arquivo) {
        prop = new Properties();
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(arquivo);) {
            if (input == null) {
                System.out.println("Não foi possivel encontrar o arquivo de conexão " + arquivo);
                return;
            }

            prop.load(input);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

    }


}
