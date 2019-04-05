package example.com.vishwamedi;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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

    private Bundle bundle;
    private Button AssignAgent;
    private ProgressDialog pd;

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

        fs=FirebaseFirestore.getInstance();

        AssignAgent=v.findViewById(R.id.AssignAgentBtn);


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
        TrackId.setText(bundle.getString("TrackID"));
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


        AssignAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(Objects.requireNonNull(getActivity()));
                dialog.setContentView(R.layout.agent_list_layout);
                dialog.setCancelable(true);

                RecyclerView recyclerView=dialog.findViewById(R.id.AgentListRecycler);

                list=new ArrayList<>();

                agentListAdapter=new AgentListAdapter(dialog,bundle,getActivity(),list, Objects.requireNonNull(getActivity()).getSupportFragmentManager());

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




        return v;
    }

}
