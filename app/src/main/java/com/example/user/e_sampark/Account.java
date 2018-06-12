package com.example.user.e_sampark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import java.io.IOException;

public class Account extends AppCompatActivity {

    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabase;
    EditText fName;
    EditText lName;
    EditText age;
    EditText number;
    private int buttonType=1;
    private Button btn;
    private Button upload;
    private static final int GALLERY_PICK=1;
    private ProgressDialog mProgressDialog;
    private StorageReference mImageStorage;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mImageStorage= FirebaseStorage.getInstance().getReference();

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        fName=(EditText)findViewById(R.id.fName);
        lName=(EditText)findViewById(R.id.lName);
        age=(EditText)findViewById(R.id.age);
        number=(EditText)findViewById(R.id.number);
        btn=(Button)findViewById(R.id.next);
        upload=(Button)findViewById(R.id.upload);
        mImage=(ImageView)findViewById(R.id.image);

        String pNumber=mCurrentUser.getPhoneNumber();
        number.setText(pNumber);
        number.setEnabled(false);

        String cur_id=mCurrentUser.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(cur_id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String comp=dataSnapshot.child("Completed").getValue().toString();
                if(comp.equals("Yes"))
                {
                    String first=dataSnapshot.child("First_Name").getValue().toString();
                    String last=dataSnapshot.child("Last_Name").getValue().toString();
                    String um=dataSnapshot.child("Age").getValue().toString();
                    String image=dataSnapshot.child("image").getValue().toString();
                    Picasso.with(Account.this).load(image).placeholder(R.drawable.images).into(mImage);
                    upload.setEnabled(false);
                    fName.setText(first);
                    lName.setText(last);
                    age.setText(um);
                    fName.setEnabled(false);
                    lName.setEnabled(false);
                    age.setEnabled(false);
                    btn.setEnabled(false);
                }
                else
                {
                    no();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void no()
    {
        upload.setEnabled(true);
        btn.setEnabled(false);
        fName.setEnabled(false);
        lName.setEnabled(false);
        age.setEnabled(false);
    }

    public void next(View v)
    {
        if(buttonType==1)
        {
            final String first=fName.getText().toString();
            if(!first.equals("")) {
                mDatabase.child("First_Name").setValue(first).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        lName.setEnabled(true);
                        buttonType++;
                        fName.setText(first);
                        fName.setEnabled(false);
                        btn.setEnabled(true);
                        upload.setEnabled(false);
                    }
                });
            }
        }

        else if(buttonType==2)
        {
            final String name=lName.getText().toString();
            if(!name.equals("")) {
                mDatabase.child("Last_Name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        age.setEnabled(true);
                        buttonType++;
                        lName.setText(name);
                        lName.setEnabled(false);
                        btn.setEnabled(true);
                        upload.setEnabled(false);
                    }
                });
            }
        }

        else if(buttonType==3)
        {
           final String ag=age.getText().toString();
            if(!ag.equals("")) {
                mDatabase.child("Age").setValue(ag).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        age.setText(ag);
                        age.setEnabled(false);
                        mDatabase.child("Completed").setValue("Yes").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                btn.setEnabled(false);
                                upload.setEnabled(false);
                                Toast.makeText(Account.this, "Account Information Uploaded", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }
    }

    public void upload(View v)
    {
        Intent galleryIntent=new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"),GALLERY_PICK);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==GALLERY_PICK && resultCode==RESULT_OK)
        {
            Uri imageUri=data.getData();
            CropImage.activity(imageUri).setAspectRatio(1,1).start(this);
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            mProgressDialog=new ProgressDialog(Account.this);
            mProgressDialog.setTitle("Uploading Image");
            mProgressDialog.setMessage("Please Wait While We Upload The Image");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK)
            {
                Uri resultUri=result.getUri();
                try {
                    StorageReference filepath=mImageStorage.child("profile_images").child(mCurrentUser.getUid()+".jpg");
                    filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()) {
                                final String download_url = task.getResult().getDownloadUrl().toString();
                                mDatabase.child("image").setValue(download_url).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Picasso.with(Account.this).load(download_url).placeholder(R.drawable.images).into(mImage);
                                        mProgressDialog.dismiss();
                                        upload.setEnabled(false);
                                        btn.setEnabled(true);
                                        fName.setEnabled(true);
                                    }
                                });
                                mProgressDialog.hide();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
