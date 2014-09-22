
package slidenerd.vivz.nosqlite;

import java.util.ArrayList;

import com.activeandroid.query.Select;

import slidenerd.vivz.model.Person;
import slidenerd.vivz.model.Score;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    public static final String EXTRA_ALL = "slidenerd.vivz.nosqlite.extra.all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void showAll(View view)
    {
        // Create an object of Select to issue a select query

        // Call select.all() to select all rows from our table which is
        // represented by Person.class and execute the query.

        // It returns an ArrayList of our Person objects where each object
        // contains data corresponding to a row of our database.

        ArrayList<Person> people = new Select()
                .all()
                .from(Person.class)
                .execute();
        Intent intentShowAll = new Intent(this, DisplayAllActivity.class);
        intentShowAll.putExtra(EXTRA_ALL, people);
        startActivity(intentShowAll);
    }

    public void insert(View view)
    {
        DialogInsert dialogInsert = new DialogInsert();
        dialogInsert.show(getSupportFragmentManager(), "DialogInsert");
    }

    public void bulkInsert(View view)
    {
        DialogBulkInsert dialogBulkInsert = new DialogBulkInsert();
        dialogBulkInsert.show(getSupportFragmentManager(), "DialogBulkInsert");
    }

    public void selectUpdateDelete(View view) {
        Intent intent = new Intent(this, SelectDeleteActivity.class);
        startActivity(intent);
    }

}
