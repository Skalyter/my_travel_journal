package com.skalyter.mytraveljournal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skalyter.mytraveljournal.MyTravelJournalApp;
import com.skalyter.mytraveljournal.activities.AddEditTripActivity;
import com.skalyter.mytraveljournal.R;
import com.skalyter.mytraveljournal.database.AppDatabase;
import com.skalyter.mytraveljournal.database.TripDao;
import com.skalyter.mytraveljournal.model.Trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.skalyter.mytraveljournal.util.Constant.REQ_EDIT_TRIP;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    RecyclerView recyclerView;
    CustomAdapter customAdapter;

    private TripDao tripDao;
    private List<Trip> tripList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppDatabase db = ((MyTravelJournalApp) getActivity().getApplicationContext()).getDatabase();
        tripDao = db.tripDao();

        LiveData<List<Trip>> allTripsLiveData = tripDao.getAllTripsChronologically();
        allTripsLiveData.observe(this, trips -> tripList = trips);

        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        List<Trip> trips = new ArrayList<>();
//        trips.add(new Trip("Dummy", "Dubai", 450.0,
//                Calendar.getInstance(), Calendar.getInstance(), 4.5f, false));
//        trips.add(new Trip("Dummy bummy", "Abu Dhabi", 450.0,
//                Calendar.getInstance(), Calendar.getInstance(), 4.5f, false));
        customAdapter = new CustomAdapter(tripList);
        recyclerView.setAdapter(customAdapter);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

        private final List<Trip> tripList;

        public CustomAdapter(List<Trip> tripList) {
            this.tripList = tripList;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CustomViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.recycler_view_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
            final Trip trip =tripList.get(position);
            holder.image.setImageBitmap(trip.getImage());
            holder.name.setText(trip.getName());
            holder.destination.setText(trip.getDestination());
            holder.price.setText(trip.getPrice() + "€");
            if(trip.getRating()!= null){
                holder.rating.setText(String.format("%.1f", trip.getRating()));
            } else {
                holder.ratingImage.setVisibility(View.GONE);
                holder.rating.setVisibility(View.GONE);
            }
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: store values in DB
                    if(trip.isFavorite()) {
                        holder.favorite.setImageResource(R.drawable.ic_favorite_border_red);
                        trip.setFavorite(false);
                    } else {
                        holder.favorite.setImageResource(R.drawable.ic_favorite_red);
                        trip.setFavorite(true);
                    }
                }
            });
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: start TripDetails activity
                }
            });
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //TODO: start EditTrip activity
                    Intent intent = new Intent(getContext(), AddEditTripActivity.class);
                    startActivityForResult(intent, REQ_EDIT_TRIP);
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return tripList.size();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView name, destination, price, rating;
        ImageView image, favorite, ratingImage;
        CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            destination = itemView.findViewById(R.id.item_destination);
            price = itemView.findViewById(R.id.item_price);
            rating = itemView.findViewById(R.id.item_rating_text);
            ratingImage = itemView.findViewById(R.id.item_rating_image);
            image = itemView.findViewById(R.id.item_image);
            favorite = itemView.findViewById(R.id.item_favorite);
            cardView = itemView.findViewById(R.id.card);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_EDIT_TRIP && resultCode == RESULT_OK){
            customAdapter.notifyDataSetChanged();
        }
    }
}