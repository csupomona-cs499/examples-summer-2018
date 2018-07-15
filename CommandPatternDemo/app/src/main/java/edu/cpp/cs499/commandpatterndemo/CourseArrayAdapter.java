package edu.cpp.cs499.commandpatterndemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseArrayAdapter extends ArrayAdapter<Course> {

    private Context context;
    private List<Course> courseList;

    public CourseArrayAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
        super(context, resource, objects);
        this.context = context;
        this.courseList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_item_course, parent, false);
        TextView idTextView = view.findViewById(R.id.idTextView);
        idTextView.setText(courseList.get(position).getId());

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        nameTextView.setText(courseList.get(position).getName());

        TextView creditTextView = view.findViewById(R.id.creditTextView);
        creditTextView.setText(courseList.get(position).getCredits() + "");

        return view;
    }
}
