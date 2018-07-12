package net.lessons.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.ArrayList;
import java.util.List;

public class KeyspaceRepository {
    private Session session;

    public KeyspaceRepository(Session session){
        this.session = session;
    }

    public void createKeyspace(String keyspaceName, String replicationStrategy, int replicationFactor) {
        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
          .append(keyspaceName).append(" WITH replication = {")
          .append("'class':'").append(replicationStrategy)
          .append("','replication_factor':").append(replicationFactor)
          .append("};");

        String query = sb.toString();
        session.execute(query);
    }

    public void useKeyspace(String keyspace) {
        session.execute("USE " + keyspace);
    }

    public void deleteKeyspace(String keyspaceName) {
        StringBuilder sb = new StringBuilder("DROP KEYSPACE ").append(keyspaceName);

        final String query = sb.toString();

        session.execute(query);
    }

}
