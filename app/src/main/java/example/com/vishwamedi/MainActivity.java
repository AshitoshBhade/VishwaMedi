package example.com.vishwamedi;

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

public class MainActivity extends AppCompatActivity {


    private CasePostFragment casePostFragment;
    private BottomNavigationView navigationView;
    private android.support.v7.widget.Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigationView = findViewById(R.id.bottom_navigation);
        mtoolbar = findViewById(R.id.MainToolbar);

        setSupportActionBar(mtoolbar);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.HomeNav:
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

              //SignOut();
                return true;

          default: return false;
      }
    }
}