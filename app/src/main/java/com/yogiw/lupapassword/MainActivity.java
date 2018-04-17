package com.yogiw.lupapassword;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    // Binding objek di XML mnenggunakan butterknife, jadi ga perlu pake findview
    TextView txtBack;
    Button btnForget;
    EditText edtEmail;
    CoordinatorLayout coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBack = findViewById(R.id.txtBack);
        btnForget = findViewById(R.id.btnForget);
        edtEmail = findViewById(R.id.edtEmail);
        coordinator = findViewById(R.id.coordinator);

        txtBack.setText("< Back");
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().equals("")) { // kalau kolom emailnya kosong tampilih pesan
                    Snackbar snackbar = Snackbar
                            .make(coordinator, "Tolong isi email anda!", Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else { // kalau tidak kosong kirim email menggunakan firebase
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = edtEmail.getText().toString();

                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) { // kalau berhasil
                                        Snackbar snackbar = Snackbar
                                                .make(coordinator, "Email telah terkirim!", Snackbar.LENGTH_LONG);

                                        snackbar.show();

                                    } else { // kalau gagal
                                        Snackbar snackbar = Snackbar
                                                .make(coordinator, "Gagal mengirim email", Snackbar.LENGTH_LONG);

                                        snackbar.show();

                                    }
                                }
                            });
                }
            }
        });
    }

}
