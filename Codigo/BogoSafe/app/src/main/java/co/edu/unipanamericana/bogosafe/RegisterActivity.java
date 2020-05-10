package co.edu.unipanamericana.bogosafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import co.edu.unipanamericana.bogosafe.modelo.Usuario;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private EditText mEditTextMail;
    private EditText mEditTextPassword;
    private Button mButtomRegister;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String name;
    private String email;
    private String password;

    private static String TAG="BogoSafe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        mEditTextName = (EditText) findViewById(R.id.editTextMailLogin);
        mEditTextMail = (EditText) findViewById(R.id.editTextMail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        mButtomRegister = (Button) findViewById(R.id.btnRegister);

        mButtomRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEditTextName.getText().toString();
                email = mEditTextMail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if (password.length() >= 6) {
                        createAccount();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Contraseña poco segura", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Campos de Texto obigatorio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void createAccount(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Usuario u = new Usuario(name, email);
                            String id = mAuth.getCurrentUser().getUid();

                            db.collection("Usuarios").document(id).set(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    startActivity(new Intent(RegisterActivity.this, MapsActivity.class));
                                    finish();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
