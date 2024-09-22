package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.DayOfWeek;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private SQLHelper(){
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }
    @SneakyThrows
    public static void cleanDataBase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entry");
        QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");

    }
@SneakyThrows
    public static String DebitCardStatus(){
    var statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
    var amountSQL = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1";
    var connection = getConn();
    var status = QUERY_RUNNER.query(connection, statusSQL, new ScalarHandler<String>());
    var amount = QUERY_RUNNER.query(connection, amountSQL, new ScalarHandler<String>());
    return new DataHelper.Card(status, amount);
    }
@SneakyThrows
    public static String CreditCardStatus(){
    var statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
    var amountSQL = "SELECT amount FROM credit_request_entity ORDER BY created DESC LIMIT 1";
    var connection = getConn();
    var status = QUERY_RUNNER.query(connection, statusSQL, new ScalarHandler<String>());
    var amount = QUERY_RUNNER.query(connection, amountSQL, new ScalarHandler<String>());
    return new DataHelper.Card(status, amount);
}
    }

