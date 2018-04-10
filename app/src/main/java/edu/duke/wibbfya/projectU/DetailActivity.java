package edu.duke.wibbfya.projectU;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView projectname,briefIntro;
    private Button documentation,testing,development;
    private Toolbar mToolbar;

    // Holder for the detail of the project passed from RequestFragment
    private String projectName, startDate, endDate, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        projectname=(TextView)findViewById(R.id.detail_displayName);
        briefIntro=(TextView)findViewById(R.id.detail_intro);
        documentation=(Button)findViewById(R.id.detail_doc_btn);
        development=(Button)findViewById(R.id.detail_development_btn);
        testing=(Button)findViewById(R.id.detail_testing_btn);

        /*
        mToolbar = (Toolbar) findViewById(R.id.users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Project Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
        // Get the project details passed from request fragment
        projectName = getIntent().getStringExtra("projectName");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        description = getIntent().getStringExtra("description");

        // Set the project name and the info to display
        projectname.setText(projectName);
        briefIntro.setText(description);


        documentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent docIntent=new Intent(DetailActivity.this,DocumentationActivity.class);
                startActivity(docIntent);

            }
        });

        development.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent devIntent=new Intent(DetailActivity.this,DevelopmentActivity.class);
                startActivity(devIntent);
            }
        });

        testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testIntent = new Intent(DetailActivity.this, TestingActivity.class);
                startActivity(testIntent);
            }
        });
    }
}
