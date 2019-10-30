package com.example.networkmodule.singletonNetWork.retrofit;

import com.example.networkmodule.singletonNetWork.retrofit.retroFit_modelsExample.SOAnswersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ddopik on 9/19/2017.
 */

public interface RetrofitInterFaceExamle {

    //"https://arabic.cnn.com/middle_east/rss"
// With Retrofit 2,
//    endpoints are defined inside of an interface using special retrofit annotations to encode details about the parameters and request method.
//    In addition,
//    the return value is always a parameterized Call<T> object such as Call<User>. If you do not need any type-specific response,
//    you can specify return value as simply Call<ResponseBody>.

//    1-Every method of an interface represents one possible API call.
//    2-It must have a HTTP annotation (GET, POST, etc.) to specify the request type and the relative URL.
//    3-The return value wraps the response in a Call object with the type of the expected result.


//    --------------------------------------
//  An invocation of a Retrofit method that sends a request to a webserver and returns a response.
//   Each call yields its own HTTP request and response pair.
//  Use clone to make multiple calls with the same parameters to the same webserver;
//  this may be used to implement polling or to retry a failed call.
//    Calls may be executed synchronously with execute, or asynchronously with enqueue.
//   In either case the call can be canceled at any time with cancel.
//  A call that is busy writing its request or reading its response may receive a IOException;
//   this is working as designed.
//    Type parameters:
//<T> - Successful response body type.
//    --------------------------------------


    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers();

//    we added a query parameter for us to filter the data from the serve

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers(@Query("tagged") String tags);
    //    You can use replacement blocks and query parameters to adjust the URL.
//    A replacement block is added to the relative URL with {}.
//    With the help of the @Path annotation on the method parameter,
//    the value of that parameter is bound to the specific replacement block.

//    @GET("users/{name}/commits")
//            Call<List<Commit>> getCommitsByName(@Path("name") String name)

}
