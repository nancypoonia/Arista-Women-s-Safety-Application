package com.example.arista1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    private static final String COORDINATE_PATTERN =
            "Latitude:([+-]?\\d+\\.\\d+)[\\s\\n]*Longitude:([+-]?\\d+\\.\\d+)";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus != null) {
                        for (Object pdu : pdus) {
                            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
                            String message = sms.getMessageBody();
                            processMessage(context, message);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "SMS processing error", e);
                }
            }
        }
    }

    private void processMessage(Context context, String message) {
        Pattern pattern = Pattern.compile(COORDINATE_PATTERN);
        Matcher matcher = pattern.matcher(message.replaceAll("\\s+", ""));

        if (matcher.find()) {
            try {
                double latitude = Double.parseDouble(matcher.group(1));
                double longitude = Double.parseDouble(matcher.group(2));

                if (isValidCoordinate(latitude, longitude)) {
                    launchMapActivity(context, latitude, longitude);
                    abortBroadcast();
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Coordinate parsing error", e);
                Toast.makeText(context, "Invalid coordinate format", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidCoordinate(double latitude, double longitude) {
        return (latitude >= -90 && latitude <= 90) &&
                (longitude >= -180 && longitude <= 180);
    }

    private void launchMapActivity(Context context, double lat, double lng) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lng);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}