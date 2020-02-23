package com.example.darshanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;
import static java.sql.DriverManager.println;

public class indexInfo extends AppCompatActivity  {

    Button btnStart;
    private static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_info);
        btnStart = findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click","click");
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if (ContextCompat.checkSelfPermission(indexInfo.this, CAMERA) == PackageManager.PERMISSION_DENIED) {
                        Log.i("denied","denied");
                        ActivityCompat.requestPermissions(indexInfo.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    }
                    if (ContextCompat.checkSelfPermission(indexInfo.this, CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Log.i("permission","permission");
                        nextActivity();
                    }
                }
                else{

                }
            }
        });
    }

    void nextActivity()
    {
        println("netxtAcitivit");
        Intent intent =new Intent(this,Scanner.class);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    nextActivity();
            }
            else
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are You Sure!");
                builder.setMessage("Either Give Permission or Close the Application");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(indexInfo.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

                builder.create();
                builder.show();
            }
        }

    }



}




