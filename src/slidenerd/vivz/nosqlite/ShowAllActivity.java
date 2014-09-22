
package slidenerd.vivz.nosqlite;

import java.util.ArrayList;
import java.util.Iterator;

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

public class ShowAllActivity extends Activity
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
        initListWithData();
    }

    private void initListWithData() {
        ArrayList<Person> people = new Select()
                .all()
                .from(Person.class)
                .execute();
        mAdapter = new VivzListAdapter(this);
        mAdapter.setData(people);
        mListAll.setAdapter(mAdapter);
        mListAll.setEmptyView(mTextEmptyList);
        mListAll.setOnItemClickListener(this);

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
        Person person = mAdapter.getItem(position);

        if (person != null) {
            View dialogView = mInflater.inflate(R.layout.dialog_insert, null);
            initDialog(dialogView);
            AlertDialog dialog = showDialog(dialogView);
            setValuesUpdateDialog(person);
            mButtonSave.setTag(R.id.object_person, person);
            mButtonSave.setTag(R.id.object_dialog, dialog);

        }
    }

    public AlertDialog showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(null)
                .setView(view);
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
