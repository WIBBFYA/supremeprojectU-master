package edu.duke.wibbfya.projectU;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProjectActivity extends AppCompatActivity {
    private TextInputLayout mProjectName;
    private TextInputLayout mStart;
    private TextInputLayout mEnd;
    private TextInputLayout mIntro;

    private Button mChange;
    private Button mMember;

    private android.support.v7.widget.Toolbar mToolbar;

    private DatabaseReference mProjectDatabase;

    //ProgressDialog
    //private ProgressDialog mProProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.project_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Manage Project");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mProjectName=(TextInputLayout)findViewById(R.id.projectname_input);
        mStart=(TextInputLayout)findViewById(R.id.projectstart_input);
        mEnd=(TextInputLayout)findViewById(R.id.projectend_input);
        mIntro=(TextInputLayout)findViewById(R.id.projectintro_input);

        mChange=(Button)findViewById(R.id.project_save_btn);
        mMember=(Button)findViewById(R.id.project_member_btn);

        mProjectDatabase= FirebaseDatabase.getInstance().getReference().child("Project");

        // Set the project details so they will show up when user click to view and edit
        mProjectDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Projects retrievedProject = dataSnapshot.getValue(Projects.class);
                if (retrievedProject != null) {
                    String projectName = retrievedProject.getName();
                    String startDate = retrievedProject.getStartDate();
                    String endDate = retrievedProject.getEndDate();
                    String description = retrievedProject.getDescription();

                    String testName = "This is the retrieved project name: " + projectName;
                    Log.d("Testing", testName);
                    String testStart = "This is the retrieved start date: " + startDate;
                    Log.d("Testing", testStart);
                    String testEnd = "This is the retrieved end date: " + endDate;
                    Log.d("Testing", testEnd);
                    String testDes = "This is the retrieved description: " + description;
                    Log.d("Testing", testDes);

                    //String projectName = dataSnapshot.child("projectName").getValue().toString();
                    //String endDate = dataSnapshot.child("start").getValue().toString();
                    //String startDate = dataSnapshot.child("end").getValue().toString();
                    //String description = dataSnapshot.child("briefIntro").getValue().toString();

                    mProjectName.getEditText().setText(projectName);
                    mStart.getEditText().setText(startDate);
                    mEnd.getEditText().setText(endDate);
                    mIntro.getEditText().setText(description);
                } else { // retrievedProject is null

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mChange.setEnabled(false);

                String projectName=mProjectName.getEditText().getText().toString();
                String start=mStart.getEditText().getText().toString();
                String end=mEnd.getEditText().getText().toString();
                String intro=mIntro.getEditText().getText().toString();
                /*
                // This is the old way of adding the project into the map

                HashMap<String,String> projectData=new HashMap<>();

                projectData.put("projectName",projectName);
                projectData.put("start",start);
                projectData.put("end",end);
                projectData.put("briefIntro",intro);
                */

                Projects newProject = new Projects();
                newProject.setName(projectName);
                newProject.setDescription(intro);
                newProject.setStartDate(start);
                newProject.setEndDate(end);

                // Push the node to database for a new project node
                DatabaseReference project_push = mProjectDatabase.push();
                String push_id = project_push.getKey();
                //Map<String, Projects> project = new HashMap<>();
                //project.put(push_id, newProject);
                mProjectDatabase.child(push_id).setValue(newProject);

                /*
                mProjectDatabase.setValue(project).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent projectIntent = new Intent(ProjectActivity.this, MainActivity.class);
                            projectIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(projectIntent);
                            finish();
                        }
                        mChange.setEnabled(true);
                    }
                });
                */
            }
        });

        mMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMemberIntent = new Intent(ProjectActivity.this, UsersActivity.class);
                startActivity(addMemberIntent);
            }
        });





    }
}
