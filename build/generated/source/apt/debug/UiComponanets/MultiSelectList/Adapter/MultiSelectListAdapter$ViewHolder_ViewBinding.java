// Generated code from Butter Knife. Do not modify!
package UiComponanets.MultiSelectList.Adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.networkmodule.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MultiSelectListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MultiSelectListAdapter.ViewHolder target;

  @UiThread
  public MultiSelectListAdapter$ViewHolder_ViewBinding(MultiSelectListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.sourseListContainer = Utils.findRequiredViewAsType(source, R.id.source_row_container, "field 'sourseListContainer'", LinearLayout.class);
    target.source_name = Utils.findRequiredViewAsType(source, R.id.row_list_checkedtextview, "field 'source_name'", CheckedTextView.class);
    target.newsImage = Utils.findRequiredViewAsType(source, R.id.row_list_checkbox_image, "field 'newsImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MultiSelectListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sourseListContainer = null;
    target.source_name = null;
    target.newsImage = null;
  }
}
