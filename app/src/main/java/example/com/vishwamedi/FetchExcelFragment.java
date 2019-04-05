package example.com.vishwamedi;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FetchExcelFragment extends Fragment {


    public static int EXCEL_CODE=101;

    private Dialog myDialog;
    private ProgressDialog pd;
    private FirebaseFirestore fs;
    private  HashMap<String,Object> data;


    private String trackIdStr,compNameStr,patientNameStr,hospitalNameStr,phoneNoStr,diseaseStr,
            treatingDocStr,addrStr,landmarkStr,stateStr,distStr,pinStr,vendorStr,remarkStr;

    private String caseDateStr,dateOfAdmissionStr,dateStr,timeStr;


    public FetchExcelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_fetch_excel, container, false);


        data=new HashMap<>();

        fs= FirebaseFirestore.getInstance();

        pd=new ProgressDialog(getActivity());
        if(checkPermission()) {

            getFile();



        }else
        {
            requestPermission();
        }





        return v;
    }

    private void getFile() {

        Intent intent=new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select File"),EXCEL_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if(requestCode==EXCEL_CODE && resultCode==RESULT_OK)
            {
                Uri uri=data.getData();

             //   Toast.makeText(getActivity(), "Path: "+ Objects.requireNonNull(uri).getPath(), Toast.LENGTH_SHORT).show();


                if (uri == null)
                {
                    Toast.makeText(getActivity(), "Please select the File", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    String csvFilename=uri.getPath();

                    String path= Objects.requireNonNull(csvFilename).replace("external_files","sdcard");


                //    Toast.makeText(getActivity(), "path: "+path, Toast.LENGTH_SHORT).show();

                    CSVReader csvReader = new CSVReader(new FileReader(path), ',' , '"' );

             //       CSVReader csvReader = new CSVReader(new FileReader(Objects.requireNonNull(path)), ';');

                    String[] temp = null;

                    while((temp = csvReader.readNext()) != null) {


                        Toast.makeText(getActivity(), "data: " +temp[0], Toast.LENGTH_SHORT).show();

                        compNameStr=temp[0];
                        patientNameStr=temp[1];
                        diseaseStr=temp[2];
                        hospitalNameStr=temp[3];
                        phoneNoStr=temp[4];
                        treatingDocStr=temp[5];
                        addrStr=temp[6];
                        landmarkStr=temp[7];
                        stateStr=temp[8];
                        distStr=temp[9];
                        pinStr=temp[10];
                        vendorStr=temp[11];
                        caseDateStr=temp[12];
                        dateOfAdmissionStr=temp[13];
                        remarkStr=temp[14];

                        create();
                    }
//...
                    csvReader.close();


/*

                    String line = "";
                    String cvsSplitBy = ",";

                    try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(path)))) {

                        while ((line = br.readLine()) != null) {

                            // use comma as separator
                            String[] temp = line.split(cvsSplitBy);

                            Toast.makeText(getActivity(), "Data: "+temp[0], Toast.LENGTH_SHORT).show();


                            compNameStr=temp[0];
                            patientNameStr=temp[1];
                            diseaseStr=temp[2];
                            hospitalNameStr=temp[3];
                            phoneNoStr=temp[4];
                            treatingDocStr=temp[5];
                            addrStr=temp[6];
                            landmarkStr=temp[7];
                            stateStr=temp[8];
                            distStr=temp[9];
                            pinStr=temp[10];
                            vendorStr=temp[11];
                            caseDateStr=temp[12];
                            dateOfAdmissionStr=temp[13];
                            remarkStr=temp[14];

                            create();
                        }

                    } catch (IOException e) {
                        Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    */
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(),"Error: "+e,Toast.LENGTH_LONG).show();
            Log.d("Error File",e.getMessage());

        }



    }


    private  void create()
    {
       final TextView TrackId,CompanyName,Patient,hospitalName,Phone,Employee,TreatingDoc,
                Addr,Landmark,State,Dist,Pin,Vendor,CaseDate,AdmissionDate,Remark,Verifier,CaseDateTime,CaseEployee;


        myDialog = new Dialog(Objects.requireNonNull(this.getActivity()));
        myDialog.setContentView(R.layout.confirm_case_diag);
        myDialog.setCancelable(true);

        Button confirm = (Button) myDialog.findViewById(R.id.ConfirmBtn);

        Button reject = (Button) myDialog.findViewById(R.id.CancelBtn);


        TrackId= myDialog.findViewById(R.id.ConfirmTrackId);
        CompanyName=myDialog.findViewById(R.id.ConfirmCompanyName);
        Patient=myDialog.findViewById(R.id.ConfirmPatient);
        hospitalName=myDialog.findViewById(R.id.ConfirmHospital);
        Phone=myDialog.findViewById(R.id.ConfirmPhone);
        TreatingDoc=myDialog.findViewById(R.id.ConfirmTreatingDoc);
        Addr=myDialog.findViewById(R.id.ConfirmAddress);
        Landmark=myDialog.findViewById(R.id.ConfirmLandmark);
        State=myDialog.findViewById(R.id.ConfirmState);
        Dist=myDialog.findViewById(R.id.ConfirmDist);
        Pin=myDialog.findViewById(R.id.ConfirmPincode);
        Vendor=myDialog.findViewById(R.id.ConfirmVendor);
        CaseDate=myDialog.findViewById(R.id.ConfirmCaseDate);
        AdmissionDate=myDialog.findViewById(R.id.ConfirmAdmissionDate);
        Remark=myDialog.findViewById(R.id.ConfirmRemark);
        //*************************************************************

        fs.collection("CaseId").document("ID").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            trackIdStr=task.getResult().getString("CurrentID");
                            //***********************************************************************





                            //**********************************************************************


                            TrackId.setText(trackIdStr);
                            CompanyName.setText(compNameStr);
                            Patient.setText(patientNameStr);
                            hospitalName.setText(hospitalNameStr);
                            Phone.setText(phoneNoStr);

                            TreatingDoc.setText(treatingDocStr);
                            Addr.setText(addrStr);
                            Landmark.setText(landmarkStr);

                            State.setText(stateStr);
                            Dist.setText(distStr);
                            Pin.setText(pinStr);
                            Vendor.setText(vendorStr);
                            CaseDate.setText(caseDateStr);
                            AdmissionDate.setText(dateOfAdmissionStr);
                            Remark.setText(remarkStr);


                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), "Could not fetched Track ID: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.hide();
                pd.dismiss();
            }
        });

        //*****************************************************************

        myDialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.setMessage("wait until uploading data");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                putData();
                upload();

            }


        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    public void upload()
    {

        fs.collection("Cases").document(trackIdStr).set(data, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            int i=Integer.parseInt(trackIdStr);
                            i++;

                            trackIdStr=String.valueOf(i);

                            HashMap<String,Object> up=new HashMap<>();
                            up.put("CurrentID",trackIdStr);

                            fs.collection("CaseId").document("ID").update(up)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {

                                                Toast.makeText(getActivity(), "Successfully updated Track Id", Toast.LENGTH_SHORT).show();
                                                pd.hide();
                                                pd.dismiss();
                                                Intent intent=new Intent(getActivity(),MainActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getActivity(), "Could not update Track ID"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    pd.hide();
                                    pd.dismiss();
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

    public void putData()
    {
        data.put("TrackId",trackIdStr);
        data.put("CompanyName",compNameStr);
        data.put("PatientName",patientNameStr);
        data.put("DiseaseName",diseaseStr);
        data.put("HospitalName",hospitalNameStr);
        data.put("PhoneNo",phoneNoStr);
        data.put("TreatingDoc",treatingDocStr);
        data.put("Address",addrStr);
        data.put("Landmark",landmarkStr);
        data.put("State",stateStr);
        data.put("District",distStr);
        data.put("Pincode",pinStr);

        data.put("Vendor",vendorStr);
        data.put("Remark",remarkStr);
        data.put("CaseDate",caseDateStr);
        data.put("DateOfAdmission",dateOfAdmissionStr);
        data.put("Status","1");

    }

    public boolean checkPermission()
    {
        final Activity context=getActivity();

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(context), android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        return false;
    }

    public void requestPermission()
    {
        final Activity context=getActivity();

        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(context), android.Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("Permission Needed")
                    .setMessage("Storage Permission is required to generate Csv file")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(context, new String []{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    }).create().show();

        }
        else
        {
            ActivityCompat.requestPermissions(context, new String []{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

    }

}
