package example.com.vishwamedi;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import example.com.vishwamedi.model.AgentModel;
import example.com.vishwamedi.model.CasePostModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class CasDetailFragment extends Fragment {

    private TextView status,createdBy,TrackId,CompanyName,Patient,hospitalName,Phone,Employee,TreatingDoc,
                    Addr,Landmark,State,Dist,Pin,Vendor,CaseDate,AdmissionDate,Remark,Verifier,CaseDateTime,CaseEployee;

    private FirebaseFirestore fs;

    private AgentListAdapter agentListAdapter;

    private ArrayList<AgentModel> list;

    private LinearLayout linearLayout;

    private Bundle bundle;
    private Button AssignAgent,acceptCase,rejectCase;
    private ProgressDialog pd;

    private  String frag;

    private Dialog dialog;

    public CasDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_cas_detail, container, false);

        pd=new ProgressDialog(getActivity());

        bundle=getArguments();

        frag= Objects.requireNonNull(bundle).getString("Fragment");

        Toast.makeText(getActivity(), "Frag: "+frag, Toast.LENGTH_SHORT).show();


        fs=FirebaseFirestore.getInstance();

        AssignAgent = v.findViewById(R.id.AssignAgentBtn);

        linearLayout=v.findViewById(R.id.ClientAcceptLayout);

        acceptCase=v.findViewById(R.id.AcceptBtn);
        rejectCase=v.findViewById(R.id.RejectBtn);


        CaseEployee=v.findViewById(R.id.EmployeeStart);

        status=v.findViewById(R.id.DetailCurrentStatus);
        createdBy=v.findViewById(R.id.DetailCreatedBy);
        TrackId=v.findViewById(R.id.DetailTrackId);
        CompanyName=v.findViewById(R.id.DetailCompanyName);
        Patient=v.findViewById(R.id.DetailPatient);
        hospitalName=v.findViewById(R.id.DetailHospital);
        Phone=v.findViewById(R.id.DetailPhone);
        Employee=v.findViewById(R.id.DetailEmployee);
        TreatingDoc=v.findViewById(R.id.DetailTreatingDoc);
        Addr=v.findViewById(R.id.DetailAddress);
        Landmark=v.findViewById(R.id.DetailLandmark);
        State=v.findViewById(R.id.DetailState);
        Dist=v.findViewById(R.id.DetailDist);
        Pin=v.findViewById(R.id.DetailPincode);
        Vendor=v.findViewById(R.id.DetailVendor);
        CaseDate=v.findViewById(R.id.DetailCaseDate);
        AdmissionDate=v.findViewById(R.id.DetailAdmissionDate);
        Remark=v.findViewById(R.id.DetailRemark);


        if (bundle.get("Status").equals("1"))
        {
            status.setText("Created");
        }else
        {
            status.setText("Closed");
        }



        createdBy.setText("Me");
        TrackId.setText(bundle.getString("TrackId"));
        CompanyName.setText(bundle.getString("CompanyName"));
        Patient.setText(bundle.getString("Patient"));
        hospitalName.setText(bundle.getString("Hospital"));
        Phone.setText(bundle.getString("Phone"));
        Employee.setText("Me");
        TreatingDoc.setText(bundle.getString("Doc"));
        Addr.setText(bundle.getString("Addr"));
        Landmark.setText(bundle.getString("Land"));

        State.setText(bundle.getString("State"));
        Dist.setText(bundle.getString("Dist"));
        Pin.setText(bundle.getString("Pin"));
        Vendor.setText(bundle.getString("Vendor"));
        CaseDate.setText(bundle.getString("CaseDate"));
        AdmissionDate.setText(bundle.getString("AdmissionDate"));
        Remark.setText(bundle.getString("Remark"));


    //    pd.setMessage("Wait Until Loading");
    //    pd.setCanceledOnTouchOutside(false);
     //   pd.show();


        if (frag.equals("CetralTeamFrag")) {


            Toast.makeText(getActivity(), "CentralTeamFrag", Toast.LENGTH_SHORT).show();

            AssignAgent.setVisibility(View.VISIBLE);

            linearLayout.setVisibility(View.GONE);
            acceptCase.setVisibility(View.GONE);
            rejectCase.setVisibility(View.GONE);

            AssignAgent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog = new Dialog(Objects.requireNonNull(getActivity()));
                    dialog.setContentView(R.layout.agent_list_layout);
                    dialog.setCancelable(true);

                    RecyclerView recyclerView = dialog.findViewById(R.id.AgentListRecycler);

                    list = new ArrayList<>();

                    agentListAdapter = new AgentListAdapter(dialog, bundle, getActivity(), list, Objects.requireNonNull(getActivity()).getSupportFragmentManager());

                    dialog.show();

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(agentListAdapter);

                    pd.setMessage("Wait Until Loading");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();

                    fs.collection("Agent").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            if (e != null) {
                                Log.e("Error: " + e.getMessage(), "Error");
                            } else {
                                for (DocumentChange doc : Objects.requireNonNull(queryDocumentSnapshots).getDocumentChanges()) {
                                    if (doc.getType().equals(DocumentChange.Type.ADDED)) {

                                        AgentModel model = doc.getDocument().toObject(AgentModel.class);
                                        list.add(model);

                                        agentListAdapter.notifyDataSetChanged();
                                    }
                                }

                                pd.hide();
                                pd.dismiss();
                            }
                        }
                    });


                }
            });
        }
        else if(frag.equals("ClientFrag"))
        {
            AssignAgent.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            acceptCase.setVisibility(View.VISIBLE);
            rejectCase.setVisibility(View.VISIBLE);

            Toast.makeText(getActivity(), "AgentFrag", Toast.LENGTH_SHORT).show();

            acceptCase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            rejectCase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Map<String,Object> temp=new HashMap<>();
                    temp.put("Status","1");

                    fs.collection("Cases").document(Objects.requireNonNull(bundle.getString("TrackId")))
                            .update(temp)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity(), "Rejected", Toast.LENGTH_SHORT).show();

                                        fs.collection("Agent").document(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()))
                                                .collection("Cases")
                                                .document(Objects.requireNonNull(bundle.getString("TrackId")))
                                                .delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        ClientPendingCases fragment=new ClientPendingCases();

                                                        android.support.v4.app.FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                                                .add(fragment,"ClientPendingCaseFragment").addToBackStack("ClientPendingCaseFragment");


                                                        fragment.setArguments(bundle);
                                                        fragmentTransaction.replace(R.id.MainLayout, fragment);
                                                        fragmentTransaction.commit();

                                                        Toast.makeText(getActivity(), "Sucessfully Rejected Case", Toast.LENGTH_SHORT).show();


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) { Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }



        return v;
    }

}
