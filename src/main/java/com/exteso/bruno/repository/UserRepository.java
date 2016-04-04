package com.exteso.bruno.repository;

import com.exteso.bruno.model.User;

import ch.digitalfondue.npjt.Bind;
import ch.digitalfondue.npjt.Query;
import ch.digitalfondue.npjt.QueryRepository;

@QueryRepository
public interface UserRepository {

    @Query("select count(*) from b_user where provider = :provider and username = :username")
    int count(@Bind("provider") String provider, @Bind("username") String username);

    @Query("insert into b_user(provider, username) values (:provider, :username)")
    int create(@Bind("provider") String provider, @Bind("username") String username);
    
    @Query("select id from b_user where provider = :provider and username = :username")
    Long getId(@Bind("provider") String provider, @Bind("username") String username);

    @Query("select * from b_user where id = :userId")
    User findById(@Bind("userId") long userId);
    
    @Query("select * from b_user where provider = :provider and username = :username")
    User findBy(@Bind("provider") String provider, @Bind("username") String username);
}
