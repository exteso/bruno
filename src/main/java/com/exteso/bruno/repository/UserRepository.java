package com.exteso.bruno.repository;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.util.Assert;

import com.exteso.bruno.model.User;
import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.model.User.UserType;

import ch.digitalfondue.npjt.Bind;
import ch.digitalfondue.npjt.Query;
import ch.digitalfondue.npjt.QueryRepository;

@QueryRepository
public interface UserRepository {

    @Query("select count(*) from b_user where provider = :provider and username = :username")
    int count(@Bind("provider") String provider, @Bind("username") String username);

    @Query("insert into b_user(provider, username, first_name, last_name, email_address, user_type) values (:provider, :username, :firstname, :lastname, :email, :usertype)")
    int create(@Bind("provider") String provider, @Bind("username") String username, @Bind("firstname") String firstname, @Bind("lastname") String lastname,
            @Bind("email") String email, @Bind("usertype") UserType usertype);

    @Query("select id from b_user where provider = :provider and username = :username")
    Long getId(@Bind("provider") String provider, @Bind("username") String username);

    @Query("select * from b_user where id = :userId")
    User findById(@Bind("userId") long userId);

    @Query("select * from b_user where provider = :provider and username = :username")
    User findBy(@Bind("provider") String provider, @Bind("username") String username);
    
    @Query("select user_locale from b_user where provider = :provider and username = :username")
    String getLocale(@Bind("provider") String provider, @Bind("username") String username);

    @Query("update b_user set user_request_type = :userType, user_request_type_date = :date, "//
            + " company_name = :company_name, "//
            + " company_field_of_work = :company_field_of_work, "//
            + " company_address = :company_address, "//
            + " company_phone_number = :company_phone_number, "//
            + " company_email = :company_email, "//
            + " company_vat_id = :company_vat_id, "//
            + " company_notes = :company_notes "//
            + " where provider = :provider and username = :username")
    int setRequestAs(@Bind("provider") String provider, @Bind("username") String username, @Bind("userType") UserType userType, @Bind("date") Date date,
            @Bind("company_name") String companyName,//
            @Bind("company_field_of_work") int fieldOfWork,//
            @Bind("company_address") String address,//
            @Bind("company_phone_number") String phoneNumber,//
            @Bind("company_email") String contactEmail,//
            @Bind("company_vat_id") String vatNumber,//
            @Bind("company_notes") String notes);
    
    @Query("update b_user set user_locale = :locale where provider = :provider and username = :username")
    int setLocale(@Bind("provider") String provider, @Bind("username") String username, @Bind("locale") String languageTag);

    @Query("select * from b_user order by first_name DESC, last_name DESC, username DESC, provider DESC")
    List<User> findAll();

    @Query("update b_user set user_type = 'SERVICE_PROVIDER' where id = :userId")
    int confirmUserAsServiceProvider(@Bind("userId") long userId);

    default long getId(UserIdentifier ui) {
        return getId(ui.getProvider(), ui.getUsername());
    }

    default User findBy(UserIdentifier ui) {
        return findBy(ui.getProvider(), ui.getUsername());
    }

    default User findBy(Principal principal) {
        return findBy(UserIdentifier.from(principal));
    }

    default void ensureUserIsAdmin(Principal principal) {
        Assert.isTrue(findBy(principal).getUserType() == UserType.ADMIN, "user is not admin");
    }

	default Locale getUserLocale(UserIdentifier from) {
		return Locale.forLanguageTag(getLocale(from.getProvider(), from.getUsername()));
	}

	default void setUserLocale(UserIdentifier from, Locale locale) {
		setLocale(from.getProvider(), from.getUsername(), locale.toLanguageTag());
	}

}
