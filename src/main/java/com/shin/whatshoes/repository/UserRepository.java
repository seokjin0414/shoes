package com.shin.whatshoes.repository;


import com.shin.whatshoes.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

// querydsl, Customized*** extends
public interface UserRepository extends JpaRepository<User, String>, QuerydslPredicateExecutor<User> {

    @EntityGraph(attributePaths = { "shoes" })
    List<User> findAll(); 

    User findByUserId(String userId);

//    @Query("select u from User u where u.userName like %?1%")
//    List<User> findByUserNameQuery(String userName);
//
//// ---- native query 사용 ----
//    @Query(value = "select * from user u where u.userName like %?1%", nativeQuery = true)
//    List<User> findByUserNameNativeQuery(String userName);
}
