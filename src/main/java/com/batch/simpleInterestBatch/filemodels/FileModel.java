package com.batch.simpleInterestBatch.filemodels;

import java.io.Serializable;

public class FileModel implements Serializable {
    private static final long serialVersionID = 1L;

    double principal;
    double rate;
    double time;

    public FileModel(double principal, double rate, double time) {
        this.principal = principal;
        this.rate = rate;
        this.time = time;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
