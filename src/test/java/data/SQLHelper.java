package data;

import io.qameta.allure.Step;
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

  private static Connection getConn(String dbUrlProperty) throws SQLException {
    return DriverManager.getConnection(dbUrlProperty, "app", "pass");
  }

  @SneakyThrows
  @Step("Очистить БД mysql")
  public static void cleanMysqlDataBase() {
    Connection connection = getConn(System.getProperty("db.mysql.url"));
    QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
    QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
    QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
  }

  @SneakyThrows
  @Step("Очистить БД postgres")
  public static void cleanPostgresqlDataBase() {
    Connection connection = getConn(System.getProperty("db.postgres.url"));
    QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
    QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
    QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
  }

  @Step("Получить статус платежа из БД mysql")
  @SneakyThrows
  public static String getOrderStatusFromMysql(String statusType) {
    String leftJoinQuery = "select ord.id, pa.status as payment, cr.status as credit\n"
        + "from app.order_entity ord\n"
        + "         left join app.payment_entity pa on ord.payment_id = pa.transaction_id\n"
        + "         left join app.credit_request_entity cr on ord.payment_id = cr.bank_id;";

    Connection connection = getConn(System.getProperty("db.mysql.url"));
    List<Map<String, Object>> resultMap = QUERY_RUNNER.query(connection, leftJoinQuery, new MapListHandler());

    return resultMap.listIterator().next().get(statusType).toString();
  }

  @Step("Получить статус платежа из БД postgres")
  @SneakyThrows
  public static String getOrderStatusFromPostgresql(String statusType) {
    String leftJoinQuery = "select ord.id, pa.status as payment, cr.status as credit\n"
        + "from app.public.order_entity ord\n"
        + "         left join app.public.payment_entity pa on ord.payment_id = pa.transaction_id\n"
        + "         left join app.public.credit_request_entity cr on ord.payment_id = cr.bank_id;";

    Connection connection = getConn(System.getProperty("db.postgres.url"));
    List<Map<String, Object>> resultMap = QUERY_RUNNER.query(connection, leftJoinQuery, new MapListHandler());

    return resultMap.listIterator().next().get(statusType).toString();
  }

}

