package example.com.vishwamedi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private CasePostFragment casePostFragment;
    private HomeFragment homeFragment;
    private BottomNavigationView navigationView;
    private android.support.v7.widget.Toolbar mtoolbar;
    private ProgressDialog pd;
    private String userEmail;
    private FirebaseFirestore fs;
    private ClientPendingCases clientPendingCases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);

        navigationView = findViewById(R.id.bottom_navigation);
        mtoolbar = findViewById(R.id.MainToolbar);

        setSupportActionBar(mtoolbar);

        fs=FirebaseFirestore.getInstance();

        pd=new ProgressDialog(this);

        userEmail= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.HomeNav:

                            pd.setMessage("Wail until Loading");
                            pd.setCanceledOnTouchOutside(false);
                            pd.show();

                        fs.collection("User_details").document(userEmail)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if(task.isSuccessful())
                                        {
                                           String frag=task.getResult().getString("u_panel");

                                           if(Objects.requireNonNull(frag).equals("agent"))
                                           {
                                               clientPendingCases=new ClientPendingCases();
                                               setFragment(clientPendingCases);

                                               Toast.makeText(MainActivity.this, "Agent", Toast.LENGTH_SHORT).show();
                                               pd.dismiss();
                                           }
                                           else
                                           {
                                               homeFragment=new HomeFragment();
                                               setFragment(homeFragment);
                                               Toast.makeText(MainActivity.this, "Central", Toast.LENGTH_SHORT).show();
                                               pd.dismiss();
                                           }
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                pd.dismiss();
                            }
                        });

                        return true;

                    case R.id.PostCaseNav:
                        casePostFragment = new CasePostFragment();
                        setFragment(casePostFragment);
                        return true;

                    case R.id.AccountNav:
                        return true;

                    default: return false;

                }


            }
        });


    }

    private void setFragment(Fragment fragment) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .add(fragment,"PostCaseFragment").addToBackStack("PostCaseFragment");
        fragmentTransaction.replace(R.id.MainLayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);

       getMenuInflater().inflate(R.menu.main_menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      super.onOptionsItemSelected(item);

      switch (item.getItemId())
      {
          case R.id.logoutMenu:

                SignOut();
                return true;

          default: return false;
      }
    }

    private void SignOut() {

        FirebaseAuth.getInstance().signOut();

        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}