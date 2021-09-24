package com.example.adminpart;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminLoginActivity extends AppCompatActivity {


    private TextInputEditText userNameEdt1, passwordEdt1;
    private TextView registerTV1;
    private Button loginBtn1;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        userNameEdt1 = findViewById(R.id.idEdtUserName1);
        passwordEdt1 = findViewById(R.id.idEdtPassword1);
        loginBtn1 = findViewById(R.id.idBtnLogin);
        registerTV1 = findViewById(R.id.idTVNewUser1);
        mAuth = FirebaseAuth.getInstance();
        loadingPB1 = findViewById(R.id.idPBLoading1);


        registerTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminLoginActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
        loginBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingPB1.setVisibility(View.VISIBLE);

                String email = userNameEdt1.getText().toString();
                String password = passwordEdt1.getText().toString();


                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(AdminLoginActivity.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            loadingPB1.setVisibility(View.GONE);
                            Toast.makeText(AdminLoginActivity.this, "Admin  Login Successful..", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(AdminLoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();


                        }else{

                            loadingPB1.setVisibility(View.GONE);
                            Toast.makeText(AdminLoginActivity.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            Intent i = new Intent(AdminLoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}