package pl.com.eLeader.accountXmlProcessor.services.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import pl.com.eLeader.accountXmlProcessor.model.Account;
import pl.com.eLeader.accountXmlProcessor.model.AccountContainer;
import pl.com.eLeader.accountXmlProcessor.services.XmlService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class XmlServiceImplementation implements XmlService {

    private XmlMapper xmlMapper = new XmlMapper();
    private Scanner sc = new Scanner(System.in);

    @Override
    public String getFilePath() {
        System.out.println("Enter data file path ...");
        return sc.nextLine();
    }

    @Override
    public List<Account> mapXmlToList(File file) throws IOException {
        return xmlMapper.readValue(file, new TypeReference<List<Account>>() {
        });
    }

    @Override
    public void mapListToXml(File file, AccountContainer container) throws IOException {
        configureMapper();
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(file, container);
    }

    private void configureMapper() {
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
