
package slidenerd.vivz.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

//Notice how we specified the name of the table below
@Table(name = "Person")
public class Person extends Model {

    // Notice how we specified the name of our column here
    @Column(name = "personName")
    public String personName;

    // Notice how we specified the name of our column here
    @Column(name = "personAge")
    public int personAge;

    @Column(name = "personScore")
    public Score personScore;

    public Person() {
        // Notice how super() has been called to perform default initialization
        // of our Model subclass
        super();
    }

    public Person(String personName, int personAge, Score personScore) {
        super();
        this.personName = personName;
        this.personAge = personAge;
        this.personScore = personScore;
    }
}
