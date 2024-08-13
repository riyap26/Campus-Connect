// Generated by view binder compiler. Do not edit!
package edu.gatech.campusconnect.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import edu.gatech.campusconnect.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentBasicCreateBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final FrameLayout container;

  @NonNull
  public final EditText createDate;

  @NonNull
  public final EditText createDescription;

  @NonNull
  public final Button createEvent;

  @NonNull
  public final EditText createLocation;

  @NonNull
  public final EditText createName;

  @NonNull
  public final EditText createTime;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final RadioGroup radioGroup;

  private FragmentBasicCreateBinding(@NonNull FrameLayout rootView, @NonNull FrameLayout container,
      @NonNull EditText createDate, @NonNull EditText createDescription,
      @NonNull Button createEvent, @NonNull EditText createLocation, @NonNull EditText createName,
      @NonNull EditText createTime, @NonNull ProgressBar loading, @NonNull RadioGroup radioGroup) {
    this.rootView = rootView;
    this.container = container;
    this.createDate = createDate;
    this.createDescription = createDescription;
    this.createEvent = createEvent;
    this.createLocation = createLocation;
    this.createName = createName;
    this.createTime = createTime;
    this.loading = loading;
    this.radioGroup = radioGroup;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentBasicCreateBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentBasicCreateBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_basic_create, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentBasicCreateBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      FrameLayout container = (FrameLayout) rootView;

      id = R.id.createDate;
      EditText createDate = ViewBindings.findChildViewById(rootView, id);
      if (createDate == null) {
        break missingId;
      }

      id = R.id.createDescription;
      EditText createDescription = ViewBindings.findChildViewById(rootView, id);
      if (createDescription == null) {
        break missingId;
      }

      id = R.id.createEvent;
      Button createEvent = ViewBindings.findChildViewById(rootView, id);
      if (createEvent == null) {
        break missingId;
      }

      id = R.id.createLocation;
      EditText createLocation = ViewBindings.findChildViewById(rootView, id);
      if (createLocation == null) {
        break missingId;
      }

      id = R.id.createName;
      EditText createName = ViewBindings.findChildViewById(rootView, id);
      if (createName == null) {
        break missingId;
      }

      id = R.id.createTime;
      EditText createTime = ViewBindings.findChildViewById(rootView, id);
      if (createTime == null) {
        break missingId;
      }

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

      return new FragmentBasicCreateBinding((FrameLayout) rootView, container, createDate,
          createDescription, createEvent, createLocation, createName, createTime, loading,
          radioGroup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
