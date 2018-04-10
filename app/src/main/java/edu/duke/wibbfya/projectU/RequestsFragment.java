package edu.duke.wibbfya.projectU;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RequestsFragment extends Fragment {
    private RecyclerView mProjectsList;
    private DatabaseReference mProjectsDatabase;

    private FirebaseAuth mAuth;
    private View mMainView;



    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView= inflater.inflate(R.layout.fragment_requests, container, false);
        mProjectsList=(RecyclerView)mMainView.findViewById(R.id.projects_list);
        mAuth=FirebaseAuth.getInstance();

        mProjectsDatabase = FirebaseDatabase.getInstance().getReference().child("Project");
        mProjectsDatabase.keepSynced(true);



        mProjectsList.setHasFixedSize(true);
        mProjectsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Projects,ProjectsViewHolder> projectRecyclerViewAdapter=new FirebaseRecyclerAdapter<Projects, ProjectsViewHolder>(
                Projects.class,
                R.layout.projects_single_layout,
                ProjectsViewHolder.class,
                mProjectsDatabase
        ) {
            @Override
            protected void populateViewHolder(final ProjectsViewHolder viewHolder, Projects model, int position) {
                viewHolder.setDescription(model.getDescription());
                viewHolder.setProjectName(model.getName());
                final String list_project_id =getRef(position).getKey();
                String testListProject = "The returned list_project_id is: " + list_project_id;
                Log.d("Testing", testListProject);

                mProjectsDatabase.child(list_project_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Gather all the details for passing on to the next activity
                        final String projectName = dataSnapshot.child("projectName").getValue().toString();
                        final String startDate = dataSnapshot.child("startDate").getValue().toString();
                        final String endDate = dataSnapshot.child("endDate").getValue().toString();
                        final String description = dataSnapshot.child("description").getValue().toString();

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Log.d("Testing", "This is testing on if the onClick will work");
                                Intent statusIntent = new Intent(getContext(), DetailActivity.class);

                                // Pass in the details of the project to the detail activiy
                                statusIntent.putExtra("projectName", projectName);
                                statusIntent.putExtra("startDate", startDate);
                                statusIntent.putExtra("endDate", endDate);
                                statusIntent.putExtra("description", description);

                                // Start the Status Activity
                                startActivity(statusIntent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        Log.d("Testing", "Right before setting the Recycler View Adapter");

        mProjectsList.setAdapter(projectRecyclerViewAdapter);

    }

    public static class ProjectsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ProjectsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setProjectName(String projectName) {
            TextView projectNameView = (TextView) mView.findViewById(R.id.project_single_name);
            projectNameView.setText(projectName);
        }

        public void setDescription(String description) {
            TextView descriptionView = (TextView) mView.findViewById(R.id.user_single_status);
            descriptionView.setText(description);
        }
    }
}
