package com.csc301.team7.era;

/**
 * Created by MuhammadAun on 2017-03-15.
 */
public class Bookmark {

    private String name;
    private String issue;
    private String solution;

    public Bookmark() {
    }

    public Bookmark(String name, String issue, String solution) {
        this.name = name;
        this.issue = issue;
        this.solution = solution;

    }

    public String getName() {
        return name;
    }

    public String getIssue() {
        return issue;
    }

    public String getSolution() {
        return solution;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
