package example.com.vishwamedi;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class CasDetailFragment extends Fragment {

    private TextView status,createdBy,TrackId,CompanyName,Patient,hospitalName,Phone,Employee,TreatingDoc,
                    Addr,Landmark,State,Dist,Pin,Vendor,CaseDate,AdmissionDate,Remark,Verifier;

    private FirebaseFirestore fs;

    private Bundle bundle;
    private ProgressDialog pd;
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
        Verifier=v.findViewById(R.id.DetailVerifier);



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
        Verifier.setText(bundle.getString("Verifier"));


    //    pd.setMessage("Wait Until Loading");
    //    pd.setCanceledOnTouchOutside(false);
     //   pd.show();



        return v;
    }

}
