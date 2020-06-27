package org.mlab.research.koios.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.mlab.research.koios.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = MapFragment.class.getName() + "_debug";
    private static final int DEFAULT_DAYS = 7;
    private static final int DEFAULT_MARKER_COLOR = 350;
    private static final int DEFAULT_PADDING = 150;

    public static final String SIG_LOC_PARCEL = "sig_loc_parcel";

    private ArrayList<SignificantLocation> significantLocations;
    SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.map_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.display_7:
                refreshMapView(7);
                break;
            case R.id.display_14:
                refreshMapView(14);
                break;
            case R.id.display_30:
                refreshMapView(30);
                break;
            case R.id.display_90:
                refreshMapView(90);
                break;
        }
        return true;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        refreshMapView(DEFAULT_DAYS);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.getUiSettings().setMapToolbarEnabled(false);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i=0;i<significantLocations.size();i++){
            LatLng tempLatLng = new LatLng(significantLocations.get(i).getLatitude(), significantLocations.get(i).getLongitude());
            Marker marker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(DEFAULT_MARKER_COLOR))
                .anchor(0.5f, 0.5f)
                .title(significantLocations.get(i).getName())
                .position(tempLatLng));
            marker.showInfoWindow();
            marker.setTag(i);
            builder.include(tempLatLng);
        }

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int tag = (int) marker.getTag();
                Intent intent = new Intent(getActivity(), VisitActivity.class);
                intent.putExtra(SIG_LOC_PARCEL, significantLocations.get(tag));
                startActivity(intent);
            }
        });

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, DEFAULT_PADDING);
        googleMap.animateCamera(cu);
    }

    private void refreshMapView(int numOfDays){
        significantLocations = MapDataProvider.getSignificantLocations(numOfDays);
        mapFragment.getMapAsync(this);
    }
}