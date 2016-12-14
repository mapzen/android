package com.mapzen.places.api;

/**
 * Generic interface for representing data contained in a buffer.
 * @param <T>
 */
public interface DataBuffer<T> {
  /**
   * Returns the number of objects in the buffer.
   * @return
   */
  int getCount();

  /**
   * Returns the object at a given index.
   * @param index
   * @return
   */
  T get(int index);
}
