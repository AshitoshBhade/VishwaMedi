package example.com.vishwamedi;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FetchExcelFragment extends Fragment {


    public static int EXCEL_CODE=101;

    private ProgressDialog pd;


    public FetchExcelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_fetch_excel, container, false);


        /*
        if(checkPermission()) {

            getFile();

        }else
        {
            requestPermission();
        }
        */
        getFile();

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


                    Toast.makeText(getActivity(), "path: "+path, Toast.LENGTH_SHORT).show();

                    /*
                    CSVReader csvReader = new CSVReader(new FileReader(Objects.requireNonNull(csvFilename)), ',');

                    String[] row = null;

                    while((row = csvReader.readNext()) != null) {

                        Toast.makeText(getActivity(), row[0]+row[1], Toast.LENGTH_SHORT).show();

                    }
//...
                    csvReader.close();

                    */


                    String line = "";
                    String cvsSplitBy = ",";

                    try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(path)))) {

                        while ((line = br.readLine()) != null) {

                            // use comma as separator
                            String[] temp = line.split(cvsSplitBy);

                            Toast.makeText(getActivity(), "Data: "+temp[0]+temp[1], Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(),"Error: "+e,Toast.LENGTH_LONG).show();
            Log.d("Error File",e.getMessage());

        }
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
