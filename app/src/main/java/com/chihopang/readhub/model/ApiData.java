package com.chihopang.readhub.model;

import java.util.List;

public class ApiData<T> {
  private List<T> data;
  private int pageSize;
  private int totalItems;
  private int totalPages;

  public List<T> getData() {
    return data;
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getTotalItems() {
    return totalItems;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public String getNextPageUrl() {
    return "?lastCursor=" + getLastCursor() + "&pageSize=10";
  }

  public String getLastCursor() {
    String lastCursor = null;
    Object lastData = data.get(data.size() - 1);
    if (lastData instanceof Topic) lastCursor = ((Topic) lastData).order;
    if (lastData instanceof News) lastCursor = ((News) lastData).getLastCursor();
    return lastCursor;
  }
}
