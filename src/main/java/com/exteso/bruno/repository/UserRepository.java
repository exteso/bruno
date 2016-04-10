package com.exteso.bruno.repository;

import java.util.Date;

import com.exteso.bruno.model.User;
import com.exteso.bruno.model.User.UserType;

import ch.digitalfondue.npjt.Bind;
import ch.digitalfondue.npjt.Query;
import ch.digitalfondue.npjt.QueryRepository;

@QueryRepository
public interface UserRepository {

    @Query("select count(*) from b_user where provider = :provider and username = :username")
    int count(@Bind("provider") String provider, @Bind("username") String username);

    @Query("insert into b_user(provider, username, first_name, last_name, email_address, user_type) values (:provider, :username, :firstname, :lastname, :email, :usertype)")
    int create(@Bind("provider") String provider, @Bind("username") String username, 
               @Bind("firstname") String firstname, @Bind("lastname") String lastname, @Bind("email") String email, @Bind("usertype") UserType usertype);
    
    @Query("select id from b_user where provider = :provider and username = :username")
    Long getId(@Bind("provider") String provider, @Bind("username") String username);

    @Query("select * from b_user where id = :userId")
    User findById(@Bind("userId") long userId);
    
    @Query("select * from b_user where provider = :provider and username = :username")
    User findBy(@Bind("provider") String provider, @Bind("username") String username);

    @Query("update b_user set user_request_type = :userType, user_request_type_date = :date where provider = :provider and username = :username")
    int setRequestAs(@Bind("provider") String provider, @Bind("username") String username, @Bind("userType") UserType userType, @Bind("date") Date date);
}
