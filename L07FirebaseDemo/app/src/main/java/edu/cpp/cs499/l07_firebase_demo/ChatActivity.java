package edu.cpp.cs499.l07_firebase_demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @BindView(R.id.chatEditText)
    EditText chatEditText;
    @BindView(R.id.chatListView)
    ListView chatListView;

    private String chatKey;
    private List<Message> messages;

    private ChatArrayAdapter chatArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        String myUserId = UserManager.userId;
        String targetUserId = getIntent().getStringExtra("targetUserId");
        chatKey = myUserId.compareTo(targetUserId) > 0 ?
                targetUserId + "-" + myUserId : myUserId + "-" + targetUserId;

        loadChatMessage();
    }

    private void loadChatMessage() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chat/" + chatKey);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    messages.add(message);
                }
                chatArrayAdapter = new ChatArrayAdapter(ChatActivity.this, R.layout.listview_item_chat, messages);
                chatListView.setAdapter(chatArrayAdapter);

                chatListView.post(new Runnable(){
                    public void run() {
                        chatListView.setSelection(chatListView.getCount() - 1);
                    }});
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.sendButton)
    public void sendOnClicked() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Message message = new Message();
        message.setTime(System.currentTimeMillis());
        message.setMessage(chatEditText.getText().toString());
        message.setSenderId(UserManager.userId);

        DatabaseReference myRef = database.getReference("chat/" + chatKey + "/" + System.currentTimeMillis());
        myRef.setValue(message);

        chatEditText.setText("");
    }

    @OnClick(R.id.photoButton)
    public void photoButtonClicked() {
        saveChatHistoryAsImage();
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
    }

    private void saveChatHistoryAsImage() {
        chatListView.setDrawingCacheEnabled(true);
        Bitmap b = chatListView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DCIM + "/image.jpg";
            File file = new File(path);
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            b.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

            //b.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(path));
            //String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Image Description", null);
            Uri uri = Uri.parse("file:///" + path);
            Log.i("TEST", uri.toString() + " size: " + file.length());


            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Share Image"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = baos.toByteArray();

        //mImageView.setImageBitmap(imageBitmap);
        Log.i("TEST", "Successfully took the image! " + b.getByteCount());

        // upload the image to Firebase Storage

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://socialappdemo-35aca.appspot.com");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference to the image file
        final StorageReference imageRef = storageRef.child(
                "chat_photos/" + System.currentTimeMillis() + ".jpg");

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.e("TEST", "Failed to upload the photo.");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i("TEST", "Successfully uploaded the photo.");
                // get the URL
                Task<Uri> task = imageRef.getDownloadUrl();
                task.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri uri = task.getResult();
                        Log.i("TEST", "Get the download URL: " + uri.toString());



//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//                        Message message = new Message();
//                        message.setTime(System.currentTimeMillis());
//                        message.setMessage(uri.toString());
//                        message.setSenderId(UserManager.userId);
//                        message.setType(Message.IMAGE);
//
//                        DatabaseReference myRef = database.getReference("chat/" + chatKey + "/" + System.currentTimeMillis());
//                        myRef.setValue(message);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datai) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = datai.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            //mImageView.setImageBitmap(imageBitmap);
            Log.i("TEST", "Successfully took the image! " + imageBitmap.getByteCount());

            // upload the image to Firebase Storage

            FirebaseStorage storage = FirebaseStorage.getInstance("gs://socialappdemo-35aca.appspot.com");
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            // Create a reference to the image file
            final StorageReference imageRef = storageRef.child(
                    "chat_photos/" + System.currentTimeMillis() + ".jpg");

            UploadTask uploadTask = imageRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.e("TEST", "Failed to upload the photo.");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i("TEST", "Successfully uploaded the photo.");
                    // get the URL
                    Task<Uri> task = imageRef.getDownloadUrl();
                    task.addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri uri = task.getResult();
                            Log.i("TEST", "Get the download URL: " + uri.toString());

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            Message message = new Message();
                            message.setTime(System.currentTimeMillis());
                            message.setMessage(uri.toString());
                            message.setSenderId(UserManager.userId);
                            message.setType(Message.IMAGE);

                            DatabaseReference myRef = database.getReference("chat/" + chatKey + "/" + System.currentTimeMillis());
                            myRef.setValue(message);
                        }
                    });
                }
            });
        }
    }
}
