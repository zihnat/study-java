package net.lessons.cassandra.test;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import java.util.List;
import java.util.stream.Collectors;
import net.lessons.cassandra.Car;
import net.lessons.cassandra.Connector;
import net.lessons.cassandra.KeyspaceRepository;
import net.lessons.cassandra.CarRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class CarRepositoryTest{
    private KeyspaceRepository schemaRepository;
    private CarRepository carRepository;
    private Session session;
    private String KEYSPACE_NAME = "my_keyspace01";
    private String TABLE_NAME = "cars";

    @Before
    public void connect() {
        Connector client = new Connector();
        client.connect("127.0.0.1", 9042);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);
        schemaRepository.createKeyspace(KEYSPACE_NAME, "SimpleStrategy", 1);
        schemaRepository.useKeyspace(KEYSPACE_NAME);
        carRepository = new CarRepository(session);
    }

    @Test
    public void whenCreatingATable_thenCreatedCorrectly() {
        schemaRepository.useKeyspace(KEYSPACE_NAME);
        carRepository.createTable();

        ResultSet result = session.execute(
          "SELECT * FROM " + TABLE_NAME + ";");

        List<String> columnNames =
          result.getColumnDefinitions().asList().stream()
          .map(cl -> cl.getName())
          .collect(Collectors.toList());

        assertEquals(columnNames.size(), 3);
        assertTrue(columnNames.contains("id"));
        assertTrue(columnNames.contains("mark"));
        assertTrue(columnNames.contains("model"));
    }

    @Test
    public void whenAddingCar_thenCarExists() {
        carRepository.deleteTable();
        carRepository.createTable();
        String mark = "Lada";
        String model = "Kalina";
        Car car = new Car(1, mark, model);
        carRepository.insertCar(car);

        Car createdCar = carRepository.selectById(1);
        assertEquals(car.getId(), createdCar.getId());
        assertEquals(car.getMark(), createdCar.getMark());
        assertEquals(car.getModel(), createdCar.getModel());
    }

    @Test
    public void whenSelectingAll_thenReturnAllRecords() {
        carRepository.deleteTable();
        carRepository.createTable();

        Car car = new Car(1, "VW", "Bug");
        carRepository.insertCar(car);

        car = new Car(2, "BMW", "A7");
        carRepository.insertCar(car);

        List<Car> cars = carRepository.selectAll();

        assertEquals(2, cars.size());
        assertTrue(cars.stream().anyMatch(b -> b.getMark().equals("VW")));
        assertTrue(cars.stream().anyMatch(b -> b.getMark().equals("BMW")));
    }


    @Test(expected = InvalidQueryException.class)
    public void whenDeletingATable_thenUnconfiguredTable() {
        carRepository.createTable();
        carRepository.deleteTable();

        session.execute("SELECT * FROM " + TABLE_NAME + ";");
    }

}
