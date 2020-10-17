package com.batch.simpleInterestBatch.entities;

import javax.persistence.*;

@Entity
@Table(name = "InterestModel")
public class  Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "principal", nullable = false)
    private Double principal;
    @Column(name = "rate", nullable = false)
    private Double rate;
    @Column(name = "sitime", nullable = false)
    private Double time;
    @Column(name = "result", nullable = false)
    private Double result;

    public Interest(Double principal, Double rate, Double time, Double result) {
        this.principal = principal;
        this.rate = rate;
        this.time = time;
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
