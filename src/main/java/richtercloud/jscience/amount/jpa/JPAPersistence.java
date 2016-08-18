/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package richtercloud.jscience.amount.jpa;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.jscience.economics.money.Currency;
import org.jscience.physics.amount.Amount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows to create random databases and reuse them. Databases are created in
 * the directory specified in the {@code databaseDir} system property or in
 * a random directory in a temporary file storage if the it isn't specified.
 *
 * The database will have the name specified in the {@code databaseName} system
 * property or a random name if it isn't specified. If a database directory
 * exists it is reused, otherwise created.
 *
 * The JPA {@code javax.persistence.schema-generation.database.action} property
 * is controlled with the {@code action} system property and defaults to
 * {@code drop-and-create} if unspecified.
 *
 * @author richter
 */
public class JPAPersistence {
    private final static Logger LOGGER = LoggerFactory.getLogger(JPAPersistence.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String databaseDir = System.getProperty("databaseDir");
        File databaseDirFile;
        if(databaseDir == null) {
            databaseDirFile = File.createTempFile("prefix", "suffix");
            databaseDirFile.delete();
            databaseDirFile.mkdirs();
        }else {
            databaseDirFile = new File(databaseDir);
            if(!databaseDirFile.exists()) {
                databaseDirFile.mkdirs();
            }
        }
        LOGGER.info(String.format("database directory is: %s", databaseDirFile.getAbsolutePath()));
        if(!databaseDirFile.exists()) {
            databaseDirFile.mkdirs();
        }
        String databaseName = System.getProperty("databaseName");
            //allows resuage of database if databaseName system property is
            //specified
        File databaseFile;
        if(databaseName == null) {
            databaseFile = File.createTempFile("prefix", "suffix", databaseDirFile);
            databaseFile.delete();
            databaseFile.mkdir();
        } else {
            databaseFile = new File(databaseDirFile, databaseName);
            if(!databaseFile.exists()) {
                databaseFile.mkdir();
            }else if(!databaseFile.isDirectory()) {
                throw new IllegalArgumentException();
            }
        }
        String action = System.getProperty("drop", "drop-and-create");
        boolean needCreation = !databaseFile.exists() || (databaseFile.exists() && databaseFile.list().length == 0);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test", new HashMap() {
            {
                put("javax.persistence.jdbc.url",
                        String.format("jdbc:derby:memory:%s%s",
                                databaseFile.getAbsolutePath(),
                                needCreation ? ";create=true": ""));
                put("javax.persistence.jdbc.user",
                        "sa");
                put("javax.persistence.jdbc.driver",
                        "org.apache.derby.jdbc.EmbeddedDriver");
                put("javax.persistence.jdbc.password",
                        "");
                put("javax.persistence.schema-generation.database.action",
                        action);
            }
        });
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        A a = new A(1L, Amount.valueOf(1, Currency.EUR));
        entityManager.getTransaction().begin();
        entityManager.persist(a);
        entityManager.getTransaction().commit();
    }
}
