package network.volly.response_module;


import com.google.gson.annotations.SerializedName;

/**
 * Created by ddopik..@_@
 */
public class CreateOrderResponse extends BaseResponse {


    @SerializedName("order")
    public String order;


    @Override
    public String toString() {
        return "CreateOrderResponse{" +
                "order=" + order +
                '}';
    }
}
