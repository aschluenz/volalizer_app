package volalizer.volalizer.network;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import volalizer.volalizer.models.Record;

/**
 * Created by andyschlunz on 22.07.16.
 */
public class OkHttpHandlerGetAllRecords extends AsyncTask<String, Boolean, Record[]> {

    public static final String TAG = "OkHttpHandlerGetAllRecords";
    private String getURL = "http://api-volalizer.rhcloud.com/get/";
    public AsyncResponse delegate = null;


    public interface AsyncResponse {
        void processFinish(Record[] output);
    }

    public OkHttpHandlerGetAllRecords(AsyncResponse delegate) {
        this.delegate = delegate;
    }


    @Override
    protected Record[] doInBackground(String... params) {
        String userIMEI = params[0];
        String getUrl = getURL + userIMEI;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(getUrl)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = responses.body().string();

            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Record>>() {
            }.getType();
            Collection<Record> allRecord = gson.fromJson(result, collectionType);
            return allRecord.toArray(new Record[allRecord.size()]);
            // JSONArray responseData = responses.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Record[] records) {
        delegate.processFinish(records);
    }
}