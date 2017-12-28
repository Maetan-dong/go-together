package gotogether.maetandong.com.gotogether;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by JAY on 2017. 12. 27..
 */
public final class HttpUtil {
    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public final String TAG = this.getClass().getSimpleName();
    private String mMessage;
    private String mToken;

    public HttpUtil(String message) throws Exception {
        mMessage = message;
        mToken = "AAAA8Sbhpto:APA91bHH91uF8zxQijUm4IzSybiq_LhY_b0FL58C-RwhWsAzTNW8ep0p8bzo-BvXtX0lPNf3V_cmtQuek7wrCp7WKVqQp04RK4BjLspCnNISWyI469K6TcIF4yIMzDqPYpjQ10uxTDKz";
    }

    public void run() throws Exception {
        RequestBody body = RequestBody.create(JSON, bowlingJson(mMessage));

        Log.d(TAG, bowlingJson(mMessage));

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .header("Authorization", "key=" + mToken)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    Log.d(TAG, responseBody.string());
                    //System.out.println(responseBody.string());
                }
            }
        });
    }

    String bowlingJson(String message) {
        return "{\"to\": \"/topics/news\", " +
                " \"priority\" : \"high\"," +
                "\"notification\": {" +
                "\"title\" : \"go-together\"," +
                " \"sound\" : \"default\"," +
                "\"body\": \""+ message + "\"}," +
                "\"data\": {\"message\": \"This is a Firebase Cloud Messaging Topic Message!\"},}";
    }
}