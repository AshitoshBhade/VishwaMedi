package example.com.vishwamedi;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import javax.annotation.Nullable;

import example.com.vishwamedi.model.CasePostModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientPendingCases extends Fragment {


    private ArrayList<CasePostModel> list;
    private HomeAdapter adapter;
    private FetchExcelFragment fetchExcelFragment;
    private RecyclerView recyclerView;
    private FirebaseFirestore fs;
    private ProgressDialog pd;

    private String Email;

    public ClientPendingCases() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_client_pending_cases, container, false);


        fs=FirebaseFirestore.getInstance();
        pd=new ProgressDialog(getActivity());

        list=new ArrayList<>();

        adapter=new HomeAdapter("ClientFrag", getActivity(),list, Objects.requireNonNull(getActivity()).getSupportFragmentManager());

        Email= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();



        recyclerView=v.findViewById(R.id.ClientCasesRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        pd.setMessage("Wait Until Loading Cases");
        pd.setCanceledOnTouchOutside(false);
        pd.show();




        //Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
        fs.collection("Agent").document(Email).collection("Cases")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.e("Error: " + e.getMessage(), "Error");
                } else {
                    for (DocumentChange doc : Objects.requireNonNull(queryDocumentSnapshots).getDocumentChanges()) {
                        if (doc.getType().equals(DocumentChange.Type.ADDED)) {

                            if(Objects.equals(doc.getDocument().getString("Status"), "1")) {
                                CasePostModel model = doc.getDocument().toObject(CasePostModel.class);
                                list.add(model);
                            }

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
