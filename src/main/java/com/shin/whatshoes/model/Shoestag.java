package com.shin.whatshoes.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString

public class Shoestag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shoestagid")
    private Long shoesTagId;

    @Column(name="shoestagbody")
    private String shoesTagBody;

    @Column(name="shoestagregdate")
    private Date shoesTagRegdate;

    @ManyToOne
    @JoinColumn(name = "tagshoesid")
    private Shoes tagShoes;

}
