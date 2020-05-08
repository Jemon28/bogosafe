package co.edu.unipanamericana.bogosafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import co.edu.unipanamericana.bogosafe.modelo.Zona;

public class MapsActivity  extends FragmentActivity implements OnMapReadyCallback, OnInfoWindowClickListener, OnMyLocationButtonClickListener, OnMyLocationClickListener  {

    private static final String TAG = "BogoSafe";
    private GoogleMap mMap;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private float DEFAULT_ZOOM = 13;
    private LocationManager locationManager;
    private LatLng mDefaultLocation = new LatLng(4.6971966,-74.1361705);
    //private GoogleMap mMap;
    private Button mbtnGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        /*mbtnGPS = (floatinActionButton) findViewById(R.id.floatingActionButtonGPS);


        mbtnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.collection("ZonasMarcadas").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                        Log.d("MapsListner", "Leyendo informacion continua " + d.getData().toString());
                        Zona z = d.toObject(Zona.class);
                        Log.w(TAG, z.toString());
                        MarkerOptions m = new MarkerOptions();
                        m.position(new LatLng(z.getUbicacion().getLatitude(), z.getUbicacion().getLongitude()))
                                .title(z.getSuceso())
                                .snippet("Barrio: " + z.getBarrio());
                        mMap.addMarker(m).setTag(z);
                    }
                } else{
                    Log.w("Advertencia", e.getCause());
                }
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Zona z = (Zona) marker.getTag();
        Toast.makeText(this, String.valueOf(z.getFecha().toDate()),
        Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}


