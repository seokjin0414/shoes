package com.shin.whatshoes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Resale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resaleid")
    private Long resaleId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "resaleshoesid")
    private Shoes resaleShoes;

    @Column(name = "resaleshoessize")
    private String resaleShoesSize;

    @Column(name = "resaleprice")
    private Integer resalePrice;

    @Column(name = "resaledate")
    private Date resaleDate;

    @Column(name = "resaleregdate")
    private Date resaleRegdate;
}
