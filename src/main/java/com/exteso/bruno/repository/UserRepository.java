package com.exteso.bruno.repository;

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
}
