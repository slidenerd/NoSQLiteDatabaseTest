
package slidenerd.vivz.nosqlite;

import java.util.ArrayList;
import java.util.Iterator;

import slidenerd.vivz.model.Person;
import slidenerd.vivz.model.Score;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SelectDeleteActivity extends Activity
        implements OnClickListener {

    private ListView mListData;
    private VivzListAdapter mAdapter;

    private EditText mEditTextPersonName;
    private EditText mEditTextPersonAge;

    private EditText mScorePhysics;
    private EditText mScoreChemistry;
    private EditText mScoreMaths;
    private EditText mScoreBiology;

    private Button mSelect;
    private Button mDelete;

    private class Query
    {
        String queryForPerson;
        String queryForScore;
        ArrayList<String> queryArgumentsForPerson;
        ArrayList<String> queryArgumentsForScore;

        public Query(String queryForPerson,
                String queryForScore,
                ArrayList<String> queryArgumentsForPerson,
                ArrayList<String> queryArgumentsForScore) {
            this.queryForPerson = queryForPerson;
            this.queryForScore = queryForScore;
            this.queryArgumentsForPerson = queryArgumentsForPerson;
            this.queryArgumentsForScore = queryArgumentsForScore;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delete);
        mListData = (ListView) findViewById(R.id.list_data);
        mAdapter = new VivzListAdapter(this);
        mListData.setAdapter(mAdapter);

        mEditTextPersonName = (EditText) findViewById(R.id.person_name);
        mEditTextPersonAge = (EditText) findViewById(R.id.person_age);

        mScorePhysics = (EditText) findViewById(R.id.score_physics);
        mScoreChemistry = (EditText) findViewById(R.id.score_chemistry);
        mScoreMaths = (EditText) findViewById(R.id.score_maths);
        mScoreBiology = (EditText) findViewById(R.id.score_biology);

        mSelect = (Button) findViewById(R.id.select);
        mDelete = (Button) findViewById(R.id.delete);

        mSelect.setOnClickListener(this);
        mDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.select:
                ArrayList<Person> people = select(buildQuery());
                if (people != null) {
                    mAdapter.setData(people);
                }
                break;
            case R.id.delete:
                delete(buildQuery());
                break;
        }

    }

    private String extractPersonName()
    {
        return mEditTextPersonName
                .getText()
                .toString();
    }

    private int extractPersonAge() {
        int personAge = 0;
        try {
            personAge = Integer.parseInt(
                    mEditTextPersonAge
                            .getText()
                            .toString());
        } catch (NumberFormatException e) {
            personAge = 0;
        }
        return personAge;
    }

    private int extractScoreFrom(EditText editText) {
        int score = -1;
        try {
            score = Integer.parseInt(
                    editText
                            .getText()
                            .toString());
        } catch (NumberFormatException e) {
            score = -1;
        }
        return score;
    }

    public Query buildQuery() {
        String and = "and ".intern();
        String personName = extractPersonName();
        int personAge = extractPersonAge();
        int scorePhysics = extractScoreFrom(mScorePhysics);
        int scoreChemistry = extractScoreFrom(mScoreChemistry);
        int scoreMaths = extractScoreFrom(mScoreMaths);
        int scoreBiology = extractScoreFrom(mScoreBiology);

        StringBuilder queryForPerson = new StringBuilder();
        ArrayList<String> queryArgumentsForPerson = new ArrayList<>();
        StringBuilder queryForScore = new StringBuilder();
        ArrayList<String> queryArgumentsForScore = new ArrayList<>();

        if (personName != null && personName.length() > 0) {
            queryForPerson.append("personName=? ")
                    .append(and);
            queryArgumentsForPerson.add(personName);
        }
        if (personAge > 0) {
            queryForPerson.append("personAge=? ")
                    .append(and);
            queryArgumentsForPerson.add(personAge + "");
        }
        if (scorePhysics >= 0) {
            queryForScore.append("scorePhysics=? ")
                    .append(and);
            queryArgumentsForScore.add(scorePhysics + "");
        }
        if (scoreChemistry >= 0) {
            queryForScore.append("scoreChemistry=? ")
                    .append(and);
            queryArgumentsForScore.add(scoreChemistry + "");
        }
        if (scoreMaths >= 0) {
            queryForScore.append("scoreMaths=? ")
                    .append(and);
            queryArgumentsForScore.add(scoreMaths + "");
        }
        if (scoreBiology >= 0) {
            queryForScore.append("scoreBiology=? ")
                    .append(and);
            queryArgumentsForScore.add(scoreBiology + "");
        }

        if (queryForPerson.length() > 0) {
            queryForPerson.delete(
                    queryForPerson.lastIndexOf(and),
                    queryForPerson.length());
        }
        if (queryForScore.length() > 0) {
            queryForScore.delete(
                    queryForScore.lastIndexOf(and),
                    queryForScore.length());
        }

        return new Query(
                queryForPerson.toString(),
                queryForScore.toString(),
                queryArgumentsForPerson,
                queryArgumentsForScore);
    }

    public ArrayList<Person> select(Query query) {

        if (query.queryForPerson.length() > 0
                && query.queryForScore.length() > 0) {

            ArrayList<Person> people = new Select()
                    .from(Person.class)
                    .where(query.queryForPerson,
                            query.queryArgumentsForPerson.toArray())
                    .execute();

            ArrayList<Score> scores = new Select()
                    .from(Score.class)
                    .where(query.queryForScore,
                            query.queryArgumentsForScore.toArray())
                    .execute();
            boolean matchFound = false;
            for (Iterator<Person> outsideIterator = people.iterator(); outsideIterator.hasNext();)
            {
                Person person = outsideIterator.next();
                matchFound = false;
                for (Score score : scores) {
                    if (person.personScore.getId() == score.getId()) {
                        matchFound = true;
                    }
                }
                if (!matchFound) {
                    outsideIterator.remove();
                }

            }
            return people;

        }
        else if (query.queryForPerson.length() > 0
                && query.queryForScore.length() == 0) {
            ArrayList<Person> people = new ArrayList<>();
            people = new Select()
                    .from(Person.class)
                    .where(query.queryForPerson,
                            query.queryArgumentsForPerson.toArray())
                    .execute();
            return people;

        }
        else if (query.queryForPerson.length() == 0
                && query.queryForScore.length() > 0) {
            ArrayList<Person> people = new Select()
                    .all()
                    .from(Person.class)
                    .execute();
            ArrayList<Score> scores = new Select()
                    .from(Score.class)
                    .where(query.queryForScore,
                            query.queryArgumentsForScore.toArray())
                    .execute();
            boolean matchFound = false;
            for (Iterator<Person> outsideIterator = people.iterator(); outsideIterator.hasNext();) {

                Person person = outsideIterator.next();
                matchFound = false;
                for (Score score : scores) {
                    if (person.personScore.getId() == score.getId()) {
                        matchFound = true;
                    }
                }
                if (!matchFound) {
                    outsideIterator.remove();
                }
            }
            return people;
        }
        return null;
    }

    public void delete(Query query) {
        ArrayList<Person> people = select(query);
        if (people != null && !people.isEmpty()) {
            ActiveAndroid.beginTransaction();
            try {
                for (Person person : people) {
                    person.delete();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
    }

}
