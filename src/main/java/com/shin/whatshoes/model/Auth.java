package com.shin.whatshoes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="authid")
    private Long authId;

    @Column(name="authname")
    private String authName;

    @JsonIgnore
    @ManyToMany(mappedBy = "auths")
    private List<User> users;
}
