
package slidenerd.vivz.nosqlite;

import java.util.ArrayList;

import com.activeandroid.query.Select;

import slidenerd.vivz.model.Person;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    EditText personNameEditText;
    EditText personAgeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personNameEditText = (EditText) findViewById(R.id.person_name);
        personAgeEditText = (EditText) findViewById(R.id.person_age);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void save(View view)
    {
        String name = personNameEditText.getText().toString();
        int age = Integer.parseInt(personAgeEditText.getText().toString());

        Person person = new Person(name, age);
        person.save();
    }

    public void showAll(View view)
    {
        // Create an object of Select to issue a select query

        Select select = new Select();

        // Call select.all() to select all rows from our table which is
        // represented by Person.class and execute the query.

        // It returns an ArrayList of our Person objects where each object
        // contains data corresponding to a row of our database.

        ArrayList<Person> people = select.all().from(Person.class).execute();

        // Iterate through the ArrayList to get all our data. We ll simply add
        // all the data to our StringBuilder to display it inside a Toast.

        StringBuilder builder = new StringBuilder();
        for (Person person : people) {
            builder.append("Name: ")
                    .append(person.personName)
                    .append(" Age: ")
                    .append(person.personAge)
                    .append("\n");
        }

        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();

    }
}
