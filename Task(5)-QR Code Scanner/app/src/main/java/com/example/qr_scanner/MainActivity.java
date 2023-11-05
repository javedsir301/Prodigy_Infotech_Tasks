package com.example.qr_scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.ScanMode;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    private TextView codeData;
    private CodeScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeData=findViewById(R.id.text_code);
        scannerView=findViewById(R.id.scanner_view);

        Objects.requireNonNull(getSupportActionBar()).hide();

        int PERMISSION_ALL=1;
        String[] PERMISSIONS={
                Manifest.permission.CAMERA
        };

        if (!hasPermissions(this,PERMISSIONS))
            ActivityCompat.requestPermissions(this,PERMISSIONS,PERMISSION_ALL);
        else
          runCodeScanner();
    }

    public void runCodeScanner() {
        CodeScanner codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.startPreview();

        codeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            String data=result.getText();
            codeData.setText(data);
        }));

    }

    public static boolean hasPermissions(Context context,String... permissions){
        if (context!=null && permissions!=null){
            for (String permission:permissions){
                if (ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }
}