/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebook.tutorial.db;

import com.ebook.tutorial.api.ContactApi;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 *
 * @author mili
 */
public interface ContactDAO {
    //private static final Logger LOGGER = LoggerFactory.getLogger(ContactDAO.class);

    @MapResultAsBean
    @SqlQuery("select * from contact where id = :id")
    ContactApi getContactById(@Bind("id") int id);

    @GetGeneratedKeys
    @SqlUpdate("insert into contact (id, firstName, lastName,"
            + "phone) values (NULL, :firstName, :lastName, :phone)")
    int createContact(@Bind("firstName") String firstName,
            @Bind("lastName") String lastName, @Bind("phone") String phone);

    @SqlUpdate("update contact set firstName = :firstName, lastName ="
            + ":lastName, phone = :phone where id = :id")
    void updateContact(@Bind("id") int id, @Bind("firstName") String firstName,
            @Bind("lastName") String lastName, @Bind("phone") String phone);
    
    @SqlUpdate("delete from contact where id = :id")
    void deleteContact(@Bind("id") int id);
}
