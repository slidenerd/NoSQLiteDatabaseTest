
package slidenerd.vivz.nosqlite;

import java.util.ArrayList;

import com.activeandroid.query.Select;

import slidenerd.vivz.model.Person;
import slidenerd.vivz.model.Score;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayAllActivity extends Activity
        implements OnItemClickListener, OnClickListener {

    /**
     * The ListView which will display all the Person objects from the database
     */
    private ListView mListAll;

    /**
     * The Adapter which will store all the Person objects from the database.
     */
    private VivzListAdapter mAdapter;

    /**
     * The TextView containing the message to be displayed to the user when the
     * ListView is Empty
     */
    private TextView mTextEmptyList;
    private LayoutInflater mInflater;

    /**
     * The View object containing a reference to a layout file which displays
     * the Update Dialog when A ListItem is clicked
     */
    private View mDialogUpdateView;

    /**
     * The EditText containing Data when the user tries to perform a update
     * after clicking a ListItem.
     */
    private EditText mTextPersonName;
    private EditText mTextPersonAge;
    private EditText mTextScorePhysics;
    private EditText mTextScoreChemistry;
    private EditText mTextScoreMaths;
    private EditText mTextScoreBiology;
    private Button mButtonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        mListAll = (ListView) findViewById(R.id.list_all);
        mTextEmptyList = (TextView) findViewById(R.id.list_empty_message);
        mInflater = LayoutInflater.from(this);
        mDialogUpdateView = mInflater.inflate(R.layout.dialog_insert, null);

        // Get the Intent that was responsible to launch this Activity.
        Intent intentCaller = getIntent();

        // Get the data from this Intent which contains a list of Person objects
        // to be displayed inside this ListView
        initListWithData(intentCaller);

        // Initialize the 'Update Dialog' View
        initDialog(mDialogUpdateView);
    }

    private void initListWithData(Intent intent) {
        if (intent != null) {
            ArrayList<Person> people = (ArrayList<Person>) intent
                    .getSerializableExtra(MainActivity.EXTRA_ALL);
            mAdapter = new VivzListAdapter(this);
            mAdapter.setData(people);
            mListAll.setAdapter(mAdapter);
            mListAll.setEmptyView(mTextEmptyList);
            mListAll.setOnItemClickListener(this);

        }
    }

    private void initDialog(View view) {
        mTextPersonName = (EditText) view
                .findViewById(R.id.person_name);
        mTextPersonAge = (EditText) view
                .findViewById(R.id.person_age);
        mTextScorePhysics = (EditText) view
                .findViewById(R.id.score_physics);
        mTextScoreChemistry = (EditText) view
                .findViewById(R.id.score_chemistry);
        mTextScoreMaths = (EditText) view
                .findViewById(R.id.score_maths);
        mTextScoreBiology = (EditText) view
                .findViewById(R.id.score_biology);
        mButtonSave = (Button) view
                .findViewById(R.id.save);
        mButtonSave.setOnClickListener(this);
    }

    /**
     * Set values for each View inside the Dialog from the Person object
     * containing the corresponding values.
     * 
     * @param values
     */
    private void setValuesUpdateDialog(Person values) {
        mTextPersonName.setText(values.personName);
        mTextPersonAge.setText(values.personAge + "");
        mTextScorePhysics.setText(values.personScore.scorePhysics + "");
        mTextScoreChemistry.setText(values.personScore.scoreChemistry + "");
        mTextScoreMaths.setText(values.personScore.scoreMaths + "");
        mTextScoreBiology.setText(values.personScore.scoreBiology + "");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Person person = getDataFromView(view);

        if (person != null) {
            AlertDialog dialog = showDialog();
            setValuesUpdateDialog(person);
            mButtonSave.setTag(R.id.object_person, person);
            mButtonSave.setTag(R.id.object_dialog, dialog);

        }
    }

    /**
     * Extract data from the currently selected row of the ListView and convert
     * this data into a Person object for further processing.
     * 
     * @param view
     * @return
     */
    public Person getDataFromView(View view) {
        String personName = ((TextView) view
                .findViewById(R.id.text_name_value))
                .getText()
                .toString();
        int personAge = Integer
                .parseInt(((TextView) view
                        .findViewById(R.id.text_age_value))
                        .getText()
                        .toString());
        int scorePhysics = Integer
                .parseInt(((TextView) view
                        .findViewById(R.id.text_physics_value))
                        .getText()
                        .toString());
        int scoreChemistry = Integer
                .parseInt(((TextView) view
                        .findViewById(R.id.text_chemistry_value))
                        .getText()
                        .toString());
        int scoreMaths = Integer
                .parseInt(((TextView) view
                        .findViewById(R.id.text_maths_value))
                        .getText()
                        .toString());
        int scoreBiology = Integer
                .parseInt(((TextView) view
                        .findViewById(R.id.text_biology_value))
                        .getText()
                        .toString());

        ArrayList<Score> scores = new Select("Id")
                .from(Score.class)
                .where("scorePhysics=? "
                        + "AND scoreChemistry=? "
                        + "AND scoreMaths=? "
                        + "AND scoreBiology=?",
                        scorePhysics,
                        scoreChemistry,
                        scoreMaths,
                        scoreBiology)
                .execute();
        ArrayList<Person> people = new Select()
                .from(Person.class)
                .where("personName=? "
                        + "AND personAge=?",
                        personName,
                        personAge)
                .execute();
        for (Score score : scores) {
            for (Person person : people) {
                if (person.personScore.getId() != score.getId()) {
                    people.remove(person);
                }
            }
        }
        return people != null
                && people.size() > 0
                ? people.get(0)
                : null;
    }

    public AlertDialog showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(null)
                .setView(mDialogUpdateView);
        return builder.show();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.save:
                Person person = (Person) v.getTag(R.id.object_person);
                AlertDialog dialog = (AlertDialog) v.getTag(R.id.object_dialog);
                if (person != null) {
                    try {
                        person.personName = mTextPersonName
                                .getText()
                                .toString();

                        person.personAge = Integer.parseInt(mTextPersonAge
                                .getText()
                                .toString());
                        person.personScore.scorePhysics =
                                Integer.parseInt(mTextScorePhysics
                                        .getText()
                                        .toString());
                        person.personScore.scoreChemistry =
                                Integer.parseInt(mTextScoreChemistry
                                        .getText()
                                        .toString());
                        person.personScore.scoreMaths =
                                Integer.parseInt(mTextScoreMaths
                                        .getText()
                                        .toString());
                        person.personScore.scoreBiology =
                                Integer.parseInt(mTextScoreBiology
                                        .getText()
                                        .toString());
                        person.save();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        mAdapter.notifyDataSetChanged();
                        
                    }

                    catch (NumberFormatException e) {
                        Log.e(MyApplication.TAG,
                                "Error converting String to Integer");
                    }

                }
                break;
        }

    }
}
