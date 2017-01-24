package com.mapzen.android.graphics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class TestLayoutInflater extends LayoutInflater {
  private int resource;
  private ViewGroup root;

  TestLayoutInflater(Context context) {
    super(context);
  }

  @Override public View inflate(int resource, ViewGroup root) {
    this.resource = resource;
    this.root = root;
    return null;
  }

  @Override public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
    this.resource = resource;
    this.root = root;
    return null;
  }

  @Override public LayoutInflater cloneInContext(Context newContext) {
    return null;
  }

  int getResId() {
    return resource;
  }

  ViewGroup getRoot() {
    return root;
  }
}
