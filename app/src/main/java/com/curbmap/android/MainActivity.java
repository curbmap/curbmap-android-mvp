package com.curbmap.android;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import com.google.openlocationcode.OpenLocationCode;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Environment.isExternalStorageEmulated;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.camera_kit)
    CameraView camera;

    @BindView(R.id.capture_button)
    AppCompatButton capture;

    OpenLocationCode locationCode;
    //ExifInterface exifInterface;
    byte[] imageJpg;
    Bitmap imageBitmap;
    FileOutputStream outputStream;
    int count = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        camera.setMethod(CameraKit.Constants.METHOD_STANDARD);
        camera.setPermissions(CameraKit.Constants.PERMISSIONS_PICTURE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onResume(){
        super.onResume();
        camera.start();
    }

    @Override
    protected void onPause(){
        camera.stop();
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @OnClick(R.id.capture_button)
    void doCapture(){
        Log.d(TAG, "doCapture button");
        camera.captureImage(new CameraKitEventCallback<CameraKitImage>() {
            @Override
            public void callback(CameraKitImage cameraKitImage) {
                imageJpg = compressToJpeg(cameraKitImage.getBitmap());
                writeToCacheDir(imageJpg, "temp");
            }
        });


    }

    private byte[] compressToJpeg(Bitmap in){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        in.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    //TODO: Set Date As File Name.
    private void writeToCacheDir(byte[] input, String filename){
        String PATH;

        /*Checks for External Storage before writing.*/
        if(Environment.isExternalStorageEmulated()){
            PATH = getExternalCacheDir().getPath();
        }else {
            PATH = getCacheDir().getPath();
        }

        File pictures = new File( PATH, "images");
        pictures.mkdirs();
        File image = new File( pictures, filename + ".jpg");

        Log.d(TAG, "Cache Location: " + image.getParentFile().getAbsolutePath());
        try {
            if(image.exists()){
                image.delete();
                image.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(image);
            outputStream.write(input);
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }

}
