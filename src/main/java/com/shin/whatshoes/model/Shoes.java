package com.shin.whatshoes.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
public class Shoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shoesid")
    private Long shoesId;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min =4, max = 100, message = "Please enter a 4 and 100 characters")
    @Column(name="shoesstyle")
    private String shoesStyle;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min =1, max = 30, message = "Please enter a 1 and 30 characters")
    @Column(name="shoesbrandid")
    private String brandId;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min =1, max = 30, message = "Please enter a 1 and 30 characters")
    @Column(name="shoesmodelid")
    private String modelId;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min = 1, max = 5, message = "Please enter a 1 and 5 characters")
    @Column(name="shoessizetype")
    private String shoesSizeType;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min = 1, max = 100, message = "Please enter a 1 and 100 characters")
    @Column(name="shoesname1")
    private String shoesName1;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min = 1, max = 100, message = "Please enter a 1 and 100 characters")
    @Column(name="shoesname2")
    private String shoesName2;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min = 3, max = 100, message = "Please enter a 3 and 100 characters")
    @Column(name="shoescolorway")
    private String shoesColorway;

    @NotNull(message = "Please enter a non-empty value")
    @Positive(message = "Only positive numbers can be entered")
    @Column(name="shoesprice")
    private Long shoesPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shoesuserid")
    private User user;

    @NotNull(message = "Please enter a date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="shoesrelease")
    private Date shoesRelease;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min = 5, max = 300, message = "Please enter a 5 and 300 characters")
    @Column(name="shoesurl")
    private String shoesUrl;

    @Column(name="shoesregdate")
    private Date shoesRegdate;

    @JsonIgnore
    @OneToMany(mappedBy = "fileShoes", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    private List<Shoesfile> shoesfiles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tagShoes", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Shoestag> shoestags = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "resaleShoes", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Resale> resales= new ArrayList<>();


}
