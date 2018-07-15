package edu.cpp.cs499.commandpatterndemo.command;

import java.util.Random;

import edu.cpp.cs499.commandpatterndemo.Course;
import edu.cpp.cs499.commandpatterndemo.CourseArrayAdapter;

public class DeleteCourseCommand implements Command {

    private CourseArrayAdapter courseArrayAdapter;
    private Course course;
    private int index;

    public DeleteCourseCommand(CourseArrayAdapter courseArrayAdapter) {
        this.courseArrayAdapter = courseArrayAdapter;
    }

    @Override
    public void execute() {
        index = new Random().nextInt(courseArrayAdapter.getCount());
        course = courseArrayAdapter.getItem(index);
        courseArrayAdapter.remove(course);
        courseArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void unexecute() {
        if (index > courseArrayAdapter.getCount() - 1) {
            courseArrayAdapter.add(course);
        } else {
            courseArrayAdapter.insert(course, index);
        }
        courseArrayAdapter.notifyDataSetChanged();
    }
}
