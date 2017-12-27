package com.chihopang.readhub.model;

import java.util.List;

public class ApiData {
  private List<Topic> data;
  private int pageSize;
  private int totalItems;
  private int totalPages;

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

  public String getNextPageUrl() {
    return "?lastCursor=" + data.get(data.size() - 1).getOrder() + "&pageSize=10";
  }
}
