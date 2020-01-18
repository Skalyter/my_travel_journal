package com.skalyter.mytraveljournal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.skalyter.mytraveljournal.util.Util;

import java.util.Calendar;

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

    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;

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

        trip = new Trip();
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
}
