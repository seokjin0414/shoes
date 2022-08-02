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
public class What {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="whatid")
    private Long whatId;

    @Column(name = "whatip")
    private String whatIp;

    @Column(name = "whatfilename")
    private String whatFileName;

    @Column(name = "whatresult")
    private String whatResult;

    @Column(name = "whatregdate")
    private Date whatRegdate;

}
