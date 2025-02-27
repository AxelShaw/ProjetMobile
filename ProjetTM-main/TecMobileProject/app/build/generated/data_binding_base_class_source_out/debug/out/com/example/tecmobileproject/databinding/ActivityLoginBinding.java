// Generated by view binder compiler. Do not edit!
package com.example.tecmobileproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.tecmobileproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnLoginActivityLogin;

  @NonNull
  public final ImageView btnLoginWithGoogle;

  @NonNull
  public final EditText etLoginActivityEmail;

  @NonNull
  public final EditText etLoginActivityPassword;

  @NonNull
  public final ImageView imageLoginActivityLogo;

  @NonNull
  public final TextView tvLoginActivityMail;

  @NonNull
  public final TextView tvLoginActivityPassword;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnLoginActivityLogin, @NonNull ImageView btnLoginWithGoogle,
      @NonNull EditText etLoginActivityEmail, @NonNull EditText etLoginActivityPassword,
      @NonNull ImageView imageLoginActivityLogo, @NonNull TextView tvLoginActivityMail,
      @NonNull TextView tvLoginActivityPassword) {
    this.rootView = rootView;
    this.btnLoginActivityLogin = btnLoginActivityLogin;
    this.btnLoginWithGoogle = btnLoginWithGoogle;
    this.etLoginActivityEmail = etLoginActivityEmail;
    this.etLoginActivityPassword = etLoginActivityPassword;
    this.imageLoginActivityLogo = imageLoginActivityLogo;
    this.tvLoginActivityMail = tvLoginActivityMail;
    this.tvLoginActivityPassword = tvLoginActivityPassword;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_loginActivity_login;
      Button btnLoginActivityLogin = ViewBindings.findChildViewById(rootView, id);
      if (btnLoginActivityLogin == null) {
        break missingId;
      }

      id = R.id.btn_login_with_google;
      ImageView btnLoginWithGoogle = ViewBindings.findChildViewById(rootView, id);
      if (btnLoginWithGoogle == null) {
        break missingId;
      }

      id = R.id.et_loginActivity_Email;
      EditText etLoginActivityEmail = ViewBindings.findChildViewById(rootView, id);
      if (etLoginActivityEmail == null) {
        break missingId;
      }

      id = R.id.et_loginActivity_Password;
      EditText etLoginActivityPassword = ViewBindings.findChildViewById(rootView, id);
      if (etLoginActivityPassword == null) {
        break missingId;
      }

      id = R.id.image_loginActivity_logo;
      ImageView imageLoginActivityLogo = ViewBindings.findChildViewById(rootView, id);
      if (imageLoginActivityLogo == null) {
        break missingId;
      }

      id = R.id.tv_loginActivity_mail;
      TextView tvLoginActivityMail = ViewBindings.findChildViewById(rootView, id);
      if (tvLoginActivityMail == null) {
        break missingId;
      }

      id = R.id.tv_loginActivity_password;
      TextView tvLoginActivityPassword = ViewBindings.findChildViewById(rootView, id);
      if (tvLoginActivityPassword == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, btnLoginActivityLogin,
          btnLoginWithGoogle, etLoginActivityEmail, etLoginActivityPassword, imageLoginActivityLogo,
          tvLoginActivityMail, tvLoginActivityPassword);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
