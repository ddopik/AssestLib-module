package bases;

/**
 * Created by abdalla-maged on 3/27/18.
 */

/**
 * this interface should have common method that all Presenter interface should have
 **/
public interface BasePresenter<T extends BaseView> {

    void setView(T view);
}
