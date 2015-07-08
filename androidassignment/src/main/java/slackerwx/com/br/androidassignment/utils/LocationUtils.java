package slackerwx.com.br.androidassignment.utils;

/**
 * Created by slackerwx on 07/07/15.
 */

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.Criteria.ACCURACY_FINE;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.location.LocationManager.PASSIVE_PROVIDER;

/**
 * Utilities for dealing with the location service
 */
public class LocationUtils {

    /**
     * Get the latest location trying multiple providers; Calling this method
     * requires that your application's manifest contains the
     * {@link android.Manifest.permission#ACCESS_FINE_LOCATION} permission
     *
     * @param context
     * @return latest location set or null if none
     */
    public static Location getLatestLocation(final Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(ACCURACY_FINE);
        String provider = manager.getBestProvider(criteria, true);
        Location bestLocation;

        if (provider != null)
            bestLocation = manager.getLastKnownLocation(provider);
        else
            bestLocation = null;

        Location latestLocation = getLatest(bestLocation, manager.getLastKnownLocation(GPS_PROVIDER));
        latestLocation = getLatest(latestLocation, manager.getLastKnownLocation(NETWORK_PROVIDER));
        latestLocation = getLatest(latestLocation, manager.getLastKnownLocation(PASSIVE_PROVIDER));

        return latestLocation;
    }

    /**
     * Get the location with the later date
     *
     * @param location1
     * @param location2
     * @return location
     */
    private static Location getLatest(final Location location1, final Location location2) {
        if (location1 == null)
            return location2;

        if (location2 == null)
            return location1;

        if (location2.getTime() > location1.getTime())
            return location2;
        else
            return location1;
    }

}
