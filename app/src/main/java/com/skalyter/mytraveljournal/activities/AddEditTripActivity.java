package com.skalyter.mytraveljournal.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;
import androidx.exifinterface.media.ExifInterface;

import com.google.android.material.textfield.TextInputEditText;
import com.skalyter.mytraveljournal.R;
import com.skalyter.mytraveljournal.database.TripDao;
import com.skalyter.mytraveljournal.model.Trip;
import com.skalyter.mytraveljournal.model.TripType;
import com.skalyter.mytraveljournal.util.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static com.skalyter.mytraveljournal.util.Constant.INTENT_TRIP_ID;
import static com.skalyter.mytraveljournal.util.Constant.REQ_CAMERA;
import static com.skalyter.mytraveljournal.util.Constant.REQ_GALLERY;
import static com.skalyter.mytraveljournal.util.Util.isBefore;
import static com.skalyter.mytraveljournal.util.Util.rotateImage;

public class AddEditTripActivity extends AppCompatActivity {

    private TextInputEditText name, destination, priceValue;
    private RadioButton radio1, radio2, radio3;
    private RadioGroup radio;
    private SeekBar priceSlider;
    private TextView startDate, endDate, startDateValue, endDateValue;
    private AppCompatRatingBar ratingBar;
    private Button imageButton;
    private ImageView imageView;

    private Trip trip;
    private TripDao tripDao;
    private long id;

    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Add / Edit trip");
        toolbar.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        name = findViewById(R.id.input_trip_name);
        destination = findViewById(R.id.input_trip_destination);

        radio = findViewById(R.id.radio);
        radio1 = findViewById(R.id.radio_1);
        radio2 = findViewById(R.id.radio_2);
        radio3 = findViewById(R.id.radio_3);

        priceSlider = findViewById(R.id.input_price_slider);
        priceValue = findViewById(R.id.input_price_value);

        startDate = findViewById(R.id.input_start_date);
        endDate = findViewById(R.id.input_end_date);
        startDateValue = findViewById(R.id.value_start_date);
        endDateValue = findViewById(R.id.value_end_date);

        ratingBar = findViewById(R.id.input_rating);

        imageButton = findViewById(R.id.button_image);
        imageView = findViewById(R.id.image);

        tripDao = new TripDao(this);

        id = getIntent().getLongExtra(INTENT_TRIP_ID, -1);
        if(id != -1){
            trip = tripDao.getTrip(id);
            name.setText(trip.getName());
            destination.setText(trip.getDestination());
            if(trip.getType().getKey()==0){
                radio1.setChecked(true);
            } else if(trip.getType().getKey() == 1){
                radio2.setChecked(true);
            } else{
                radio3.setChecked(true);
            }
            priceValue.setText(trip.getPrice().toString());
            priceSlider.setProgress((int)Math.floor(trip.getPrice()));
            startDateValue.setText(Util.getStringFromCalendar(trip.getStartDate()));
            endDateValue.setText(Util.getStringFromCalendar(trip.getEndDate()));
            ratingBar.setProgress((int)Math.ceil(trip.getRating()*2));
            //imageView.setImageURI();
        } else {
            trip = new Trip();
        }
        priceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                priceValue.setText(String.format("%d", i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        priceValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(priceValue.getText().length()>1)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        priceSlider.setProgress(Integer.parseInt(priceValue.getText().toString()), true);
                    } else {
                        priceSlider.setProgress(Integer.parseInt(priceValue.getText().toString()));
                    }
                priceValue.setSelection(priceValue.getText().length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        startDateSetListener = (datePicker, year, month, date) -> {
            calendarStart = Calendar.getInstance();
            calendarStart.set(year, month, date);
            if (Util.isAfter(calendarStart, trip.getEndDate())) {
                trip.setEndDate(calendarStart);
                endDateValue.setText(Util.getStringFromCalendar(calendarStart));
                calendarEnd = calendarStart;
            }
            trip.setStartDate(calendarStart);
            startDateValue.setVisibility(View.VISIBLE);
            startDateValue.setText(Util.getStringFromCalendar(calendarStart));
        };
        endDateSetListener = (datePicker, year, month, date) -> {
            calendarEnd = Calendar.getInstance();
            calendarEnd.set(year, month, date);
            if (Util.isBefore(calendarEnd, trip.getStartDate())) {
                trip.setStartDate(calendarEnd);
                startDateValue.setText(Util.getStringFromCalendar(calendarEnd));
                calendarStart = calendarEnd;
            }
            trip.setEndDate(calendarEnd);
            endDateValue.setVisibility(View.VISIBLE);
            endDateValue.setText(Util.getStringFromCalendar(calendarEnd));
        };

    }

    public void setTripType(View view) {
        switch (view.getId()) {
            case R.id.radio_1:
                trip.setType(TripType.CITYBREAK);
                break;
            case R.id.radio_2:
                trip.setType(TripType.MOUNTAIN);
                break;
            case R.id.radio_3:
                trip.setType(TripType.SEASIDE);
                break;
            default:
                throw new IllegalArgumentException();

        }
    }

    public void setStartDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddEditTripActivity.this,
                android.R.style.Theme_DeviceDefault_Dialog, startDateSetListener,
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    public void setEndDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddEditTripActivity.this,
                android.R.style.Theme_DeviceDefault_Dialog, endDateSetListener,
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    public void chooseImage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an action").setCancelable(true);
        String[] options = {"Choose from Gallery", "Take a picture"};
        builder.setItems(options, (dialogInterface, i) -> {
            switch (i) {
                case 0:
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, REQ_GALLERY);
                    break;
                case 1:
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, REQ_CAMERA);
                    break;
                default:
                    break;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void save(View view){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GALLERY && resultCode == Activity.RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                imageView.setVisibility(View.VISIBLE);
                trip.setImage(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddEditTripActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            Log.d("DEBUG", "onActivityResult: " + imageUri.toString());
            trip.setImage(imageUri);
            //TODO: store the image in InternalMemory(/External w/e)
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            try {
                InputStream inputStream;
                if (imageUri != null) {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    ExifInterface ei = new ExifInterface(inputStream);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            photo = rotateImage(photo, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            photo = rotateImage(photo, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            photo = rotateImage(photo, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(photo);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit_trip, menu);
        return true;
    }

    public void save(MenuItem item) {
        //TODO: save the trip into DB
        String name = this.name.getText().toString();
        String destination = this.destination.getText().toString();
        Double price = (double)priceSlider.getProgress();
        Float rating = ratingBar.getRating();
        //TODO save the image into storage
        if(name.isEmpty() || name == null) {
            this.name.setError("Please fill in the name");
            return;
        } else {
            trip.setName(name);
        }
        if(destination.isEmpty() || destination == null){
            this.destination.setError("Please fill in the destination");
            return;
        } else {
            trip.setDestination(destination);
        }
        if(isBefore(calendarStart, calendarEnd)
                && trip.getStartDate()!=null
                && trip.getEndDate() != null){
            trip.setStartDate(calendarStart);
            trip.setEndDate(calendarEnd);
        } else {
            Toast.makeText(this, "Double check the dates :)", Toast.LENGTH_SHORT).show();
            return;
        }
        if(rating != 0){
            trip.setRating(rating);
        }
        if(radio.getCheckedRadioButtonId() != -1){
            switch (radio.getCheckedRadioButtonId()){
                case R.id.radio_1:
                    trip.setType(TripType.CITYBREAK);
                    break;
                case R.id.radio_2:
                    trip.setType(TripType.MOUNTAIN);
                    break;
                case R.id.radio_3:
                    trip.setType(TripType.SEASIDE);
            }
        } else{
            Toast.makeText(this, "Select a trip type", Toast.LENGTH_SHORT).show();
            return;
        }
        if(price == null){
            priceValue.setError("Please enter the price.");
        } else {
            trip.setPrice(price);
        }
        if(id != -1){
            tripDao.updateTrip(trip);
            Toast.makeText(this, "Trip updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            trip.setId(tripDao.insertTrip(trip));
            Toast.makeText(this, "Trip added successfully", Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK);
        finish();
    }
}
