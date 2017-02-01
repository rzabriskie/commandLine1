package rsz.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by rzabrisk on 2/1/17.
 */
public class DefaultBatchPersister implements BatchPersistable {
    public static final Logger LOGGER = LogManager.getLogger(DefaultBatchPersister.class);

    private Connection connection;
    private Statement statement;

    public Connection getConnection() {
        if (connection == null) {
            setConnection(buildConnection());
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        if (statement == null) {
            setStatement(buildStatement());
        }
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    protected Connection buildConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost/postgres";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "p0stgr3sR$%8675309");
//        props.setProperty("ssl","true");
            conn = DriverManager.getConnection(url, props);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return conn;
    }

    protected Statement buildStatement() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }


    @Override
    public void addToBatch(String sqlInsert) {

        Statement statement = getStatement();
        try {
            statement.addBatch(sqlInsert);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void addToBatch(String name, String value) {
        addToBatch(String.format("insert into names values('%s','%s'", name, value));
    }

    @Override
    public void commitBatch() {

        try {
            getStatement().executeBatch();
            getConnection().commit();
            getStatement().close();
            setStatement(null);
        } catch (SQLException e) {
            LOGGER.error(e);
        }

    }

    @Override
    public void truncate(String tablename) {
        String sql = String.format("delete from %s;", tablename);
        Statement statement = getStatement();

        try {
            statement.executeUpdate(sql);
            getConnection().commit();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }
}
