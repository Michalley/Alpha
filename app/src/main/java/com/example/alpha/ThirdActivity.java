package com.example.alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import static com.example.alpha.FBreff.mStorageRef;

public class ThirdActivity extends AppCompatActivity {

    private static final int PICK_IMAGE =100 ;
    Uri imageUri;
    Button btnC,btnU;
    ImageView imv;

    Intent t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        btnC= (Button) findViewById(R.id.btnC);
        btnU = (Button) findViewById(R.id.btnU);
        imv = (ImageView) findViewById(R.id.imv);

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
        }
        uploadFile();
    }

    public void uploadFile(){
        if (imageUri != null){
            StorageReference riversRef = mStorageRef.child("images/pic.jpg");
            riversRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ThirdActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(ThirdActivity.this,"Fail",Toast.LENGTH_SHORT).show();
                        }
                    });
            Toast.makeText(ThirdActivity.this,"Uploaded Successfully",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(ThirdActivity.this, "Empty File", Toast.LENGTH_SHORT).show();
        }
    }

    public void Upload(View view) throws IOException {
        /*ref = mStorageRef.child("images/pic.jpg");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                uri = Uri.parse(url);
                imv.setImageURI(uri);
                //downloadFile(ThirdActivity.this,"image","pic.jpn",DIRECTORY_DOWNLOAD,url);
            }
        });*/
        final File localFile = File.createTempFile("images","pic.jpg");
        mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                imv.setImageURI(Uri.fromFile(localFile));
            }
        });
    }

    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url){

        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request r = new DownloadManager.Request(uri);

        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        r.setDestinationInExternalFilesDir(context,destinationDirectory,fileName+fileExtension);

        imv.setImageURI(uri);
        dm.enqueue(r);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        //activity menu defined
        menu.add("Authentication");
        menu.add("Database");
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem item){
        //activity menu selection
        String st = item.getTitle().toString();
        if (st.equals("Authentication")){
            t = new Intent(this,MainActivity.class);
        }
        if (st.equals("Database")){
            t = new Intent(this,SecondActivity.class);
        }

        startActivity(t);
        return true;
    }
}
