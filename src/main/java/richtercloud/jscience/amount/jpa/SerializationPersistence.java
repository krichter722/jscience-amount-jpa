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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.jscience.economics.money.Currency;
import org.jscience.physics.amount.Amount;

/**
 *
 * @author richter
 */
public class SerializationPersistence {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        A a = new A(2L, Amount.valueOf(3.0, Currency.EUR));
        File outputFile = File.createTempFile("prefix", "suffix");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(a);
            objectOutputStream.flush();
        }
        FileInputStream fileInputStream = new FileInputStream(outputFile);
        A aReloaded;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            aReloaded = (A) objectInputStream.readObject();
        }
        System.out.println(aReloaded.toString());
    }
}
