package com.suslov.basejava.util;

import com.suslov.basejava.exception.SerializeException;
import com.suslov.basejava.exception.StorageException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;

public class XMLParser {
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public XMLParser(Class... classesBeBound) {
        try {
            JAXBContext context = JAXBContext.newInstance(classesBeBound);

            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException exp) {
            throw new SerializeException("Error initializing XML parser", exp);
        }
    }

    public <T> T unmarshall(Reader reader) {
        try {
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException exp) {
            throw new SerializeException("Error XML unmarshalling from reader to object", exp);
        }
    }

    public void marshall(Object instance, Writer writer) {
        try {
            marshaller.marshal(instance, writer);
        } catch (JAXBException exp) {
            throw new SerializeException("Error XML marshalling object to writer", exp);
        }
    }
}
