package com.example.android0211;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android0211.Retrofit.INodeJS;
import com.example.android0211.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.button.MaterialButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.google.android.material.button.MaterialButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialEditText edt_email, edt_password;
    MaterialButton btn_register, btn_login;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);


        //View
        btn_login = findViewById(R.id.login_button);
        btn_register = findViewById(R.id.register_button);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);

        btn_login.setOnClickListener(v -> loginUser(edt_email.getText().toString(), edt_password.getText().toString()));

        btn_register.setOnClickListener(v -> registerUser(edt_email.getText().toString(),edt_password.getText().toString()));


    }
    //파라미터 안에 final String macAdd

    private void registerUser(final String email, final String password) {
        final View enter_name_view = LayoutInflater.from(this).inflate(R.layout.enter_name_layout,null);

        new MaterialStyledDialog.Builder(this)
                .setTitle("Register")
                .setDescription("One more step!")
                .setCustomView(enter_name_view)
                .setIcon(R.drawable.ic_user)
                .setNegativeText("Cancel")
                .onNegative((dialog, which) -> dialog.dismiss())

                .setPositiveText("Register")
                .onPositive((dialog, which) -> {

                    MaterialEditText edt_name= enter_name_view.findViewById(R.id.edt_name);

                    compositeDisposable.add(myAPI.registerUser(email,edt_name.getText().toString(),password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(str -> showToast(str)));

                }).show();

    }

    private void showToast(@NonNull final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void loginUser(String email, String password) {
        compositeDisposable.add(myAPI.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if(s.contains("encrypted_password")){
                        Toast.makeText(MainActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                        //이메일넘김
                        Intent intent = new Intent(getApplicationContext(),SelectActivty.class);
                        intent.putExtra("email",email);


                        startActivity(intent);

                    }
                    else
                        Toast.makeText(MainActivity.this,""+s,Toast.LENGTH_SHORT).show();

                })
        );
    }

    //기기의 맥주소 출력

    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();

                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();

                for (int idx=0; idx<mac.length; idx++)

                    buf.append(String.format("%02X:", mac[idx]));

                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);

                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions

        return "";
    }



    String HardwareAdd = getMACAddress("wlan0");//여기에 저장





}
