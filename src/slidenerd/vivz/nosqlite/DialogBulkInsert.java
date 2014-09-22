
package slidenerd.vivz.nosqlite;

import java.util.ArrayList;

import com.activeandroid.ActiveAndroid;

import slidenerd.vivz.model.Person;
import slidenerd.vivz.model.Score;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class DialogBulkInsert extends DialogFragment implements OnClickListener {

    /**
     * Assign TAGS to each dynamically added UI element so that data can be
     * retrieved from all of them
     */
    public static final String TAG_NAME = "name";
    public static final String TAG_AGE = "age";
    public static final String TAG_SCORE = "score";
    public static final String TAG_PHYSICS = "physics";
    public static final String TAG_CHEMISTRY = "chemistry";
    public static final String TAG_MATHS = "maths";
    public static final String TAG_BIOLOGY = "biology";

    /**
     * The key used to store the number of student rows currently visible on our
     * UI used to restore the UI after an orientation change.
     */
    private static final String sKeyCountStudent = "countStudent";

    private Context mContext;
    /**
     * The vertical LinearLayout from our dialog's XML file which acts as the
     * root for all our dynamically added children
     */
    private LinearLayout mLayoutOuter;

    /**
     * The Button responsible for adding all the dynamic controls that let you
     * add a new student on our UI
     */
    private Button mButtonAddStudent;

    /**
     * The Button responsible for performing bulk Insert by extracting data from
     * all the dynamic rows
     */
    private Button mButtonBulkInsert;
    private int mCountRows = 0;

    private ArrayList<String> mListNames = new ArrayList<>();
    private ArrayList<String> mListAges = new ArrayList<>();
    private ArrayList<String> mListScoresPhysics = new ArrayList<>();
    private ArrayList<String> mListScoresChemistry = new ArrayList<>();
    private ArrayList<String> mListScoresMaths = new ArrayList<>();
    private ArrayList<String> mListScoresBiology = new ArrayList<>();

    private int mId = 0;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mContext = activity;
    }

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
        View layout = inflater
                .inflate(R.layout.dialog_bulk_insert, null);
        mLayoutOuter = (LinearLayout) layout
                .findViewById(R.id.linear_layout_bulk_insert);
        mButtonAddStudent = (Button) layout.findViewById(R.id.new_student);
        mButtonAddStudent.setOnClickListener(this);
        mButtonBulkInsert = (Button) layout.findViewById(R.id.add);
        mButtonBulkInsert.setOnClickListener(this);

        // If this Dialog is being launched after an orientation change, restore
        // all the previously dynamically created View elements.
        if (savedInstanceState != null) {
            mCountRows = savedInstanceState
                    .getInt(sKeyCountStudent);
            restoreUI();
        }
        return layout;
    }

    private void restoreUI() {
        mButtonBulkInsert.setVisibility(mCountRows > 0 ? View.VISIBLE : View.GONE);
        for (int i = 0; i < mCountRows; i++) {
            mLayoutOuter.addView(
                    createEditTextPersonName(mLayoutOuter));
            mLayoutOuter.addView(
                    createEditTextPersonAge(mLayoutOuter));
            mLayoutOuter.addView(
                    createEditTextPCMB(mLayoutOuter));
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.new_student:
                mLayoutOuter.addView(
                        createEditTextPersonName(
                        mLayoutOuter));
                mLayoutOuter.addView(
                        createEditTextPersonAge(mLayoutOuter));
                mLayoutOuter.addView(
                        createEditTextPCMB(mLayoutOuter));
                mCountRows++;
                mButtonBulkInsert.setVisibility(mCountRows > 0 ? View.VISIBLE : View.GONE);
                break;
            case R.id.add:
                extractValues();
                insertValues();
                dismiss();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(arg0);
        arg0.putInt(sKeyCountStudent, mCountRows);
    }

    private void extractValues() {

        for (int i = 0; i < mLayoutOuter.getChildCount(); i++) {
            View outerCurrent = mLayoutOuter.getChildAt(i);
            String name = null, age = null, physics = null, chemistry = null, maths = null, biology = null;
            if (outerCurrent instanceof EditText) {
                if (TAG_NAME.equals(outerCurrent.getTag())) {
                    EditText editTextName = (EditText) outerCurrent;
                    name = editTextName.getText().toString();
                    mListNames.add(name);
                }
                if (TAG_AGE.equals(outerCurrent.getTag())) {
                    EditText editTextAge = (EditText) outerCurrent;
                    age = editTextAge.getText().toString();
                    mListAges.add(age);
                }
            }
            if (outerCurrent instanceof LinearLayout) {
                LinearLayout innerLayout = (LinearLayout) outerCurrent;
                for (int j = 0; j < innerLayout.getChildCount(); j++) {
                    View innerCurrent = innerLayout.getChildAt(j);
                    if (innerCurrent instanceof EditText) {
                        if (TAG_PHYSICS.equals(innerCurrent.getTag())) {
                            EditText editTextPhysics = (EditText) innerCurrent;
                            physics = editTextPhysics.getText().toString();
                            mListScoresPhysics.add(physics);
                        }
                        if (TAG_CHEMISTRY.equals(innerCurrent.getTag())) {
                            EditText editTextChemistry = (EditText) innerCurrent;
                            chemistry = editTextChemistry.getText().toString();
                            mListScoresChemistry.add(chemistry);
                        }
                        if (TAG_MATHS.equals(innerCurrent.getTag())) {
                            EditText editTextMaths = (EditText) innerCurrent;
                            maths = editTextMaths.getText().toString();
                            mListScoresMaths.add(maths);
                        }
                        if (TAG_BIOLOGY.equals(innerCurrent.getTag())) {
                            EditText editTextBiology = (EditText) innerCurrent;
                            biology = editTextBiology.getText().toString();
                            mListScoresBiology.add(biology);
                        }
                    }
                }
            }

        }
    }

    public void insertValues() {
        if (mListNames.isEmpty()
                || mListAges.isEmpty()
                || mListScoresPhysics.isEmpty()
                || mListScoresChemistry.isEmpty()
                || mListScoresMaths.isEmpty()
                || mListScoresBiology.isEmpty()) {
            return;
        }
        ArrayList<Person> listPeople = new ArrayList<>();
        for (int i = 0; i < mListNames.size(); i++) {
            try
            {
                Score currentScore = new Score(
                        Integer.parseInt(mListScoresPhysics.get(i)),
                        Integer.parseInt(mListScoresChemistry.get(i)),
                        Integer.parseInt(mListScoresMaths.get(i)),
                        Integer.parseInt(mListScoresBiology.get(i)));
                Person currentPerson =
                        new Person(mListNames.get(i),
                                Integer.parseInt(mListAges.get(i)),
                                currentScore);
                listPeople.add(currentPerson);
            } catch (NumberFormatException e) {
                Log.e(MyApplication.TAG, "Error converting String to Integer");
            }
        }

        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < listPeople.size(); i++) {
                Person current = listPeople.get(i);
                current.personScore.save();
                current.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    private EditText createEditTextPersonName(LinearLayout parent) {
        EditText personName = new EditText(mContext);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);
        personName.setLayoutParams(params);
        personName.setTag(TAG_NAME);
        personName.setEms(15);
        personName.setId(mId++);
        personName.setBackgroundResource(R.drawable.custom_bg_edit_text);
        personName.setHint(R.string.hint_person_name);
        personName.setTextColor(Color.parseColor("#F39C12"));
        return personName;
    }

    private EditText createEditTextPersonAge(LinearLayout parent) {
        EditText personAge = new EditText(mContext);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);
        personAge.setLayoutParams(params);
        personAge.setTag(TAG_AGE);
        personAge.setId(mId++);
        personAge.setBackgroundResource(R.drawable.custom_bg_edit_text);
        personAge.setHint(R.string.hint_person_age);
        personAge.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        personAge.setTextColor(Color.parseColor("#F39C12"));
        return personAge;
    }

    private LinearLayout createEditTextPCMB(LinearLayout parent) {
        LinearLayout innerLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        innerLayout.setLayoutParams(params);
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        innerLayout.setWeightSum(4.0F);
        innerLayout.setTag(TAG_SCORE);

        LinearLayout.LayoutParams layoutParamsChildren =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0F);

        EditText physics = createEditTextPhysics(
                innerLayout,
                layoutParamsChildren);

        EditText chemistry = createEditTextChemistry(
                innerLayout,
                layoutParamsChildren);

        EditText maths = createEditTextMaths(
                innerLayout,
                layoutParamsChildren);

        EditText biology = createEditTextBiology(
                innerLayout,
                layoutParamsChildren);

        innerLayout.addView(physics);
        innerLayout.addView(chemistry);
        innerLayout.addView(maths);
        innerLayout.addView(biology);
        return innerLayout;
    }

    private EditText createEditTextPhysics(LinearLayout innerLayout,
            LinearLayout.LayoutParams params) {
        EditText physics = new EditText(mContext);
        physics.setLayoutParams(params);
        physics.setTag(TAG_PHYSICS);
        physics.setId(mId++);
        physics.setBackgroundResource(R.drawable.custom_bg_edit_text);
        physics.setHint(R.string.hint_score_physics);
        physics.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        return physics;
    }

    private EditText createEditTextChemistry(LinearLayout innerLayout,
            LinearLayout.LayoutParams params) {
        EditText chemistry = new EditText(mContext);
        chemistry.setLayoutParams(params);
        chemistry.setTag(TAG_CHEMISTRY);
        chemistry.setId(mId++);
        chemistry.setBackgroundResource(R.drawable.custom_bg_edit_text);
        chemistry.setHint(R.string.hint_score_chemistry);
        chemistry.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        return chemistry;
    }

    private EditText createEditTextMaths(LinearLayout innerLayout,
            LinearLayout.LayoutParams params) {
        EditText maths = new EditText(mContext);
        maths.setLayoutParams(params);
        maths.setTag(TAG_MATHS);
        maths.setId(mId++);
        maths.setBackgroundResource(R.drawable.custom_bg_edit_text);
        maths.setHint(R.string.hint_score_maths);
        maths.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        return maths;
    }

    private EditText createEditTextBiology(LinearLayout innerLayout,
            LinearLayout.LayoutParams params) {
        EditText biology = new EditText(mContext);
        biology.setLayoutParams(params);
        biology.setTag(TAG_BIOLOGY);
        biology.setId(mId++);
        biology.setBackgroundResource(R.drawable.custom_bg_edit_text);
        biology.setHint(R.string.hint_score_biology);
        biology.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        return biology;
    }
}
