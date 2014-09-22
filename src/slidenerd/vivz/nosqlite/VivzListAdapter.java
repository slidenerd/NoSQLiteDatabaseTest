
package slidenerd.vivz.nosqlite;

import java.util.ArrayList;

import slidenerd.vivz.model.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VivzListAdapter extends BaseAdapter {

    private ArrayList<Person> mData;
    private LayoutInflater mInflater;

    public VivzListAdapter(Context context) {
        // TODO Auto-generated constructor stub
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<Person> getData()
    {
        return mData;
    }

    public void setData(ArrayList<Person> data)
    {
        this.mData = data;
        if (data != null) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (mData != null && !mData.isEmpty()) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Person getItem(int position) {
        // TODO Auto-generated method stub
        if (mData != null && !mData.isEmpty()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row = convertView;
        ViewHolder holder = null;
        if (row == null)
        {
            row = mInflater
                    .inflate(R.layout.custom_list_item, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }
        if (mData != null && !mData.isEmpty()) {
            Person currentPerson = mData.get(position);
            initValues(holder, currentPerson);
        }

        return row;
    }

    public void initValues(ViewHolder holder, Person currentPerson) {
        holder.personName
                .setText(currentPerson.personName);
        holder.personAge
                .setText(currentPerson.personAge + "");
        holder.scorePhysics
                .setText(currentPerson.personScore.scorePhysics + "");
        holder.scoreChemistry
                .setText(currentPerson.personScore.scoreChemistry + "");
        holder.scoreMaths
                .setText(currentPerson.personScore.scoreMaths + "");
        holder.scoreBiology
                .setText(currentPerson.personScore.scoreBiology + "");

    }

    class ViewHolder {
        TextView personName;
        TextView personAge;
        TextView scorePhysics;
        TextView scoreChemistry;
        TextView scoreMaths;
        TextView scoreBiology;

        public ViewHolder(View view) {
            // TODO Auto-generated constructor stub
            personName = (TextView) view
                    .findViewById(R.id.text_name_value);

            personAge = (TextView) view
                    .findViewById(R.id.text_age_value);

            scorePhysics = (TextView) view
                    .findViewById(R.id.text_physics_value);

            scoreChemistry = (TextView) view
                    .findViewById(R.id.text_chemistry_value);

            scoreMaths = (TextView) view
                    .findViewById(R.id.text_maths_value);

            scoreBiology = (TextView) view
                    .findViewById(R.id.text_biology_value);
        }
    }
}
