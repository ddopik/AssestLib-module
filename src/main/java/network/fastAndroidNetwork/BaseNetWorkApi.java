package network.fastAndroidNetwork;

import com.androidnetworking.common.Priority;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import io.reactivex.Observable;
import network.volly.response_module.BaseResponse;

/**
 * Created by abdalla-maged on 3/29/18.
 */

public class BaseNetWorkApi {

    private static final String BASE_URL = "http://dev.spade.studio/mek-apis/public/api/v1/{lang}";
    private static final String REQUEST_URL = BASE_URL + "/causes";
    private static final String PAGE_NUMBER = "page";


    public static io.reactivex.Observable<BaseResponse> makeGetquestRequest(String lang, String key) {
        return Rx2AndroidNetworking.get(REQUEST_URL)
                .addPathParameter("lang", lang)
                .addQueryParameter("key", String.valueOf(key))
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(BaseResponse.class);
    }

    public static Observable<Example> makeGetquestRequestTemp(String lang, String url) {
        return Rx2AndroidNetworking.get(url)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(Example.class);
    }

    public static io.reactivex.Observable<BaseResponse> makePostRequest(JSONObject jsonObject) {
        return Rx2AndroidNetworking.post(REQUEST_URL)
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(BaseResponse.class);
    }

//    public static void complaint(String message, File file, SendMessageCallBacks sendMessageCallBacks) {
//        AndroidNetworking.upload(REQUEST_URL)
//                .addHeaders("Content-Type", "multipart/form-data")
//                .addMultipartParameter("message", message)
//                .addMultipartFile("files[]", file) //todo "files[]" is A key According to back End
//                .setPriority(Priority.HIGH)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean success = response.getBoolean("success");
//                            if (success) {
//                                sendMessageCallBacks.onMessageSent();
//                            } else {
//                                sendMessageCallBacks.onMessageSentFailed();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            sendMessageCallBacks.onMessageSentFailed();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        sendMessageCallBacks.onMessageSentFailed();
//                    }
//                });
//    }

    public class Example {

        @SerializedName("success")
        @Expose
        private boolean success;
        @SerializedName("data")
        @Expose
        private Data data;

        public class Data {

            @SerializedName("id")
            @Expose
            private int id;
            @SerializedName("name")
            @Expose
            private Object name;
            @SerializedName("body")
            @Expose
            private Object body;
            @SerializedName("position")
            @Expose
            private Object position;
            @SerializedName("image")
            @Expose
            private String image;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getBody() {
                return body;
            }

            public void setBody(Object body) {
                this.body = body;
            }

            public Object getPosition() {
                return position;
            }

            public void setPosition(Object position) {
                this.position = position;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}