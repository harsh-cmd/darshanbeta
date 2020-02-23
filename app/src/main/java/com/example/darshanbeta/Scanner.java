package com.example.darshanbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity {
    static String BUNDLE_SCANNER_RESULT = "scannerResult";
    TextView textViewScanTotal;
    Button buttonGo;
    SurfaceView mSurface;
    BarcodeDetector barcodeDetector;
    Set<String> ResultCount;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        buttonGo = findViewById(R.id.ButtonGo);
        textViewScanTotal = findViewById(R.id.TextViewTotalScan);
        final ScannerResult scannerResult = new ScannerResult();
        mSurface = findViewById(R.id.surfaceView);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1080, 720).setAutoFocusEnabled(true).build();
        mSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(mSurface.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                final String scanner = "Scan:";
                if (qrcodes.size() != 0) {
                    scannerResult.setResultCount(qrcodes.valueAt(0).displayValue);
                    textViewScanTotal.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewScanTotal.setText(scanner + Integer.toString(scannerResult.getResultCount().size()));
                        }
                    });
                }
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Scanner.this, DisplayResult.class);
                i.putExtra("array", scannerResult);
                startActivity(i);

            }
        });

    }

}


