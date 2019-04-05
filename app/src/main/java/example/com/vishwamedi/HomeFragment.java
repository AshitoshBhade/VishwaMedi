package example.com.vishwamedi;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import example.com.vishwamedi.model.CasePostModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private ArrayList<CasePostModel> list;
    private HomeAdapter adapter;
    private FetchExcelFragment fetchExcelFragment;
    private RecyclerView recyclerView;
    private FirebaseFirestore fs;
    private ProgressDialog pd;
    private CasePostFragment casePostFragment;
    private FloatingActionButton addpostBtn;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        fs=FirebaseFirestore.getInstance();
        pd=new ProgressDialog(getActivity());

        list=new ArrayList<>();

        adapter=new HomeAdapter(getActivity(),list, Objects.requireNonNull(getActivity()).getSupportFragmentManager());




        recyclerView=v.findViewById(R.id.CasesRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        pd.setMessage("Wait Until Loading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();


        addpostBtn=v.findViewById(R.id.AddPostBtn);


        addpostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                casePostFragment=new CasePostFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .add(casePostFragment,"PostCaseFragment").addToBackStack("PostCaseFragment");
                fragmentTransaction.replace(R.id.MainLayout, casePostFragment);
                fragmentTransaction.commit();
                */

                fetchExcelFragment=new FetchExcelFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .add(fetchExcelFragment,"fetchCsvFragment").addToBackStack("fetchCsvFragment");

                fragmentTransaction.replace(R.id.MainLayout, fetchExcelFragment);
                fragmentTransaction.commit();

            }
        });


        //Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
        fs.collection("Cases").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.e("Error: " + e.getMessage(), "Error");
                } else {
                    for (DocumentChange doc : Objects.requireNonNull(queryDocumentSnapshots).getDocumentChanges()) {
                        if (doc.getType().equals(DocumentChange.Type.ADDED)) {

                            CasePostModel model = doc.getDocument().toObject(CasePostModel.class);
                            list.add(model);

                            adapter.notifyDataSetChanged();
                        }
                    }

                    pd.hide();
                    pd.dismiss();
                }
            }
        });
        return v;
    }

}
