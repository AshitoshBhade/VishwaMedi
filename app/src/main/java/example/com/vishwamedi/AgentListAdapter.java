package example.com.vishwamedi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.thoughtbot.expandablerecyclerview.ExpandCollapseController;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.listeners.ExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableList;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import example.com.vishwamedi.model.AgentModel;
import example.com.vishwamedi.model.CasePostModel;

public class AgentListAdapter extends RecyclerView.Adapter<AgentListAdapter.ViewHolder> implements Filterable
{
    private ArrayList<AgentModel> list;
    private FragmentManager fragmentManager;
    Activity activity;
    private HomeFragment homeFragment;
    private Bundle bundle;
    private Dialog dialog;
    public AgentListAdapter(Dialog dialog, Bundle s, FragmentActivity activity, ArrayList<AgentModel> list, FragmentManager supportFragmentManager) {

        this.activity=activity;
        this.list=list;
        this.dialog=dialog;
        this.bundle=s;
        fragmentManager=supportFragmentManager;
    }


    @NonNull
    @Override
    public AgentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_detail_header,parent,false);

        ViewHolder holder=new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AgentListAdapter.ViewHolder holder, final int position) {

        holder.AgentName.setText(list.get(position).getName());
        holder.AgentPhone.setText(list.get(position).getPhoneNo());
        holder.AgentAddr.setText(list.get(position).getLandmark()+", "+list.get(position).getAddress()+", "+list.get(position).getDistrict()+", "+list.get(position).getState());

        holder.Email.setText(list.get(position).getEmail());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.Assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseFirestore fs=FirebaseFirestore.getInstance();


                HashMap<String,Object> data=new HashMap<>();

                data.put("TrackId",bundle.getString("TrackId"));
                data.put("CompanyName",bundle.getString("CompanyName"));
                data.put("PatientName",bundle.getString("Patient"));
                data.put("DiseaseName",bundle.getString("Disease"));
                data.put("HospitalName",bundle.getString("Hospital"));
                data.put("PhoneNo",bundle.getString("Phone"));
                data.put("TreatingDoc",bundle.getString("Doc"));
                data.put("Address",bundle.getString("Addr"));
                data.put("Landmark",bundle.getString("Land"));
                data.put("State",bundle.getString("State"));
                data.put("District",bundle.getString("Dist"));
                data.put("Pincode",bundle.getString("Pin"));
                data.put("Vendor",bundle.getString("Vendor"));
                data.put("Remark",bundle.getString("Remark"));
                data.put("CaseDate",bundle.getString("CaseDate"));
                data.put("DateOfAdmission",bundle.getString("AdmissionDate"));
                data.put("Status","1");


                fs.collection("Agent").document(list.get(position).getEmail()).collection("Cases")
                        .document(Objects.requireNonNull(bundle.getString("TrackId")))
                        .set(data,SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful())
                                {
                                    Map<String,Object> temp=new HashMap<>();
                                    temp.put("Status","2");

                                    fs.collection("Cases").document(Objects.requireNonNull(bundle.getString("TrackId")))
                                            .update(temp)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful())
                                                    {
                                                        dialog.dismiss();
                                                        homeFragment=new HomeFragment();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                                                                .add(homeFragment,"home").addToBackStack("home");

                                                        fragmentTransaction.replace(R.id.MainLayout, homeFragment);
                                                        fragmentTransaction.commit();

                                                        Toast.makeText(activity, "Successfully case Assigned", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(activity, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("Agent Upload Error: ",e.getMessage());
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView AgentName,AgentPhone,AgentAddr,Email;
        Button Assign;
        CardView card;

        public ViewHolder(View itemView) {

            super(itemView);
            AgentName=itemView.findViewById(R.id.AgentDetailName);
            AgentPhone=itemView.findViewById(R.id.AgentDetailPhone);
            AgentAddr=itemView.findViewById(R.id.AgentDetailAddr);
            Assign=itemView.findViewById(R.id.AssignBtn);
            Email=itemView.findViewById(R.id.AgentDetailEmail);
            card=itemView.findViewById(R.id.AgentDetailCard);
        }
    }
}



