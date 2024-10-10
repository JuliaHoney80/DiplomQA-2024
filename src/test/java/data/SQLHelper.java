package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class SQLHelper {

  private static final QueryRunner QUERY_RUNNER = new QueryRunner();

  private SQLHelper() {
  }

  private static Connection getConn() throws SQLException {
    return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
  }

  @SneakyThrows
  public static void cleanDataBase() {
    Connection connection = getConn();
    QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
    QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
    QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");

  }

  @SneakyThrows
  public static String getOrderStatus(String statusType) {
    String leftJoinQuery = "select ord.id, pa.status as payment, cr.status as credit\n"
        + "from app.order_entity ord\n"
        + "         left join app.payment_entity pa on ord.payment_id = pa.transaction_id\n"
        + "         left join app.credit_request_entity cr on ord.payment_id = cr.bank_id;";

    Connection connection = getConn();
    List<Map<String, Object>> resultMap = QUERY_RUNNER.query(connection, leftJoinQuery, new MapListHandler());

    return resultMap.listIterator().next().get(statusType).toString();
  }

}

