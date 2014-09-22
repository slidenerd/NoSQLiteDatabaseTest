
package slidenerd.vivz.model;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

//Notice how we specified the name of the table below
@Table(name = "Person")
public class Person extends Model implements Serializable{

    // Notice how we specified the name of our column here
    @Column(name = "personName")
    public String personName;

    // Notice how we specified the name of our column here
    @Column(name = "personAge")
    public int personAge;

    @Column(name = "personScore", onDelete = Column.ForeignKeyAction.CASCADE)
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

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Name: "
                + personName
                + " Age: "
                + personAge
                + " "
                + personScore;
    }
}
