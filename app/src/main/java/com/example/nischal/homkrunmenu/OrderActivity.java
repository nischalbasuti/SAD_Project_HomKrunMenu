package com.example.nischal.homkrunmenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

public class OrderActivity extends AppCompatActivity {

    ImageView QRCodeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        QRCodeIv = findViewById(R.id.QRCodeIv);
        String orderJsonString = getIntent().getExtras().getString("OrderJsonString");
        this.getSupportActionBar().setTitle("Order Summary");
        ((TextView)findViewById(R.id.orderSummaryTv)).setText(orderJsonString);

        // Create Message Authentication Code for order data
        String orderCompactJws = Jwts.builder()
                .setSubject(orderJsonString)
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.encode("secret"))
                .compact();

        // Generate QR code represents the encoded order
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(orderCompactJws, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            QRCodeIv.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

//        Toast.makeText(this, orderJsonString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

}
