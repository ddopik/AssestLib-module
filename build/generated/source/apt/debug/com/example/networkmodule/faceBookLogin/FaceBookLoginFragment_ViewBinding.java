// Generated code from Butter Knife. Do not modify!
package com.example.networkmodule.faceBookLogin;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.networkmodule.R;
import com.facebook.login.widget.LoginButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FaceBookLoginFragment_ViewBinding implements Unbinder {
  private FaceBookLoginFragment target;

  private View view2131492952;

  @UiThread
  public FaceBookLoginFragment_ViewBinding(final FaceBookLoginFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.connectWithFbButton, "field 'facebook_button' and method 'setFacebook_button_acction'");
    target.facebook_button = Utils.castView(view, R.id.connectWithFbButton, "field 'facebook_button'", LoginButton.class);
    view2131492952 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setFacebook_button_acction(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FaceBookLoginFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.facebook_button = null;

    view2131492952.setOnClickListener(null);
    view2131492952 = null;
  }
}
