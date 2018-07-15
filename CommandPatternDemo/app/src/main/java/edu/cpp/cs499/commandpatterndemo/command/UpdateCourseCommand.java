package edu.cpp.cs499.commandpatterndemo.command;

import java.util.Random;

import edu.cpp.cs499.commandpatterndemo.Course;
import edu.cpp.cs499.commandpatterndemo.CourseArrayAdapter;

public class UpdateCourseCommand implements Command {

    private CourseArrayAdapter courseArrayAdapter;
    private Course course;
    private String id;
    private String name;
    private int credits;

    public UpdateCourseCommand(CourseArrayAdapter courseArrayAdapter) {
        this.courseArrayAdapter = courseArrayAdapter;
    }

    @Override
    public void execute() {
        int index = new Random().nextInt(courseArrayAdapter.getCount());

        course = courseArrayAdapter.getItem(index);
        id = course.getId();
        credits= course.getCredits();
        name = course.getName();

        course.setId("CS" + new Random().nextInt(600) + 100);
        course.setCredits(new Random().nextInt(4) + 1);
        course.setName("Computer Science " + new Random().nextInt(10) + 1);
        courseArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void unexecute() {
        course.setId(id);
        course.setName(name);
        course.setCredits(credits);
        courseArrayAdapter.notifyDataSetChanged();
    }
}
