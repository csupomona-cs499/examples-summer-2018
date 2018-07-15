package edu.cpp.cs499.commandpatterndemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import edu.cpp.cs499.commandpatterndemo.command.AddCourseCommand;
import edu.cpp.cs499.commandpatterndemo.command.Command;
import edu.cpp.cs499.commandpatterndemo.command.DeleteCourseCommand;
import edu.cpp.cs499.commandpatterndemo.command.UpdateCourseCommand;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    private Button undoButton;
    private Button redoButton;

    private ListView listView;

    private CourseArrayAdapter courseArrayAdapter;
    private List<Course> courseList;


    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        undoButton = findViewById(R.id.undoButton);
        redoButton = findViewById(R.id.redoButton);
        listView = findViewById(R.id.listView);

        courseList = new ArrayList<>();
        courseArrayAdapter = new CourseArrayAdapter(this, R.layout.listview_item_course, courseList);
        listView.setAdapter(courseArrayAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Command command = new AddCourseCommand(courseArrayAdapter);
                command.execute();
                undoStack.push(command);
                redoStack.clear();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Command command = new UpdateCourseCommand(courseArrayAdapter);
                command.execute();
                undoStack.push(command);
                redoStack.clear();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Command command = new DeleteCourseCommand(courseArrayAdapter);
                command.execute();
                undoStack.push(command);
                redoStack.clear();
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!undoStack.isEmpty()) {
                    Command command = undoStack.pop();
                    command.unexecute();

                    redoStack.push(command);
                }
            }
        });

        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!redoStack.isEmpty()) {
                    Command command = redoStack.pop();
                    command.execute();

                    undoStack.push(command);
                }
            }
        });
    }
}
