package com.yogiw.lupapassword;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    // Binding objek di XML mnenggunakan butterknife, jadi ga perlu pake findview
    @BindView(R.id.txtBack)
    TextView txtBack;
    @BindView(R.id.btnForget)
    Button btnForget;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        txtBack.setText("< Back");
    }

    @OnClick(R.id.btnForget) // ketika tombol btnForget diteken
    public void forgetPass(){
        if (edtEmail.getText().toString().equals("")){ // kalau kolom emailnya kosong tampilih pesan
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

    @OnClick(R.id.txtBack)
    public void back(){ // kalau tombol back ditekan
        finish();
    }
}
