// Generated by view binder compiler. Do not edit!
package edu.gatech.campusconnect.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import edu.gatech.campusconnect.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAdminRemoveBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button adDeleteDelete;

  @NonNull
  public final TextView adDeleteTitle;

  @NonNull
  public final TextView adDeleteWarning;

  @NonNull
  public final TextView adRemove;

  @NonNull
  public final FrameLayout container;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final RadioGroup radioGroup;

  private FragmentAdminRemoveBinding(@NonNull FrameLayout rootView, @NonNull Button adDeleteDelete,
      @NonNull TextView adDeleteTitle, @NonNull TextView adDeleteWarning,
      @NonNull TextView adRemove, @NonNull FrameLayout container, @NonNull ProgressBar loading,
      @NonNull RadioGroup radioGroup) {
    this.rootView = rootView;
    this.adDeleteDelete = adDeleteDelete;
    this.adDeleteTitle = adDeleteTitle;
    this.adDeleteWarning = adDeleteWarning;
    this.adRemove = adRemove;
    this.container = container;
    this.loading = loading;
    this.radioGroup = radioGroup;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAdminRemoveBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAdminRemoveBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_admin_remove, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAdminRemoveBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.adDeleteDelete;
      Button adDeleteDelete = ViewBindings.findChildViewById(rootView, id);
      if (adDeleteDelete == null) {
        break missingId;
      }

      id = R.id.adDeleteTitle;
      TextView adDeleteTitle = ViewBindings.findChildViewById(rootView, id);
      if (adDeleteTitle == null) {
        break missingId;
      }

      id = R.id.adDeleteWarning;
      TextView adDeleteWarning = ViewBindings.findChildViewById(rootView, id);
      if (adDeleteWarning == null) {
        break missingId;
      }

      id = R.id.adRemove;
      TextView adRemove = ViewBindings.findChildViewById(rootView, id);
      if (adRemove == null) {
        break missingId;
      }

      FrameLayout container = (FrameLayout) rootView;

      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);
      if (loading == null) {
        break missingId;
      }

      id = R.id.radioGroup;
      RadioGroup radioGroup = ViewBindings.findChildViewById(rootView, id);
      if (radioGroup == null) {
        break missingId;
      }

      return new FragmentAdminRemoveBinding((FrameLayout) rootView, adDeleteDelete, adDeleteTitle,
          adDeleteWarning, adRemove, container, loading, radioGroup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
