package com.mapzen.places.api.internal;

import com.mapzen.places.api.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PlaceDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
  public static final String TAG = PlaceDialogFragment.class.getSimpleName();

  private static final String ARG_NAME_TITLE = "title";

  private AlertDialog dialog;
  private PlaceDialogListener listener;

  public static PlaceDialogFragment newInstance(String title) {
    final PlaceDialogFragment fragment = new PlaceDialogFragment();
    Bundle args = new Bundle();
    args.putString(ARG_NAME_TITLE, title);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final String title = getArguments().getString(ARG_NAME_TITLE);
    dialog = new AlertDialog.Builder(getActivity())
        .setTitle(R.string.use_this_place)
        .setMessage(title)
        .setNegativeButton(R.string.change_location, this)
        .setPositiveButton(R.string.select, this)
        .create();
    return dialog;
  }

  public void setMessage(String detail) {
    dialog.setMessage(detail);
    getArguments().putString(ARG_NAME_TITLE, detail);
  }

  @Override public void onClick(DialogInterface dialog, int which) {
    if (listener == null) {
      return;
    }

    if (which == DialogInterface.BUTTON_POSITIVE) {
      listener.onPlaceConfirmed();
    } else {
      listener.onPlaceDismissed();
    }
  }

  public void setListener(PlaceDialogListener listener) {
    this.listener = listener;
  }

  public interface PlaceDialogListener {
    void onPlaceConfirmed();
    void onPlaceDismissed();
  }
}
