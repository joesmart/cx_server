package com.server.cx.thumbnail;


public interface ThumbnailStrategy {

  /**
   * Construct a DecodeAndCaptureFrames which reads and captures frames from a video file.
   * 
   * @param filename the name of the media file to read
   */

  public String generate(String filename);

  // public File dumpImageToFile(BufferedImage image);

  public void setStoreDirectory(String path);

  public long getSize();

  public void setSourceFileSize(Long size);
}
