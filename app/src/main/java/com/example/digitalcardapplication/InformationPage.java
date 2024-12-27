package com.example.digitalcardapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class InformationPage extends AppCompatActivity {

    private CircleImageView profile_image;
    private static final int PICK_IMAGE_REQUEST = 100;
    private TextInputLayout fullName,designation,company,aboutMe,contactNumber,email,address,service,errorText;
    private TextInputEditText fullName_content,designation_content,company_content,aboutMe_content,contactNumber_content,email_content,address_content,service_content;
    private RadioGroup radioGroup;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information_page);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        initBinding();
        pickImage();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton();
            }
        });

    }

    public void initBinding(){
        profile_image = findViewById(R.id.Profile);
        fullName = findViewById(R.id.Full_Name);
        designation = findViewById(R.id.designation);
        company = findViewById(R.id.Company);
        aboutMe = findViewById(R.id.About);
        contactNumber = findViewById(R.id.ContactNumber);
        email = findViewById(R.id.Email);
        address = findViewById(R.id.Address);
        service = findViewById(R.id.Service);
        fullName_content = findViewById(R.id.FullName_content);
        designation_content = findViewById(R.id.designation_content);
        company_content = findViewById(R.id.company_content);
        aboutMe_content = findViewById(R.id.About_content);
        contactNumber_content = findViewById(R.id.ContactNumber_content);
        email_content = findViewById(R.id.Email_content);
        address_content = findViewById(R.id.Address_content);
        service_content = findViewById(R.id.Service_content);
        radioGroup = findViewById(R.id.RadioGroup);
        RadioButton yesWhatsapp = findViewById(R.id.YesWhatsapp);
        RadioButton noWhatsapp = findViewById(R.id.NoWhatsapp);
        nextButton = findViewById(R.id.NextButton);
        errorText = findViewById(R.id.errorText);
    }

    public void pickImage(){

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            profile_image.setImageURI(uri);
        }
    }

    public void nextButton(){

        String nameText = fullName_content.getText().toString();
        String designationText = designation_content.getText().toString();
        String companyText = company_content.getText().toString();
        String aboutMeText = aboutMe_content.getText().toString();
        String contactNumberText = contactNumber_content.getText().toString();
        String emailText = email_content.getText().toString();
        String addressText = address_content.getText().toString();
        String serviceText = service_content.getText().toString();
        String selected = "";

        int radioGroupId = radioGroup.getCheckedRadioButtonId();

        if (radioGroupId == R.id.YesWhatsapp)
        {
            selected = "Yes";
        } else if (radioGroupId == R.id.NoWhatsapp) {
            selected = "No";
        }

        if (nameText.isEmpty()){
            fullName_content.setError("Please Enter Name");
            fullName.setError("Please Enter Name");
            fullName_content.requestFocus();
        } else if (designationText.isEmpty()) {
            designation_content.setError("Please Enter Designation");
            designation.setError("Please Enter Designation");
            designation_content.requestFocus();
        }else if (companyText.isEmpty()) {
            company_content.setError("Please Enter Company Name");
            company.setError("Please Enter Company Name");
            company_content.requestFocus();
        }else if (aboutMeText.isEmpty()) {
            aboutMe_content.setError("Please Enter About You");
            aboutMe.setError("Please Enter About You");
            aboutMe_content.requestFocus();
        }else if (contactNumberText.isEmpty()) {
            contactNumber_content.setError("Contact Number is required");
            contactNumber.setError("Contact Number is required");
            contactNumber_content.requestFocus();
        }else if (radioGroupId == -1){
            errorText.setError("Please select an option for WhatsApp");
            errorText.requestFocus();
        }else if (emailText.isEmpty()) {

            email_content.setError("Email is required");
            email.setError("Email is required");
            email_content.requestFocus();
        }else if (addressText.isEmpty()) {
            address_content.setError("Please Enter Your Address");
            address.setError("Please Enter Your Address");
            address_content.requestFocus();
        }else if (serviceText.isEmpty()) {
            service_content.setError("Please Enter ServiceInfo");
            service.setError("Please Enter ServiceInfo");
            service_content.requestFocus();
        }else{

            Intent intent = new Intent(InformationPage.this, EditDataScreen.class);


            File tempFile;
            try {
                tempFile = saveImageToCache();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            intent.putExtra("profilePicturePath", tempFile.getAbsolutePath());
            intent.putExtra("fullName",nameText);
            intent.putExtra("designation",designationText);
            intent.putExtra("company",companyText);
            intent.putExtra("aboutMe",aboutMeText);
            intent.putExtra("contactNumber",contactNumberText);
            intent.putExtra("selected",selected);
            intent.putExtra("email",emailText);
            intent.putExtra("address",addressText);
            intent.putExtra("serviceInfo",serviceText);

            startActivity(intent);

            Toast.makeText(InformationPage.this,"Success",Toast.LENGTH_SHORT).show();
        }
    }
    private File saveImageToCache() throws IOException {
        Bitmap bitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
        File tempFile = new File(getCacheDir(), "profile_picture.jpg");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        return tempFile;
    }
}