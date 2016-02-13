/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebook.tutorial.resources;

import com.codahale.metrics.annotation.Timed;
import com.ebook.tutorial.api.ContactApi;
import com.ebook.tutorial.db.ContactDAO;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mili
 */
@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private static final Logger LOGGER = LoggerFactory.
            getLogger(ContactResource.class);

    private final ContactDAO contactDao;

    public ContactResource(DBI jdbi) {
        contactDao = jdbi.onDemand(ContactDAO.class);
    }

    @GET
    @Timed
    @Path("/{id}")
    public ContactApi getContact(@PathParam("id") int id) {

        //return Response.ok("{contact_id: " + id + ", name: \"Dummy Name\",phone: \"+0123456789\" }").build();
        ContactApi contact = contactDao.getContactById(id);
        return contact;
    }

    @POST
    public Response createContact(ContactApi contact) throws URISyntaxException {
        int newContactId
                = contactDao.createContact(contact.getFirstName(),
                        contact.getLastName(), contact.getPhone());
        return Response
                .created(new URI(String.valueOf(newContactId)))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        contactDao.deleteContact(id);
        return Response
                .noContent()
                .build();
    }

    @PUT
    @Path("/{id}")
    public ContactApi updateContact(
            @PathParam("id") int id, ContactApi contact) {
        contactDao.updateContact(id, contact.getFirstName(),
                contact.getLastName(), contact.getPhone());
        return contact;
    }
}
