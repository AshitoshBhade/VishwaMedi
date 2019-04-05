package example.com.vishwamedi;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CasePostFragment extends Fragment {


    private Button postCaseBtn,caseDateBtn,admissionDateBtn,submitBtn;

    private EditText trackId,compName,patientName,hospitalName,phoneNo,
            treatingDoc,addr,landmark,state,dist,pin,vendor,remark,verifier,Disease;

    private CasePostFragment casePostFragment;

    private String trackIdStr,compNameStr,patientNameStr,hospitalNameStr,phoneNoStr,caseStatusStr,diseaseStr,
            treatingDocStr,addrStr,landmarkStr,stateStr,distStr,pinStr,vendorStr,remarkStr,verifierStr;

    private String caseDateStr,dateOfAdmissionStr,dateStr,timeStr;

    private FirebaseFirestore fs;
    private Map<String,Object> data;
    int mYear,mMonth,mDay;

    private ProgressDialog pd;
    private Spinner StatusSpinner;
    private List<String> type;
    private ArrayAdapter<String> adapter;
    private LocalDate localDate;
    private Time localTime;
    public CasePostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_case_post, container, false);


        trackId=v.findViewById(R.id.TrackId);
        trackId.setEnabled(false);

        compName=v.findViewById(R.id.CompanyName);
        patientName=v.findViewById(R.id.PatientName);
        hospitalName=v.findViewById(R.id.HospitalName);
        phoneNo=v.findViewById(R.id.Phone);
        treatingDoc=v.findViewById(R.id.TreatingDoctor);
        addr=v.findViewById(R.id.Address);
        landmark=v.findViewById(R.id.LandMark);
        state=v.findViewById(R.id.State);
        dist=v.findViewById(R.id.District);
        pin=v.findViewById(R.id.Pincode);
        vendor=v.findViewById(R.id.Vendor);
        remark=v.findViewById(R.id.Remarks);
        verifier=v.findViewById(R.id.VerifierName);
        Disease=v.findViewById(R.id.Disease);

        caseDateBtn=v.findViewById(R.id.CaseDateButton);
        admissionDateBtn=v.findViewById(R.id.DateOfAdmissionButton);
        submitBtn=v.findViewById(R.id.SubmitButton);

        FirebaseApp.initializeApp(this.getActivity());

        fs=FirebaseFirestore.getInstance();

        pd=new ProgressDialog(getActivity());

        StatusSpinner=v.findViewById(R.id.CaseStatus);

        localDate=new LocalDate();

        dateStr=localDate.toString();

        Calendar cal = Calendar.getInstance();

        Date date=cal.getTime();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        timeStr=dateFormat.format(date);


     //   Toast.makeText(getActivity(), "date: "+dateStr+"Time: "+timeStr, Toast.LENGTH_SHORT).show();

        type=new ArrayList<String>();
        type.add("Planned");
        type.add("Admitted");
        type.add("Discharge");

        adapter=new ArrayAdapter<>(Objects.requireNonNull(this.getActivity()),android.R.layout.simple_spinner_item,type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StatusSpinner.setAdapter(adapter);


        StatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                caseStatusStr=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    caseStatusStr=null;
            }
        } );

        caseDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                mMonth=month+1;

                                month=month+1;

                                if(month<10) {
                                    caseDateStr = year + "-0" + month + "-" + dayOfMonth;
                                }
                                else
                                {
                                    caseDateStr = year + "-0" + month + "-" + dayOfMonth;
                                }

                                if (!caseDateStr.isEmpty())
                                {
                                    try {
                                        Date date =new SimpleDateFormat("dd/MM/yyyy").parse(caseDateStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    caseDateBtn.setText(caseDateStr);

                                }else
                                {
                                    caseDateBtn.setError("Could Not Found Date");
                                }


                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });


        admissionDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                mMonth=month+1;

                                month=month+1;

                                if(month<10) {
                                    dateOfAdmissionStr = year + "-0" + month + "-" + dayOfMonth;
                                }
                                else
                                {
                                    dateOfAdmissionStr = year + "-0" + month + "-" + dayOfMonth;
                                }

                                if (!dateOfAdmissionStr.isEmpty())
                                {
                                    try {
                                        Date date =new SimpleDateFormat("dd/MM/yyyy").parse(dateOfAdmissionStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    admissionDateBtn.setText(caseDateStr);

                                }else
                                {
                                    admissionDateBtn.setError("Could Not Found Date");
                                }


                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });




        fs.collection("CaseId").document("ID").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful())
                        {
                            trackIdStr=task.getResult().getString("CurrentID");
                            trackId.setText(trackIdStr);

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


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.setTitle("Please Wait Until Uploading Data");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                trackIdStr=trackId.getText().toString();

                trackId.setEnabled(false);

                compNameStr=compName.getText().toString();
                patientNameStr=patientName.getText().toString();
                hospitalNameStr=hospitalName.getText().toString();
                phoneNoStr=phoneNo.getText().toString();
                diseaseStr=Disease.getText().toString();

                treatingDocStr=treatingDoc.getText().toString();
                addrStr=addr.getText().toString();
                landmarkStr=landmark.getText().toString();
                stateStr=state.getText().toString();
                distStr=dist.getText().toString();
                pinStr=pin.getText().toString();
                vendorStr=vendor.getText().toString();
                remarkStr=remark.getText().toString();
                verifierStr=verifier.getText().toString();


                if(valid())
                {

                    data=new HashMap<String,Object>();


                    data.put("TrackId",trackIdStr);
                    data.put("CompanyName",compNameStr);
                    data.put("PatientName",patientNameStr);
                    data.put("DiseaseName",diseaseStr);
                    data.put("CaseStatus",caseStatusStr);
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
                    data.put("Verifier",verifierStr);
                    data.put("CaseDate",caseDateStr);
                    data.put("DateOfAdmission",dateOfAdmissionStr);
                    data.put("DateTime",dateStr+" "+timeStr);
                    data.put("Status","1");

                    fs.collection("Cases").document(trackIdStr).set(data, SetOptions.merge())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity(), "Successfully uploaded data", Toast.LENGTH_SHORT).show();

                                        pd.setMessage("Updating Track Id");

                                        Map<String,Object> data=new HashMap<>();
                                        int id=Integer.parseInt(trackIdStr);
                                        id++;

                                        data.put("CurrentID",String.valueOf(id));

                                        fs.collection("CaseId").document("ID").update(data)
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

                            Toast.makeText(getActivity(), "Could not upload date "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            pd.hide();
                            pd.dismiss();
                        }
                    });
                }



            }
        });


        return v;
    }

    private boolean valid() {

        if(trackIdStr.isEmpty())
        {
            trackId.setError("Please Enter Track Id");
            trackId.setFocusable(true);

            return false;
        }
        else if(compNameStr.isEmpty())
        {
            compName.setError("Please Enter Company Name");
            compName.setFocusable(true);

            return  false;
        }
        else if(patientNameStr.isEmpty())
        {
            patientName.setError("Please Enter Patient name");
            patientName.setFocusable(true);
            return false;
        }
        else if(diseaseStr.isEmpty())
        {
            Disease.setError("Please Enter Disease name");
            Disease.setFocusable(true);
            return false;
        }else if(caseStatusStr==null || caseStatusStr.isEmpty())
        {
            Toast.makeText(getActivity(), "Please Select Case Status", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(hospitalNameStr.isEmpty())
        {

            hospitalName.setError("Please Enter Hospital Name");
            hospitalName.setFocusable(true);
            return false;
        }
        else if(phoneNoStr.isEmpty())
        {
            phoneNo.setError("Please Enter Phone No.");
            phoneNo.setFocusable(true);
            return false;
        }
        else if(treatingDocStr.isEmpty())
        {
            treatingDoc.setError("Please Enter Doctor Name");
            treatingDoc.setFocusable(true);
            return false;
        }
        else if(addrStr.isEmpty())
        {
            addr.setError("Please Enter Address");
            addr.setFocusable(true);
            return false;
        }
        else if(landmarkStr.isEmpty())
        {
            landmark.setError("Please Enter Landmark");
            landmark.setFocusable(true);

            return false;
        }
        else if (stateStr.isEmpty())
        {
            state.setError("Please Enter State Name");
            state.setFocusable(true);
            return false;
        }
        else if(distStr.isEmpty())
        {
            dist.setError("Please Enter District Name");
            dist.setFocusable(true);

            return false;
        }
        else if(pinStr.isEmpty())
        {
            pin.setError("Please Enter Pin");
            pin.setFocusable(true);

            return false;
        }
        else if(vendorStr.isEmpty())
        {
            vendor.setError("Please Enter  Vendor Name");
            vendor.setFocusable(true);

            return false;
        }
        else if (caseDateStr.isEmpty())
        {
            caseDateBtn.setError("{Please Select date");

            return false;
        }
        else if(dateOfAdmissionStr.isEmpty())
        {
            admissionDateBtn.setError("Please Select Date");
            return false;
        }
        else if(dateStr.isEmpty() || timeStr.isEmpty())
        {
            Toast.makeText(getActivity(), "Could not get date time", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(remarkStr.isEmpty())
        {
            remark.setError("Please Enter Remark");
            remark.setFocusable(true);

            return false;
        }
        else if(verifierStr.isEmpty())
        {
            verifier.setError("Please Enter Verifier Name");
            verifier.setFocusable(true);

            return false;
        }

        return true;
    }


}
