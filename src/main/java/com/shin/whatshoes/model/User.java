package com.shin.whatshoes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
public class User {

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min =5, max = 30, message = "Please enter a 7 and 30 characters")
    @Email(message = "Please enter email type")
    @Id
    @Column(name = "userid")
    private String userId;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min =5, max = 199, message = "Please enter a 5 and 30 characters")
    @Column(name = "userpw")
    private String userPw;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min =2, max = 50, message = "Please enter a 2 and 50 characters")
    @Column(name = "username")
    private String userName;

    @NotBlank(message = "Please enter a non-empty value")
    @Size(min =1, max = 10, message = "Please enter a 1 and 4 characters")
    @Column(name = "usergender")
    private String userGender;

    @NotNull(message = "Please enter a date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "userbirth")
    private Date userBirth;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "userupdate")
    private Date userUpdate;

    @Column(name = "userregdate")
    private Date userRegdate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "userauth",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "authid"))
    private Set<Auth> auths = new LinkedHashSet<>();

//    model.shoes 에 get, orphanRemoval = false 디폴트임 (true 부모 delete시 자식 delete 구현할때 ?지금은 DB상 설정으로 구현)
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
//    EntityGraph 사용하여 JsonIgnore 기능 대체 가능
    @JsonIgnore
//  fetch = data select 조건(무조건, 필요시)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Shoes> shoes = new ArrayList<>();
}