package com.skalyter.mytraveljournal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.skalyter.mytraveljournal.R;
import com.skalyter.mytraveljournal.database.TripDao;
import com.skalyter.mytraveljournal.model.Trip;
import com.skalyter.mytraveljournal.model.TripType;

public class AddEditTripActivity extends AppCompatActivity {

    private TextInputEditText name, destination;
    private RadioButton radio1, radio2, radio3;
    private RadioGroup radio;
    private SeekBar priceSlider;
    private TextView priceValue, startDate, endDate, startDateValue, endDateValue;
    private AppCompatRatingBar ratingBar;
    private Button imageButton;
    private ImageView imageView;

    private Trip trip;
    private TripDao tripDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(RESULT_CANCELED);
//                finish();
//            }
//        });
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

    public void setStartDate(View view){
    }
    public void setEndDate(View view){

    }
}
