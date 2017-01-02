package com.example.woko_app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.fragment.PersonalDataFragment;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.Tenant;

public class SignatureActivity extends Activity {

    private Tenant tenant = null;
    private AP ap = null;
    private String name = null;

    private SignatureView signatureView;
    private Button btnDone;
    private Button btnClear;
    private Button btnStop;
    private TextView txtSignature;
    private RelativeLayout sView;

    private Typeface font;
    private PersonalDataFragment personalDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        intentReceiver();

        // fontawesome
        font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        sView = (RelativeLayout)findViewById(R.id.signatureContainer);
        this.signatureView = new SignatureView(this);

        txtSignature = (TextView)findViewById(R.id.txtSignature);
        if (null != tenant) {
            txtSignature.setText(R.string.signature_oldTenant);
        } else if (null != ap) {
            if (name.equals("tenant")) {
                txtSignature.setText(R.string.signature_newTenant);
            } else
                txtSignature.setText(R.string.signature_woko);
        }

        btnStop = (Button)findViewById(R.id.btnStop);
        btnStop.setTypeface(font);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalDataFragment.updateSignatureImage();
                finish();
            }
        });
        btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setTypeface(font);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearSignature();
            }
        });
        btnDone = (Button)findViewById(R.id.btnDone);
        btnDone.setTypeface(font);
        btnDone.setEnabled(false);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage(signatureView.getSignature());
                PersonalDataFragment.updateSignatureImage();
                finish();
            }
        });

        sView.addView(signatureView);
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * save the signature in the db as a picture (byte[])
     * check if it is the signature of the tenantOld, tenant or woko
     * @param bitmap
     */
    final void saveImage(Bitmap bitmap) {
        if (null != tenant) {
            tenant.setSignature(PersonalSerializer.getBytes(bitmap));
            tenant.save();
        }
        if (null != ap) {
            if (name.equals("tenant")) {
                ap.setSignatureNewTenant(PersonalSerializer.getBytes(bitmap));
                ap.save();
            }
            if (name.equals("woko")) {
                ap.setSignatureWoko(PersonalSerializer.getBytes(bitmap));
                ap.save();
            }
        }
    }

    /**
     * get the data form personal data fragment
     */
    private void intentReceiver() {
        Intent intent = getIntent();
        tenant = Tenant.findById(intent.getLongExtra("tenant id", 111110));
        ap = AP.findById(intent.getLongExtra("ap id", 100010100));
        name = intent.getStringExtra("name");
    }

    /**
     * Signature class
     * touchEvent
     */
    private class SignatureView extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public SignatureView(Context context) {
            super(context);

            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);

        }

        /**
         * reads the drawn signature and put it in a bitmap
         * @return
         */
        protected Bitmap getSignature() {
            Bitmap signatureBM = null;

            if (signatureBM == null) {
                signatureBM = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.RGB_565);
            }

            final Canvas canvas = new Canvas(signatureBM);
            canvas.drawColor(Color.WHITE);
            this.draw(canvas);

            return signatureBM;
        }

        /**
         * deletes the drawn signature
         */
        private void clearSignature() {
            path.reset();
            this.invalidate();
            btnDone.setEnabled(false);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(this.path, this.paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            btnDone.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:
                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);

                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
}
