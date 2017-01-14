package com.blooddonation.blooddonation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.UserDAO;


public class ScanActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String photo,email;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView mImageView1;
    private ImageView mImageView2;


    private LinearLayout linearLayout;
    private Button but_photo;
    Uri contentUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mImageView1 = new ImageView(this);
        mImageView2 = new ImageView(this);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        but_photo = (Button) findViewById(R.id.photo);

        but_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
       // photo = GetString("photo");
        //Log.i("photo", photo);
        if(GetString("photo") !="")
        {
            File file = new File(GetString("photo"));
            mImageView2.setImageURI(Uri.fromFile(file));
            linearLayout.addView(mImageView2);
            mImageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(contentUri, "image/*");
                    startActivityForResult(intent, 10);
                }
            });
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photo = image.getAbsolutePath();

        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                    photoFile = createImageFile();
                email = GetString("email");
                UserDAO u = new UserDAO(getApplicationContext());
                u.open();
                if(u!=null)
                 u.AddPhoto(email, photo);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                galleryAddPic();
                contentUri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }

        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File file = new File(photo);
            saveString("photo",photo);
            mImageView1.setImageURI(Uri.fromFile(file));
            linearLayout.addView(mImageView1);
            mImageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(contentUri, "image/*");
                    startActivityForResult(intent, 10);
                }
            });

//            for(int i=0 ; i<images.size() ; i++)
//            {
//                linearLayout.addView(images.get(i));
//            }
//
//            mImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //ouvrir l'image avec le visionneur
//                }
//            });
        }
    }
    public String GetString(String key)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(key, "");
    }
    public void saveString(String key, String value)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
