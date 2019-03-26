package example.com.vishwamedi;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CasePostFragment extends Fragment {


    private Button postCaseBtn,caseDateBtn,admissionDateBtn,submitBtn;

    private EditText trackId,compName,patientName,hospitalName,phoneNo,
            treatingDoc,addr,landmark,state,dist,pin,vendor,remark,verifier;

    private CasePostFragment casePostFragment;


    String caseDateStr,dateOfAdmissionStr;
    int mYear,mMonth,mDay;


    public CasePostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_case_post, container, false);


        trackId=v.findViewById(R.id.TrackId);
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


        caseDateBtn=v.findViewById(R.id.CaseDateButton);
        admissionDateBtn=v.findViewById(R.id.DateOfAdmissionButton);
        submitBtn=v.findViewById(R.id.SubmitButton);


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



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return v;
    }




}
