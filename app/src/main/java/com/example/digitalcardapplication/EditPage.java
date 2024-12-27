package com.example.digitalcardapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPage extends AppCompatActivity {

    private FloatingActionButton saveButton, editScreenButton, call, message, location;

    private TextView fullNameTextview, designationTextview, companyText, aboutUserTextview, userContactTextview, userWhatsappTextview, userEmailTextview, userAddressTextview, userServiceTextview;

    private RelativeLayout headerBox, contentBox, mainViewBox;

    private String fullName;
    private static final int REQUEST_CODE_PERMISSIONS = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_page);
        initBindings();
        getData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
            }
        });

        editScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customEditBox();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+918799400865"));
                startActivity(callIntent);

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
                messageIntent.setData(Uri.parse("sms to:+918799400865"));
                messageIntent.putExtra("sms_body","Hello, I am interested in your services.");
                startActivity(messageIntent);
            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "geo:0,0?q=Surat,Gujarat,India";
                Intent locationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(locationIntent);

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    String getFilePath() {
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String path = f.getPath() + "/MyCard";
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return path;
    }

    void saveImage() {
        Bitmap bitmap = Bitmap.createBitmap(mainViewBox.getWidth(), mainViewBox.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mainViewBox.draw(canvas);

        String path = getFilePath();
        String finalPath = path + "/" + "card.jpg";
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(finalPath))) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            Toast.makeText(EditPage.this,"Successfully Download",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void initBindings() {

        saveButton = findViewById(R.id.floatingButton_download);
        editScreenButton = findViewById(R.id.floatingButton_edit);
        fullNameTextview = findViewById(R.id.FullNameText);
        designationTextview = findViewById(R.id.DesignationNameText);
        companyText = findViewById(R.id.CompanyNameText);
        aboutUserTextview = findViewById(R.id.userAboutTextView);
        userContactTextview = findViewById(R.id.userContactTextView);
        userWhatsappTextview = findViewById(R.id.userWhatsappTextView);
        userEmailTextview = findViewById(R.id.userEmailTextView);
        userAddressTextview = findViewById(R.id.userAddressTextView);
        userServiceTextview = findViewById(R.id.userServiceTextView);
        headerBox = findViewById(R.id.ContentRelative);
        contentBox = findViewById(R.id.floatingButtons2);
        mainViewBox = findViewById(R.id.mainRelative);
        call = findViewById(R.id.PhoneCall);
        message = findViewById(R.id.Message);
        location = findViewById(R.id.location);


    }

    void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            String imagePath = intent.getStringExtra("profilePicturePath");

            if (imagePath != null) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Bitmap profilePicture = BitmapFactory.decodeFile(imagePath);
                    CircleImageView profileImageView = findViewById(R.id.Profile11);
                    profileImageView.setImageBitmap(profilePicture);
                } else {
                    Toast.makeText(EditPage.this, "", Toast.LENGTH_SHORT).show();
                }
                fullName = intent.getStringExtra("fullName");
                String designation = intent.getStringExtra("designation");
                String company = intent.getStringExtra("company");
                String aboutMe = intent.getStringExtra("aboutMe");
                String contact = intent.getStringExtra("contactNumber");
                String whatsappNumber = intent.getStringExtra("selected");
                String email = intent.getStringExtra("email");
                String address = intent.getStringExtra("address");
                String services = intent.getStringExtra("serviceInfo");

                fullNameTextview.setText(fullName);
                designationTextview.setText(designation);
                companyText.setText(company);
                aboutUserTextview.setText(aboutMe);
                userContactTextview.setText(contact);
                userWhatsappTextview.setText(whatsappNumber);
                userEmailTextview.setText(email);
                userAddressTextview.setText(address);
                userServiceTextview.setText(services);

            }
        }
    }

    public void customEditBox() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dailog_box);

        LinearLayout blackColor = dialog.findViewById(R.id.blackColor);
        LinearLayout rosePinkColor = dialog.findViewById(R.id.greenColor);
        LinearLayout beaverColor = dialog.findViewById(R.id.BlueColor);
        LinearLayout splashColor = dialog.findViewById(R.id.splashColor);
        LinearLayout darkPurpleColor = dialog.findViewById(R.id.PurpleColor);
        LinearLayout pacificCyanColor = dialog.findViewById(R.id.BrownColor);

        LinearLayout bg1 = dialog.findViewById(R.id.bg1);
        LinearLayout bg2 = dialog.findViewById(R.id.bg2);
        LinearLayout bg3 = dialog.findViewById(R.id.bg3);
        LinearLayout bg4 = dialog.findViewById(R.id.bg4);
        LinearLayout bg5 = dialog.findViewById(R.id.bg5);
        LinearLayout bg6 = dialog.findViewById(R.id.bg6);
        TextView fontStyle1 = dialog.findViewById(R.id.font1);
        TextView fontStyle2 = dialog.findViewById(R.id.font2);
        TextView fontStyle3 = dialog.findViewById(R.id.font3);
        TextView fontStyle4 = dialog.findViewById(R.id.font4);
        TextView fontStyle5 = dialog.findViewById(R.id.font5);
        TextView fontStyle6 = dialog.findViewById(R.id.font6);

        Button cancelButton = dialog.findViewById(R.id.cancel);
        Button resetButton = dialog.findViewById(R.id.reset);

        fontStyle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText(R.font.font1);
            }
        });
        fontStyle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText(R.font.font2);
            }
        });
        fontStyle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText(R.font.font3);
            }
        });
        fontStyle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText(R.font.font4);
            }
        });
        fontStyle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText(R.font.font5);
            }
        });
        fontStyle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText(R.font.font6);
            }
        });


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText(R.font.defaultstyle);
                headerBox.setBackgroundColor(getColor(R.color.splash));
                contentBox.setBackground(getDrawable(R.color.white));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        blackColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerBox.setBackgroundColor(getColor(R.color.black));
            }
        });
        splashColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerBox.setBackgroundColor(getColor(R.color.splash));
            }
        });
        beaverColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerBox.setBackgroundColor(getColor(R.color.Green));
            }
        });
        darkPurpleColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerBox.setBackgroundColor(getColor(R.color.Purple));
            }
        });
        rosePinkColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerBox.setBackgroundColor(getColor(R.color.Blue));
            }
        });
        pacificCyanColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerBox.setBackgroundColor(getColor(R.color.Brown));
            }
        });

        bg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentBox.setBackground(getDrawable(R.drawable.bg1));
            }
        });

        bg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentBox.setBackground(getDrawable(R.drawable.bg2));
            }
        });
        bg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentBox.setBackground(getDrawable(R.drawable.bg3));
            }
        });
        bg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentBox.setBackground(getDrawable(R.drawable.bg4));
            }
        });
        bg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentBox.setBackground(getDrawable(R.drawable.bg5));
            }
        });
        bg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentBox.setBackground(getDrawable(R.drawable.bg6));
            }
        });

        dialog.show();
    }
    void setText(int font ){
        fullNameTextview.setTypeface(ResourcesCompat.getFont(EditPage.this,font));
        designationTextview.setTypeface(ResourcesCompat.getFont(EditPage.this,font));
        companyText.setTypeface(ResourcesCompat.getFont(EditPage.this,font));

    }
}