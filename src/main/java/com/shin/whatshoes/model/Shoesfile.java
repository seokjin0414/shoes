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
public class Shoesfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shoesfileid")
    private Long shoesFileId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fileshoesid")
    private Shoes fileShoes;

    @Column(name="shoesfilename")
    private String shoesFileName;

    @Column(name="shoesfileregdate")
    private Date shoesFileRegdate;

}
