package me.flyingrub.beermeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AllBeerAdapter allBeerAdapter;
    TextView emptyView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Beer> beers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext());
        recyclerView.addItemDecoration(itemDecoration);

        beers = new ArrayList<>(); // TO DO RETRIEVE SAVED BEER

        if (beers.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            allBeerAdapter = new AllBeerAdapter(beers);
            recyclerView.setAdapter(allBeerAdapter);
            allBeerAdapter.SetOnItemClickListener(new AllBeerAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View v , int position) {
                    showEditBeeDialog(position);
                }
            });
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBeerDialog(view);
            }
        });
    }

    public void showEditBeeDialog(final int position) {
        final Beer beer = beers.get(position);
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.edit_beer)
                .customView(R.layout.dialog, true) // Wrap in scroll view. Usefull for small screen
                .positiveText(R.string.ok)
                .negativeText(R.string.delete)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteBeer(position);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        EditText nameText = (EditText) dialog.getCustomView().findViewById(R.id.name);
                        EditText degreeText = (EditText) dialog.getCustomView().findViewById(R.id.degree);
                        EditText volumeText = (EditText) dialog.getCustomView().findViewById(R.id.volume);
                        EditText priceText = (EditText) dialog.getCustomView().findViewById(R.id.price);
                        String name = nameText.getText().toString();
                        try {
                            if (name == "") {
                                throw new IllegalCharsetNameException("Invalid Name");
                            }
                            Float price = Float.parseFloat(priceText.getText().toString());
                            Float degree = Float.parseFloat(degreeText.getText().toString());
                            Float volume = Float.parseFloat(volumeText.getText().toString());

                            editBeer(position, new Beer(name, price, volume, degree));
                        } catch (NumberFormatException e) {
                            Snackbar.make(getWindow().getDecorView(), "Wrong number", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (IllegalCharsetNameException e) {
                            Snackbar.make(getWindow().getDecorView(), "Invalid Name", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }


                    }
                })
                .build();
        EditText nameText = (EditText) dialog.getCustomView().findViewById(R.id.name);
        EditText degreeText = (EditText) dialog.getCustomView().findViewById(R.id.degree);
        EditText volumeText = (EditText) dialog.getCustomView().findViewById(R.id.volume);
        EditText priceText = (EditText) dialog.getCustomView().findViewById(R.id.price);
        nameText.setText(beer.getName());
        degreeText.setText("" + beer.getDegree());
        volumeText.setText("" + beer.getVolume());
        priceText.setText("" + beer.getPrice());
        dialog.show();
    }

    public void showAddBeerDialog(View view) {
        new MaterialDialog.Builder(this)
                .title(R.string.add_beer)
                .customView(R.layout.dialog, true) // Wrap in scroll view. Usefull for small screen
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        EditText nameText = (EditText) dialog.getCustomView().findViewById(R.id.name);
                        EditText degreeText = (EditText) dialog.getCustomView().findViewById(R.id.degree);
                        EditText volumeText = (EditText) dialog.getCustomView().findViewById(R.id.volume);
                        EditText priceText = (EditText) dialog.getCustomView().findViewById(R.id.price);
                        String name = nameText.getText().toString();
                        try {
                            if (name == "") {
                                throw new IllegalCharsetNameException("Invalid Name");
                            }
                            Float price = Float.parseFloat(priceText.getText().toString());
                            Float degree = Float.parseFloat(degreeText.getText().toString());
                            Float volume = Float.parseFloat(volumeText.getText().toString());

                            addNewBeer(new Beer(name, price, volume, degree));
                        } catch (NumberFormatException e) {
                            Snackbar.make(getWindow().getDecorView(), "Wrong number", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (IllegalCharsetNameException e) {
                            Snackbar.make(getWindow().getDecorView(), "Invalid Name", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }


                    }
                })
                .show();
    }

    public void deleteBeer(int position) {
        beers.remove(position);
        reloadDisplay();
    }

    public void editBeer(int position, Beer beer) {
        beers.set(position, beer);
        reloadDisplay();
    }
    public void addNewBeer(Beer beer) {
        beers.add(beer);
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        reloadDisplay();
    }

    public void reloadDisplay() {
        allBeerAdapter = new AllBeerAdapter(beers);
        recyclerView.swapAdapter(allBeerAdapter, true);
        allBeerAdapter.SetOnItemClickListener(new AllBeerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v , int position) {
                showEditBeeDialog(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
