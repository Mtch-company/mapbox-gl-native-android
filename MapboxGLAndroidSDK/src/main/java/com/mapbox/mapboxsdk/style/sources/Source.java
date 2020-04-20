package com.mapbox.mapboxsdk.style.sources;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.LibraryLoader;
import com.mapbox.mapboxsdk.utils.ThreadUtils;

/**
 * Base Peer class for sources. see source.hpp for the other half of the peer.
 */
public abstract class Source {

  private static final String TAG = "Mbgl-Source";

  @Keep
  private long nativePtr;

  protected boolean detached;

  static {
    LibraryLoader.load();
  }

  /**
   * Internal use
   *
   * @param nativePtr - pointer to native peer
   */
  @Keep
  protected Source(long nativePtr) {
    checkThread();
    this.nativePtr = nativePtr;
  }

  public Source() {
    checkThread();
  }

  /**
   * Validates if source interaction is happening on the UI thread
   */
  protected void checkThread() {
    ThreadUtils.checkThread(TAG);
  }

  /**
   * Retrieve the source id
   *
   * @return the source id
   */
  @NonNull
  public String getId() {
    checkThread();
    return nativeGetId();
  }

  /**
   * Retrieve the source attribution.
   * <p>
   * Will return an empty String if no attribution is available.
   * </p>
   *
   * @return the string representation of the attribution in html format
   */
  @NonNull
  public String getAttribution() {
    checkThread();
    return nativeGetAttribution();
  }

  /**
   * Retrieve current pre-fetching zoom delta.
   *
   * @return current zoom delta or null if not set.
   */
  @Nullable
  public Integer getPrefetchZoomDelta() {
    return nativeGetPrefetchZoomDelta();
  }

  /**
   * Set the tile pre-fetching zoom delta for current source. Pre-fetching makes sure that a low-resolution
   * tile at the (current_zoom_level - delta) is rendered as soon as possible at the
   * expense of a little bandwidth.
   * If delta has not been set or set to null, it will use the value in MapboxMap instance.
   *
   * @param delta zoom delta
   */
  public void setPrefetchZoomDelta(@Nullable Integer delta) {
    nativeSetPrefetchZoomDelta(delta);
  }

  /**
   * When a set of tiles for a current zoom level is being rendered and some of the
   * ideal tiles that cover the screen are not yet loaded, parent tile could be
   * used instead. This might introduce unwanted rendering side-effects, especially
   * for raster tiles that are overscaled multiple times. This method sets the maximum
   * limit for how much a parent tile can be overscaled.
   *
   * @param maxOverscaleFactor maximum overscale factor
   */
  public void setMaxOverscaleFactorForParentTiles(@Nullable Integer maxOverscaleFactor) {
    nativeSetMaxOverscaleFactorForParentTiles(maxOverscaleFactor);
  }

  /**
   * Retrieve current maximum overscale factor for parent tiles.
   *
   * @return current maximum overscale factor or null if not set.
   */
  @Nullable
  public Integer getMaxOverscaleFactorForParentTiles() {
    return nativeGetMaxOverscaleFactorForParentTiles();
  }

  /**
   * Internal use
   *
   * @return the native peer pointer
   */
  public long getNativePtr() {
    return nativePtr;
  }

  @NonNull
  @Keep
  protected native String nativeGetId();

  @NonNull
  @Keep
  protected native String nativeGetAttribution();

  @NonNull
  @Keep
  protected native Integer nativeGetPrefetchZoomDelta();

  @NonNull
  @Keep
  protected native void nativeSetPrefetchZoomDelta(Integer delta);

  @NonNull
  @Keep
  protected native void nativeSetMaxOverscaleFactorForParentTiles(Integer maxOverscaleFactor);

  @NonNull
  @Keep
  protected native Integer nativeGetMaxOverscaleFactorForParentTiles();

  public void setDetached() {
    detached = true;
  }
}
