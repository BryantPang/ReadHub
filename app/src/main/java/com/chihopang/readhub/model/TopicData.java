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

  public void setData(List<Topic> data) {
    this.data = data;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }
}
