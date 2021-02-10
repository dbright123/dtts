package com.DBright.DTTS;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.icu.text.MessagePattern;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class textCamera extends AppCompatActivity {



    private static final int GALLERY = 0;
    private static int MenuShootImage = 0;

    private static final String TAG = "Testing";

    ImageView imageView1;
    Bitmap bitmap;

    TextView dtext;
    TextView countin ;

    public Uri mImageUri;

    boolean connected = false;

    private String [] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE",
             "android.permission.READ_PHONE_STATE", "android.permission.CAMERA"};


    public void DDisplay(String text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    AdView madView1;
    AdView madView2;
    InterstitialAd iad;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_camera);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        madView1 = findViewById(R.id.adView);
        madView2 = findViewById(R.id.adView2);

        MobileAds.initialize(this,"ca-app-pub-3041136835684961~8023196183");
        adRequest = new AdRequest.Builder().build();
        madView1.loadAd(adRequest);
        madView2.loadAd(adRequest);

        iad = new InterstitialAd(this);
        iad.setAdUnitId("ca-app-pub-3041136835684961/8523025105");
        iad.loadAd(new AdRequest.Builder().build());
        madView1.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e(TAG, "This is why: " + errorCode);
            }
        });
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); // to help fix FileUriExposedException error
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                    // path = saveImage(bitmap);
                    imageView1 = (ImageView) findViewById(R.id.imageView);
                    imageView1.setImageBitmap(bitmap);
                    dtext = (TextView) findViewById(R.id.textView);
                    final String dImageText1 = cImage(bitmap);
                    dtext.setText(dImageText1);



                    countin = (TextView) findViewById(R.id.Counter);
                    countin.setVisibility(View.VISIBLE);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {

                            try {
                                for(int x = 5; x >= 0; x --){

                                    countin.setText(String.valueOf(x));
                                    Thread.sleep(1000);
                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent read = new Intent(textCamera.this,MainActivity2.class);
                            read.putExtra("message", dImageText1);
                            startActivity(read);

                        }
                    } ).start();





                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(textCamera.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (1 == MenuShootImage || requestCode == MenuShootImage) {
            try {
                imageView1 = (ImageView) findViewById(R.id.imageView);
                grabImage(imageView1);
            }catch(Exception e){
                Log.e("Testing","Not working");
                Log.e("Testing", String.valueOf(e));
                DDisplay("Something is wrong processing image from camera please snap it and select image from gallery");

            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();


    };

    public void pickImage(View v) {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //internet connection is enable
            connected = true;
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY);
        }
        else{
            connected = false;
            DDisplay("Your system is currently not connected to internet");
        }


    };
// will continue later

    public String cImage(Bitmap bitmap){


            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();


            Frame imageFrame = new Frame.Builder()

                    .setBitmap(bitmap)                 // your image bitmap
                    .build();

            String imageText = "";


            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                imageText += textBlock.getValue();
                imageText += "\n";
                // return string
            }
            return imageText;

    }
    public void snapPicx(View v){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File photo ;
            try
            {
                // place where to store camera taken picture
                //photo = this.createTemporaryFile("picture", ".jpg");
                File RootDir = new File(Environment.getExternalStorageDirectory() + File.separator + "Pictures");
                RootDir.mkdir();
                if (!RootDir.exists()) {
                    RootDir.mkdirs();
                }
                photo = new File(
                        RootDir, "Picture.jpg");


                photo.delete();
                //
                //mImageUri = Uri.fromFile(photo);
                mImageUri = PhotoProvider.getPhotoUri(photo);
                /*ContentResolver cr = getContentResolver();
                cr.notifyChange(mImageUri, null);*/
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                //start camera intent
                MenuShootImage = 1;
                startActivityForResult(intent, MenuShootImage);// more work still have to be done here

            }
            catch(Exception e)
            {
                Log.v(TAG, "Can't create file to take picture!");
                Log.e("Issues"," unable to use camera ");
                Log.e("Issues ", String.valueOf(e));


            }

        }
        else{
            connected = false;
            DDisplay("Your system is currently not connected to internet");
        }



    }

    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }
    public void grabImage(ImageView imageView)
    {
        this.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = this.getContentResolver();
        //Bitmap bitmap;
        try
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr,mImageUri);
                imageView.setImageBitmap(bitmap);
                dtext = (TextView) findViewById(R.id.textView);
                final String dImageText1 = cImage(bitmap);
                dtext.setText(dImageText1);
                countin = (TextView) findViewById(R.id.Counter);
                countin.setVisibility(View.VISIBLE);
                new Thread(new Runnable(){
                    @Override           
                    public void run() {

                        try {
                            for(int x = 5; x >= 0; x --){

                                countin.setText(String.valueOf(x));
                                Thread.sleep(1000);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent read = new Intent(textCamera.this,MainActivity2.class);
                        read.putExtra("message", dImageText1);
                        startActivity(read);

                    }
                } ).start();
            }
            else{
                connected = false;
                DDisplay("Your system is currently not connected to internet");
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to load", e);
        }

    }
    /*
     ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            }
        else{
            connected = false;
            DDisplay("Your system is currently not connected to internet");
            }
     */


}