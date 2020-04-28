package co.edu.unipanamericana.bogosafe.control;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipanamericana.bogosafe.modelo.Zona;

public class ControlZonasMarcadas {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static List<Zona> listaZonasMarcadas = new ArrayList<>();


    public static List<Zona> loadZonasMarcadas() {
       db.collection("ZonasMarcadas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("ObtenientosDB", "cargando Datos desde Firebase");
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Zona z = document.toObject(Zona.class);
                        listaZonasMarcadas.add(z);
                    }
                } else {
                    Log.w("ZonasMarcadas", "Error getting documents; ", task.getException());
                }
            }
        });
        return listaZonasMarcadas;
    }
}
