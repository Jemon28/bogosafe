package co.edu.unipanamericana.bogosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private EditText mEditTextMailLogin;
    private EditText mEditTextPasswordLogin;

    private Button mButtonSingup;
    private Button mButtonLogin;

    private String email;
    private String password;

    private FirebaseAuth mAuth;

    private static String TAG = "BogoSafe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mEditTextMailLogin = (EditText) findViewById(R.id.editTextMailLogin);
        mEditTextPasswordLogin = (EditText) findViewById(R.id.editTextPasswordLogin);
        mButtonSingup = (Button) findViewById(R.id.btnSingup);
        mButtonLogin = (Button) findViewById(R.id.btnLogin);

        mButtonSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEditTextMailLogin.getText().toString();
                password = mEditTextPasswordLogin.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    singIn();
                } else {
                    Toast.makeText(MainActivity.this, "Campos de Texto obigatorio", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void singIn(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this, MapsActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
