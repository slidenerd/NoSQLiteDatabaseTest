
package slidenerd.vivz.nosqlite;

import slidenerd.vivz.model.Person;
import slidenerd.vivz.model.Score;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DialogInsert extends DialogFragment implements OnClickListener {
    EditText mEditTextPersonName;
    EditText mEditTextPersonAge;
    EditText mEditTextScorePhysics;
    EditText mEditTextScoreChemistry;
    EditText mEditTextScoreMaths;
    EditText mEditTextScoreBiology;
    Button mButtonSave;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View layout = inflater.inflate(R.layout.dialog_insert, null);
        mEditTextPersonName = (EditText) layout
                .findViewById(R.id.person_name);
        mEditTextPersonAge = (EditText) layout
                .findViewById(R.id.person_age);

        mEditTextScorePhysics = (EditText) layout
                .findViewById(R.id.score_physics);
        mEditTextScoreChemistry = (EditText) layout
                .findViewById(R.id.score_chemistry);
        mEditTextScoreMaths = (EditText) layout
                .findViewById(R.id.score_maths);
        mEditTextScoreBiology = (EditText) layout
                .findViewById(R.id.score_biology);
        mButtonSave = (Button) layout.findViewById(R.id.save);
        mButtonSave.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.save:
                save();
                break;
        }
    }

    public void save()
    {

        // Create a new score object with the 4 peices of data extracted from
        // their respective EditText and converted to an Integer
        try {
            Score score = new Score(
                    Integer
                            .parseInt(mEditTextScorePhysics
                                    .getText()
                                    .toString())
                    , Integer
                            .parseInt(mEditTextScoreChemistry
                                    .getText()
                                    .toString())
                    , Integer
                            .parseInt(mEditTextScoreMaths
                                    .getText()
                                    .toString())
                    , Integer
                            .parseInt(mEditTextScoreBiology
                                    .getText()
                                    .toString()));

            // Save the score object to its own table first.
            score.save();

            String name = mEditTextPersonName.getText().toString();
            int age = Integer
                    .parseInt(mEditTextPersonAge
                            .getText()
                            .toString());

            // Create a Person object with its details and the score object
            Person person = new Person(name, age, score);

            // Save the Person object
            person.save();
            
        } catch (NumberFormatException e) {
            Log.e(MyApplication.TAG,
                    "Error while converting String to Integer");
        }
        dismiss();
    }
}
