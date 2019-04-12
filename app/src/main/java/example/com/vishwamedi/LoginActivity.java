package example.com.vishwamedi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_google_login,btn_email_login;
    private EditText edt_email,edt_pass;
    private GoogleSignInClient mGoogleSignInClient;
    private String gmail;
    private ProgressDialog pd;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        btn_google_login=findViewById(R.id.google_sign_in);
        btn_google_login.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        pd=new ProgressDialog(this);
    }
    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                gmail=account.getEmail();
                firebaseAuthWithGoogle(account);
            }
            catch (ApiException e)
            {

            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        pd.setMessage("please wait");
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            pd.show();
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            Toast.makeText(LoginActivity.this, ""+isNew, Toast.LENGTH_SHORT).show();
                            if(isNew) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "New User.....", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,Registration.class);
                                intent.putExtra("email",user.getEmail());
                                startActivity(intent);
                                pd.dismiss();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "LOGIN SUCCESSFULLY WITH GOOGLE", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                pd.dismiss();
                            }

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "PLEASE TRY AGAIN LATER", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
            if (view == btn_google_login)
            {
                signIn();
            }
    }
}
