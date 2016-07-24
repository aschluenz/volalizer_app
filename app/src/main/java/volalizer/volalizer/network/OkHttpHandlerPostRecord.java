package volalizer.volalizer.network;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by andyschlunz on 20.06.16.
 */
public class OkHttpHandlerPostRecord extends AsyncTask<Object, Void, String> {

    public static final String TAG = "OkHttpHandlerPostRecord";
    private String postURL = "http://api-volalizer.rhcloud.com/add";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected String doInBackground(Object[] params) {
        String json = params[0].toString();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(postURL)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
