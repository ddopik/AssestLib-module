package ui_componanets.widgets;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by abdalla_maged on 10/18/2018.
 */


/**
 * This class is to add padding around RecyclerView thumbnail images.
 * The padding-right will be added to all the thumbnail images but not to the last item in the list.
 * <p>
 * RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
 * recyclerView.setLayoutManager(mLayoutManager);
 * recyclerView.setItemAnimator(new DefaultItemAnimator());
 * int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
 * recyclerView.addItemDecoration(new SpacesItemDecoration(space));
 * recyclerView.setAdapter(mAdapter);
 **/

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            outRect.left = space;
            outRect.right = 0;
        } else {
            outRect.right = space;
            outRect.left = 0;
        }
    }
}