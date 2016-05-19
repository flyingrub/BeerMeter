package me.flyingrub.beermeter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fly on 5/19/16.
 */
public class AllBeerAdapter extends RecyclerView.Adapter<AllBeerAdapter.ViewHolder> {
    public OnItemClickListener mItemClickListener;
    private ArrayList<Beer> beers;

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public AllBeerAdapter(ArrayList<Beer> beers) {
        this.beers = beers;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AllBeerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_one_beer, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Beer beer = beers.get(position);
        holder.beerName.setText(beer.getName());
        holder.beerRentability.setText("" + beer.getRentabilty());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return beers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RelativeLayout element;
        public TextView beerName;
        public TextView beerRentability;

        public ViewHolder(View v) {
            super(v);
            beerName = (TextView) itemView.findViewById(R.id.name);
            beerRentability = (TextView) itemView.findViewById(R.id.rentability);
            element  = (RelativeLayout) itemView.findViewById(R.id.element);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }
}