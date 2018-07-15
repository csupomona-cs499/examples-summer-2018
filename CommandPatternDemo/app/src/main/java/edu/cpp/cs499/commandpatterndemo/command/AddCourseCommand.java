package edu.cpp.cs499.commandpatterndemo.command;

import java.util.Random;

import edu.cpp.cs499.commandpatterndemo.Course;
import edu.cpp.cs499.commandpatterndemo.CourseArrayAdapter;

public class AddCourseCommand implements Command {

    private CourseArrayAdapter courseArrayAdapter;
    private Course courseAdded;

    public AddCourseCommand(CourseArrayAdapter courseArrayAdapter) {
        this.courseArrayAdapter = courseArrayAdapter;
    }

    @Override
    public void execute() {
        courseAdded = new Course();
        courseAdded.setId("CS" + new Random().nextInt(600) + 100);
        courseAdded.setCredits(new Random().nextInt(4) + 1);
        courseAdded.setName("Computer Science " + new Random().nextInt(10) + 1);
        courseArrayAdapter.add(courseAdded);
        courseArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void unexecute() {
        courseArrayAdapter.remove(courseAdded);
        courseArrayAdapter.notifyDataSetChanged();
    }
}
