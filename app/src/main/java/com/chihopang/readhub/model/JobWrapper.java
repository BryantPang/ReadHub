package com.chihopang.readhub.model;

import java.util.List;

public class JobWrapper {
  private long id;
  private String uuid;
  private String jobTitle;
  private int jobCount;
  private int companyCount;
  private int salaryLower;
  private int salaryUpper;
  private int experienceLower;
  private int experienceUpper;
  private String publishDate;
  private String createdAt;
  private List<Job> jobsArray;
}