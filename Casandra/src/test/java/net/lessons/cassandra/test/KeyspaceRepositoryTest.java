package net.lessons.cassandra.test;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import java.util.List;
import java.util.stream.Collectors;
import net.lessons.cassandra.Connector;
import net.lessons.cassandra.KeyspaceRepository;
import net.lessons.cassandra.CarRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class KeyspaceRepositoryTest{
    private KeyspaceRepository schemaRepository;
    private CarRepository carRepository;
    private Session session;
    private String KEYSPACE_NAME = "my_keyspace01";

    @Before
    public void connect() {
        Connector client = new Connector();
        client.connect("127.0.0.1", 9042);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);
        carRepository = new CarRepository(session);
    }

    @Test
    public void whenCreateKeyspace_thenIsCreated() {
        schemaRepository.createKeyspace(KEYSPACE_NAME, "SimpleStrategy", 1);

        ResultSet result =
          session.execute("SELECT * FROM system_schema.keyspaces;");

        List<String> matchedKeyspaces = result.all()
          .stream()
          .filter(r -> r.getString(0).equals(KEYSPACE_NAME.toLowerCase()))
          .map(r -> r.getString(0))
          .collect(Collectors.toList());

        assertEquals(matchedKeyspaces.size(), 1);
        assertTrue(matchedKeyspaces.get(0).equals(KEYSPACE_NAME.toLowerCase()));
    }

    @Test
    public void whenDeleteKeyspace_thenDoesNotExist() {
        String keyspaceName = "testBaeldungKeyspace";

        schemaRepository.createKeyspace(KEYSPACE_NAME, "SimpleStrategy", 1);
        schemaRepository.deleteKeyspace(KEYSPACE_NAME);

        ResultSet result = session.execute("SELECT * FROM system_schema.keyspaces;");
        boolean isKeyspaceCreated = result.all().stream().anyMatch(r -> r.getString(0).equals(keyspaceName.toLowerCase()));
        assertFalse(isKeyspaceCreated);
    }

}
