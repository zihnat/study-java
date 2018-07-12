package net.lessons.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private static final String TABLE_NAME = "cars";
    private Session session;

    public CarRepository(Session session){
        this.session = session;
    }

    public void createTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
          .append(TABLE_NAME).append("(")
          .append("id int PRIMARY KEY, ")
          .append("mark text,")
          .append("model text);");

        String query = sb.toString();
        session.execute(query);
    }

    /*
    public void alterTableCars(String columnName, String columnType) {
        StringBuilder sb = new StringBuilder("ALTER TABLE ")
          .append(TABLE_NAME).append(" ADD ")
          .append(columnName).append(" ")
          .append(columnType).append(";");

        String query = sb.toString();
        session.execute(query);
    }*/

    public void insertCar(Car car) {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
          .append(TABLE_NAME).append("(id, mark, model) ")
          .append("VALUES (").append(car.getId())
          .append(", '").append(car.getMark())
          .append("', '").append(car.getModel()).append("');");

        String query = sb.toString();
        session.execute(query);
    }

    public List<Car> selectAll() {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<Car> cars = new ArrayList<Car>();

        rs.forEach(r -> {
            cars.add(new Car(
              r.getInt("id"),
              r.getString("mark"),
              r.getString("model")));
        });
        return cars;
    }

    public Car selectById(int id) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME)
            .append(" WHERE id = ").append(id);

        String query = sb.toString();
        ResultSet rs = session.execute(query);
        Row row = rs.one();
        Car car = new Car(
              row.getInt("id"),
              row.getString("mark"),
              row.getString("model"));

        return car;
    }

    public void deleteCar(int id) {
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME)
            .append(" WHERE id = ").append(id).append(";");

        final String query = sb.toString();
        session.execute(query);
    }


    public void deleteTable() {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_NAME);

        final String query = sb.toString();
        session.execute(query);
    }

}
