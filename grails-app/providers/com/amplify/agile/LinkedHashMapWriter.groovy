package com.amplify.agile

import grails.converters.JSON
import org.grails.jaxrs.support.MessageBodyWriterSupport

import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.Provider

@Provider
@Produces('application/json')
class LinkedHashMapWriter extends MessageBodyWriterSupport<LinkedHashMap> {

    @Override
    protected void writeTo(LinkedHashMap t, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        def writer = new OutputStreamWriter(entityStream)
        def converter = new JSON(t)
        converter.render(writer)
    }
}
