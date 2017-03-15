package com.mapzen.places.api.internal;

import com.mapzen.places.api.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Confirmation dialog shown to the user when a POI has been selected.
 */
public class PlaceDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
  public static final String TAG = PlaceDialogFragment.class.getSimpleName();

  private static final String ARG_NAME_TITLE = "title";

  private AlertDialog dialog;
  private PlaceDialogListener listener;

  /**
   * Create new instance of the dialog fragment.
   *
   * @param title text to be displayed in the dialog.
   * @return a new instance of the dialog fragment.
   */
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

  /**
   * Update text displayed in the dialog.
   *
   * @param detail new text to display.
   */
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

  /**
   * Set place dialog listener.
   *
   * @param listener the callbacks to invoke when the dialog is confirmed or dismissed.
   */
  public void setListener(PlaceDialogListener listener) {
    this.listener = listener;
  }

  /**
   * Dialog listener interface.
   */
  public interface PlaceDialogListener {
    /**
     * Positive button click listener.
     */
    void onPlaceConfirmed();

    /**
     * Negative button click listener.
     */
    void onPlaceDismissed();
  }
}
