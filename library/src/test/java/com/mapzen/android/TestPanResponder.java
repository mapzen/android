package com.mapzen.android;

import com.mapzen.tangram.TouchInput;

public class TestPanResponder implements TouchInput.PanResponder {
  public float startX = 0f;
  public float startY = 0f;
  public float endX = 0f;
  public float endY = 0f;

  public float posX = 0f;
  public float posY = 0f;
  public float velocityX = 0f;
  public float velocityY = 0f;

  @Override public boolean onPan(float startX, float startY, float endX, float endY) {
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    return false;
  }

  @Override public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
    this.posX = posX;
    this.posY = posY;
    this.velocityX = velocityX;
    this.velocityY = velocityY;
    return false;
  }
}
