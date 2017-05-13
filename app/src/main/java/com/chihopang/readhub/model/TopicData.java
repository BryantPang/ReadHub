package com.chihopang.readhub.model;

import java.util.List;

public class TopicData {
  private List<Topic> data;
  private int pageSize;
  private int totalItems;
  private int totalPages;

  @Override public String toString() {
    return "TopicData{" +
        "data=" + data +
        ", pageSize=" + pageSize +
        ", totalItems=" + totalItems +
        ", totalPages=" + totalPages +
        '}';
  }

  public List<Topic> getData() {
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
}
