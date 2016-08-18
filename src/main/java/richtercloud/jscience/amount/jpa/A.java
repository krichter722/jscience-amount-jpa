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

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.jscience.economics.money.Money;
import org.jscience.physics.amount.Amount;

/**
 *
 * @author richter
 */
@Entity
public class A implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Basic
    private Amount<Money> amount;

    protected A() {
    }

    public A(Long id, Amount<Money> amount) {
        this.id = id;
        this.amount = amount;
    }


    public void setAmount(Amount<Money> amount) {
        this.amount = amount;
    }

    public Amount<Money> getAmount() {
        return amount;
    }
    
}
