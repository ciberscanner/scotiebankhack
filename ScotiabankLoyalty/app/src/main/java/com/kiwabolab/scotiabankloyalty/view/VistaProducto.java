package com.kiwabolab.scotiabankloyalty.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.kiwabolab.scotiabankloyalty.R;
import com.kiwabolab.scotiabankloyalty.model.Product;
import com.kiwabolab.scotiabankloyalty.model.Servidor;
import com.ramotion.fluidslider.FluidSlider;
import com.squareup.picasso.Picasso;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class VistaProducto extends Activity {
    //----------------------------------------------------------------------------------------------
    //Variables
    private ImageView img;
    private TextView title;
    private TextView puntos, precio;
    private TextView descripcion;
    FluidSlider slider;


    String TAG = "GenerateQRCode";
    EditText edtValue;
    ImageView qrImage;
    Button start, save;
    String inputValue;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    //----------------------------------------------------------------------------------------------
    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_item_catalogo);

        final TextView textView = findViewById(R.id.textView);

        img=(ImageView)findViewById(R.id.img_item_image);
        title=(TextView)findViewById(R.id.txt_item_title);
        puntos=(TextView)findViewById(R.id.txt_item_puntos);
        descripcion=(TextView)findViewById(R.id.txt_item_description);

        qrImage = (ImageView) findViewById(R.id.QR_Image);


        Product product = (Product) getIntent().getExtras().getSerializable("product");


        Picasso.with(this)
                .load(Servidor.getImage_url(product.getImg()))
                .placeholder(R.mipmap.shop)
                .error(R.mipmap.shop)
                .into(img);

        title.setText(product.getName());
        puntos.setText("Puntos: "+product.getDot());
        descripcion.setText(product.getDescription());

        int numero =Integer.parseInt(product.getDot());

        final int max = 100 ;
        final int min = 0;
        final int total = max - min;

        slider = findViewById(R.id.fluidSlider);

        slider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                textView.setVisibility(View.INVISIBLE);
                return Unit.INSTANCE;
            }
        });

        slider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                textView.setVisibility(View.VISIBLE);
                return Unit.INSTANCE;
            }
        });

        slider.setPosition(0);
        slider.setStartText(String.valueOf(min));
        slider.setEndText(String.valueOf(max));




    }

    //----------------------------------------------------------------------------------------------
    //
    public void Pagar(View view){
        Log.v("Numero",""+(slider.getPosition()*100));

        inputValue = (slider.getPosition()*100)+"";
        if (inputValue.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    inputValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        } else {
            edtValue.setError("Required");
        }
    }

    //----------------------------------------------------------------------------------------------
    //
}