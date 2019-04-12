package example.com.vishwamedi;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;
import example.com.vishwamedi.model.AddUserModel;
import example.com.vishwamedi.model.CasePostModel;


public class Profile_Fragment extends Fragment {

    private View rootView;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore fs;
    private ProgressDialog pd;
    private TextView u_name,u_phno,u_email;
    private CircleImageView u_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_profile_, container, false);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        fs=FirebaseFirestore.getInstance();

        pd=new ProgressDialog(getActivity());
        pd.setTitle("Please wait..");
        pd.setMessage("Loading.....");
        pd.setCancelable(false);
        pd.show();
        u_name=rootView.findViewById(R.id.txt_name);
        u_email=rootView.findViewById(R.id.txt_email);
        u_phno=rootView.findViewById(R.id.txt_phno);

        u_profile=rootView.findViewById(R.id.profile_img);

       fs.collection("User_details").document(user.getEmail()).get()
               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if(task.isSuccessful())
                       {
                           u_email.setText("Name:- ".concat(task.getResult().getString("u_email")));
                           u_phno.setText("Phone no.:- ".concat(task.getResult().getString("u_phno")));
                           u_name.setText("Name:- ".concat(task.getResult().getString("u_name")));
                           if(!task.getResult().getString("u_url").equals("none"))
                           {
                               Picasso.get().load(task.getResult().getString("u_url")).into(u_profile);
                           }
                           pd.dismiss();
                       }
                   }
               })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "ERROR"+e, Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
        return rootView;
    }
}
