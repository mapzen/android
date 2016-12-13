package com.mapzen.places.api;

public interface DataBuffer<T> {
  int getCount();
  T get(int index);
}
