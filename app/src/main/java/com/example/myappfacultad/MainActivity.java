package com.example.myappfacultad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity
        extends  FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.mapaFacultad);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        TextView Latitud =  findViewById(R.id.lblatitud);
        Latitud.setText("Latitud: "+ String.format("%.4f",latLng.latitude));
        TextView Longitud =  findViewById(R.id.lblongitud);
        Longitud.setText("Longitud: "+ String.format("%.4f",latLng.longitude));
        mapa.addMarker(new MarkerOptions().position(latLng)
                .title("Marcador"));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(true);

        LatLng[] coordenadaFacultad = {
                new LatLng(-1.012621359551321, -79.47048732760969),//FCI
                new LatLng(-1.012871436462689, -79.46931050837884),//SALUD
                new LatLng(-1.0129572537450988, -79.46881027640472),//RECTORADO
                new LatLng(-1.0123028969666923, -79.46883173408457)//ADMINISTRATIVO
        };
        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(coordenadaFacultad[2], 15);
        mapa.moveCamera(camUpd1);
        mapa.setOnMapClickListener(this);

        // Info(Matriz) de los edificiosFacultad
String[][] infoMarcadores = {
        {"Facultad de Ciencias de la Ingenieria"
                , "Una unidad académica multidisciplinaria con un alto grado de aceptación y reconocimiento nacional e internacional, por su calidad educativa, servicios e interacción con la sociedad"
                , "Formar íntegramente profesionales con sólida formación técnica científica, ética, sensibilidad social, liderazgo, creatividad y competitividad, comprometidos con los cambios tecnológicos, científicos y humanísticos"
                , "  facultadci@uteq.edu.ec", "@drawable/fci"},

        {"Facultad de la Salud"
                ,"Se  constituye en un referente de sostenibilidad académica, reconocida regionalmente por formar profesionales cualificados"
                ,"Formar íntegramente profesionales de la salud con sólidas bases científicas, humanísticas que le permitan actuar ante las necesidades de salud",
                "fcs@uteq.edu.ec", "@drawable/salud"},

        {"Edificio de Rectorado"
                , "Su función principal es liderar y dirigir la universidad, estableciendo objetivos, gestionando recursos, promoviendo la excelencia académica y representando a la institución ante diversas entidades. "
                , "Además, supervisa la gestión administrativa y académica"
                , "info@uteq.edu.ec", "@drawable/edirectorado"},

        {"Edificio Administrativo"
                , "Es el centro neurálgico donde se coordinan y ejecutan las políticas, procedimientos y decisiones administrativas que son fundamentales para el funcionamiento adecuado de la universidad."
                , "Donde se llevan a cabo las actividades relacionadas con la gestión y administración de la institución."
                , "info@uteq.edu.ec", "@drawable/administrativo"}
};
        //Indice de colores
        float[] coloresMarcadores = {
                BitmapDescriptorFactory.HUE_BLUE,
                BitmapDescriptorFactory.HUE_CYAN,
                BitmapDescriptorFactory.HUE_ORANGE,
                BitmapDescriptorFactory.HUE_MAGENTA
        };

        for (int i=0; i<infoMarcadores.length; i++) {
            int indiceColor = i % coloresMarcadores.length;
            marcadorFacultad(coordenadaFacultad[i], infoMarcadores[i][0], coloresMarcadores[indiceColor]);
        }
            mapa.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                // referencias de layout_facultad
                View infoLayoutFacultad = getLayoutInflater().inflate(R.layout.layout_facultad, null);
                TextView edificiofacultad = infoLayoutFacultad.findViewById(R.id.infoFacultad);
                TextView vision = infoLayoutFacultad.findViewById(R.id.infoVision);
                TextView cordenada = infoLayoutFacultad.findViewById(R.id.infoCoordenada);
                TextView mision = infoLayoutFacultad.findViewById(R.id.infoMision);
                TextView correo = infoLayoutFacultad.findViewById(R.id.infoCorreo);
                ImageView logo = infoLayoutFacultad.findViewById(R.id.logoFacultad);

                TextView Latitud =  findViewById(R.id.lblatitud);
                TextView Longitud =  findViewById(R.id.lblongitud);

                // Marcador de vistas
                String info = marker.getTitle();
                for (int p=0; p<infoMarcadores.length; p++){
                     if(info.equals(infoMarcadores[p][0])){

                         Latitud.setText(String.valueOf(coordenadaFacultad[p]));
                         Longitud.setText("");

                         edificiofacultad.setText(infoMarcadores[p][0]);
                         cordenada.setText(String.valueOf(coordenadaFacultad[p]));
                         vision.setText(infoMarcadores[p][1]);
                         mision.setText(infoMarcadores[p][2]);
                         correo.setText(infoMarcadores[p][3]);
                         String urlLogo = String.valueOf(infoMarcadores[p][4]);
                         int resourceId = getResources().getIdentifier(urlLogo, "drawable", getPackageName());
                         logo.setImageResource(resourceId);
                     }
                }
                    return infoLayoutFacultad;
            }

            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }
        });
    }
    public void marcadorFacultad(LatLng coordenadas, String edificiofacultad, float color){
        mapa.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(edificiofacultad)
                .icon(BitmapDescriptorFactory.defaultMarker(color))
        );
    }
}