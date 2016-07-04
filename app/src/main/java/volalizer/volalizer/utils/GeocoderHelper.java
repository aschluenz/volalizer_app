package volalizer.volalizer.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by andyschlunz on 04.07.16.
 */
public class GeocoderHelper {

    private Geocoder mGeocoder;
    private Double mLat;
    private Double mLong;
    private Context mcontext;
    private List<Address> mAddress;
    private String address;


    public String convertLatLongToAdress(Double Lat, Double Long, Context context) {
        mGeocoder = new Geocoder(context, Locale.getDefault());

        try {
            mAddress = mGeocoder.getFromLocation(Lat, Long, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
       address = mAddress.get(0).getAddressLine(0) + ", " + mAddress.get(0).getPostalCode() + ", " + mAddress.get(0).getLocality() ;

        return address ;
    }
}
