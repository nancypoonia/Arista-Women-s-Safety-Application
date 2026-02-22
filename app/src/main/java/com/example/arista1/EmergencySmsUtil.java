package com.example.arista1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Utility class for sending emergency SMS messages
 */
public class EmergencySmsUtil {
    private static final String TAG = "EmergencySmsUtil";
    private static final int REQUEST_SMS_PERMISSION = 102;

    /**
     * Creates a properly formatted emergency SMS message with location data
     * @param latitude The current latitude
     * @param longitude The current longitude
     * @return Formatted emergency SMS message
     */
    public static String createEmergencyMessage(double latitude, double longitude) {
        return "EMERGENCY ALERT\n" +
                "LAT:" + latitude + "\n" +
                "LON:" + longitude;
    }

    /**
     * Sends an emergency SMS to a contact with the current location
     * @param context The application context
     * @param phoneNumber The emergency contact's phone number
     * @param location The current location
     * @return True if SMS was sent successfully, false otherwise
     */
    public static boolean sendEmergencySms(Context context, String phoneNumber, Location location) {
        if (location == null) {
            Toast.makeText(context, "Cannot send emergency alert: Location not available",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            Toast.makeText(context, "Cannot send emergency alert: No phone number provided",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        // Check SMS permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (context instanceof Activity) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSION);
            }
            Toast.makeText(context, "SMS permission required to send emergency alerts",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String message = createEmergencyMessage(latitude, longitude);

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            Log.d(TAG, "Emergency SMS sent to " + phoneNumber + " with coordinates: "
                    + latitude + ", " + longitude);
            Toast.makeText(context, "Emergency alert sent", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to send emergency SMS", e);
            Toast.makeText(context, "Failed to send emergency alert: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Sends a test emergency SMS to yourself
     * @param context The application context
     * @param phoneNumber Your own phone number for testing
     */
    public static void sendTestEmergencySms(Context context, String phoneNumber) {
        // Use fixed test coordinates
        try {
            String message = "EMERGENCY ALERT\n" +
                    "LAT:28.248743\n" +
                    "LON:76.815521";

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            Toast.makeText(context, "Test emergency alert sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Failed to send test emergency SMS", e);
            Toast.makeText(context, "Failed to send test alert: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}