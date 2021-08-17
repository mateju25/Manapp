package service;

import model.Link;
import model.LinkList;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class LinkService {
    public LinkList findAll() {
        LinkList lst = null;
        try {
            File file = new File("links.xml");
            if (!file.exists()) {
                System.out.println("File links.xml is not available.");
                return null;
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(LinkList.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            lst = (LinkList) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return lst;
    }

    public void saveLinks(LinkList linkList) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(LinkList.class);

            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshallerObj.marshal(linkList, new FileOutputStream("links.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
