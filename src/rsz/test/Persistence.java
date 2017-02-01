package rsz.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Properties;

/**
 * Created by rzabrisk on 2/1/17.
 */
public class Persistence {

    public static final Logger LOGGER = LogManager.getLogger(Persistence.class);

    public static void test() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }

        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "p0stgr3sR$%8675309");
//        props.setProperty("ssl","true");
        try {
            Connection conn = DriverManager.getConnection(url, props);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM names WHERE firstname = 'rob'");
            while (rs.next()) {
                LOGGER.info("Column 1 returned ");
                LOGGER.info(rs.getString(1));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }
}
