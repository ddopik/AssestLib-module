package bases;

/**
 * Created by abdalla-maged on 3/27/18.
 */
/**
 * this interface should have common method that all view interface should have
 **/
public interface BaseView {
    void onError(String message);

    void onError(int resID);
}
