package com.example.arista1;
import java.util.Locale;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 101;
    private static final String TAG = "MapActivity";
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Marker userMarker, emergencyMarker;
    private double emergencyLat, emergencyLon;
    private TextView tvLatLngInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // Initialize osmdroid configuration
            Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));
            setContentView(R.layout.activity_map);

            initializeMap();
            setupUI();
            handleIntentData();
            checkLocationPermissions();
        } catch (Exception e) {
            Log.e(TAG, "Initialization failed", e);
            Toast.makeText(this, "Map initialization failed", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initializeMap() {
        try {
            mapView = findViewById(R.id.map);
            if (mapView != null) {
                mapView.setTileSource(TileSourceFactory.MAPNIK);
                mapView.setMultiTouchControls(true);
                mapView.getController().setZoom(15.0);
            }
        } catch (Exception e) {
            Log.e(TAG, "Map initialization error", e);
            throw e;
        }
    }

    private void setupUI() {
        try {
            tvLatLngInfo = findViewById(R.id.tvLatLngInfo);
            findViewById(R.id.btnDirections).setOnClickListener(v -> launchDirections());
        } catch (Exception e) {
            Log.e(TAG, "UI setup failed", e);
            Toast.makeText(this, "UI setup error", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleIntentData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra("latitude") && intent.hasExtra("longitude")) {
                    emergencyLat = intent.getDoubleExtra("latitude", 0);
                    emergencyLon = intent.getDoubleExtra("longitude", 0);
                } else if (intent.hasExtra("lat") && intent.hasExtra("lng")) {
                    emergencyLat = intent.getDoubleExtra("lat", 0);
                    emergencyLon = intent.getDoubleExtra("lng", 0);
                }

                if (emergencyLat != 0 || emergencyLon != 0) {
                    updateEmergencyMarker();
                    tvLatLngInfo.setText(String.format("Lat: %.6f\nLon: %.6f", emergencyLat, emergencyLon));
                } else {
                    Toast.makeText(this, "No valid coordinates received", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Intent handling failed", e);
            Toast.makeText(this, "Failed to process location data", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void checkLocationPermissions() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
            } else {
                startLocationUpdates();
            }
        } catch (Exception e) {
            Log.e(TAG, "Permission check failed", e);
            Toast.makeText(this, "Location permission error", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLocationUpdates() {
        try {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            LocationRequest locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000)
                    .setFastestInterval(2000);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        if (locationResult != null) {
                            updateUserLocation(locationResult.getLastLocation());
                        }
                    }
                };
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            }
        } catch (Exception e) {
            Log.e(TAG, "Location updates failed", e);
            Toast.makeText(this, "Failed to start location updates", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUserLocation(Location location) {
        try {
            if (location == null || mapView == null) return;

            GeoPoint userPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
            if (userMarker == null) {
                userMarker = new Marker(mapView);
                userMarker.setTitle("Your Position");
                try {
                    userMarker.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_user_marker));
                } catch (Exception e) {
                    userMarker.setIcon(getResources().getDrawable(android.R.drawable.presence_online));
                }
                mapView.getOverlays().add(userMarker);
            }
            userMarker.setPosition(userPoint);
            zoomToBothLocations();
            mapView.invalidate();
        } catch (Exception e) {
            Log.e(TAG, "Location update failed", e);
        }
    }

    private void updateEmergencyMarker() {
        try {
            if (mapView == null) return;

            if (emergencyMarker == null) {
                emergencyMarker = new Marker(mapView);
                emergencyMarker.setTitle("Emergency Location");
                try {
                    emergencyMarker.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_emergency_marker));
                } catch (Exception e) {
                    emergencyMarker.setIcon(getResources().getDrawable(android.R.drawable.presence_busy));
                }
                mapView.getOverlays().add(emergencyMarker);
            }
            emergencyMarker.setPosition(new GeoPoint(emergencyLat, emergencyLon));
            zoomToBothLocations();
        } catch (Exception e) {
            Log.e(TAG, "Emergency marker update failed", e);
        }
    }

    private void zoomToBothLocations() {
        try {
            if (mapView == null || userMarker == null || emergencyMarker == null) return;

            BoundingBox boundingBox = new BoundingBox(
                    Math.max(userMarker.getPosition().getLatitude(), emergencyLat) + 0.01,
                    Math.max(userMarker.getPosition().getLongitude(), emergencyLon) + 0.01,
                    Math.min(userMarker.getPosition().getLatitude(), emergencyLat) - 0.01,
                    Math.min(userMarker.getPosition().getLongitude(), emergencyLon) - 0.01
            );

            mapView.zoomToBoundingBox(boundingBox, true, 100);
            mapView.getController().setCenter(boundingBox.getCenter());
        } catch (Exception e) {
            Log.e(TAG, "Zoom failed", e);
        }
    }

    private void launchDirections() {
        // Directly open Google Maps with navigation to emergency location
        String uri = String.format(Locale.US,
                "https://www.google.com/maps/dir/?api=1&destination=%f,%f&travelmode=driving",
                emergencyLat,
                emergencyLon);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDetach();
        }
    }
}