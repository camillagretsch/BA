package com.example.woko_app.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cete.dynamicpdf.Document;
import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.PDFCreator;
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.WallState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OpenActivity extends Activity {

    private AP currentAP;
    private Document document;
    private static String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        intentReceiver();
        path = Environment.getExternalStorageDirectory() + "/Abnahmeprotokoll_" + currentAP.getName();
        createPDF();
        openPDF();


    }

    /**
     * get data from home activity
     */
    private void intentReceiver() {
        Intent intent = getIntent();
        currentAP = AP.findById(intent.getLongExtra("ID", 0000000));
    }

    /**
     * open the created pdf
     */
    private void openPDF() {
        Uri uri = Uri.fromFile(new File(path));
        try
        {
            Intent intentUrl = new Intent(Intent.ACTION_VIEW);
            intentUrl.setDataAndType(uri, "application/pdf");
            intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentUrl);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * create the pdf with all the entries from the edit activity
     */
    private void createPDF() {
        document = new Document();

        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.cross)).getBitmap();
        byte[] cross = PersonalSerializer.getBytes(bitmap);

        bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.logo_gross)).getBitmap();
        byte[] logo = PersonalSerializer.getBytes(bitmap);

        PDFCreator pdfCreator = new PDFCreator(currentAP, document, getResources().getStringArray(R.array.header_variante1), getResources().getStringArray(R.array.header_variante2), getResources().getString(R.string.edit_title), cross, logo);
        pdfCreator.create();

        try {
            document.draw(path);
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT);
        }

    }
}
