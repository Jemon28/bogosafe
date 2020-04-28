package co.edu.unipanamericana.bogosafe.vista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


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



import co.edu.unipanamericana.bogosafe.R;
import co.edu.unipanamericana.bogosafe.modelo.Zona;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnInfoWindowClickListener, OnMyLocationButtonClickListener, OnMyLocationClickListener {
    private static final String TAG = "MapsActivity";
    private GoogleMap mapaZonaRiesgo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private float DEFAULT_ZOOM = 13;
    private LatLng mDefaultLocation = new LatLng(4.6971966,-74.1361705);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapaZonaRiesgo = googleMap;
        mapaZonaRiesgo.setOnInfoWindowClickListener(this);
        //mapaZonaRiesgo.moveCamera();
        mapaZonaRiesgo.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));
        mapaZonaRiesgo.getUiSettings().setMapToolbarEnabled(false);
        mapaZonaRiesgo.getUiSettings().setMyLocationButtonEnabled(false);
        mapaZonaRiesgo.setMyLocationEnabled(true);
        mapaZonaRiesgo.setOnMyLocationButtonClickListener(this);
        mapaZonaRiesgo.setOnMyLocationClickListener(this);

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
                        MarkerOptions m = new MarkerOptions();
                        m.position(new LatLng(z.getUbicacion().getLatitude(), z.getUbicacion().getLongitude()))
                                .title(z.getSuceso())
                                .snippet("Barrio: " + z.getBarrio());
                        mapaZonaRiesgo.addMarker(m).setTag(z);
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
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
        //mapaZonaRiesgo.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    public void reposicionarQ) {

    }
}





