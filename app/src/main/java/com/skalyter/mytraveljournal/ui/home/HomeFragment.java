package com.skalyter.mytraveljournal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skalyter.mytraveljournal.activities.AddEditTripActivity;
import com.skalyter.mytraveljournal.R;
import com.skalyter.mytraveljournal.database.TripDao;
import com.skalyter.mytraveljournal.model.Trip;
import com.skalyter.mytraveljournal.model.TripType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.skalyter.mytraveljournal.util.Constant.INTENT_TRIP_ID;
import static com.skalyter.mytraveljournal.util.Constant.REQ_EDIT_TRIP;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    private TripDao tripDao;
    private List<Trip> tripList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tripDao = new TripDao(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tripList = tripDao.getAllTripsChronologically();
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
            //if(trip.getImageUri() != null) holder.image.setImageURI(trip.getImageUri());
            holder.name.setText(trip.getName());
            holder.destination.setText(trip.getDestination());
            holder.price.setText(trip.getPrice() + "€");
            if(trip.getRating()!= null){
                holder.rating.setText(String.format("%.1f", trip.getRating()));
            } else {
                holder.ratingImage.setVisibility(View.GONE);
                holder.rating.setVisibility(View.GONE);
            }
            holder.favorite.setOnClickListener(view -> {
                if(trip.isFavorite()) {
                    holder.favorite.setImageResource(R.drawable.ic_favorite_border_red);
                    tripList.get(position).setFavorite(false);
                    trip.setFavorite(false);
                } else {
                    holder.favorite.setImageResource(R.drawable.ic_favorite_red);
                    tripList.get(position).setFavorite(true);
                    trip.setFavorite(true);
                }
                tripDao.updateTrip(trip);
            });
            holder.itemView.setOnClickListener(view -> {
                //TODO: start TripDetails activity
            });
            holder.cardView.setOnLongClickListener(view -> {
                Intent intent = new Intent(getContext(), AddEditTripActivity.class);
                intent.putExtra(INTENT_TRIP_ID, trip.getId());
                startActivityForResult(intent, REQ_EDIT_TRIP);
                return true;
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
//            tripList = tripDao.getAllTripsChronologically();
//            customAdapter = new CustomAdapter(tripList);
//            recyclerView.setAdapter(customAdapter);t
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tripList = tripDao.getAllTripsChronologically();
        customAdapter = new CustomAdapter(tripList);
        recyclerView.setAdapter(customAdapter);
    }
}