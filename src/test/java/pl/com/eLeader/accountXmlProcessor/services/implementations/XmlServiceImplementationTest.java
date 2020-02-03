package pl.com.eLeader.accountXmlProcessor.services.implementations;

import com.fasterxml.jackson.core.JsonParseException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.com.eLeader.accountXmlProcessor.model.Account;
import pl.com.eLeader.accountXmlProcessor.model.AccountContainer;
import pl.com.eLeader.accountXmlProcessor.services.XmlService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class XmlServiceImplementationTest {

    private XmlService service;
    private List<Account> accounts;
    private File file;
    private File outputFile;

    @Before
    public void setUp() throws Exception {
        service = new XmlServiceImplementation();
        accounts = new ArrayList<>();
        file = new File("foo.xml");
        outputFile = new File("outputFoo.xml");
    }

    @Test
    public void shouldMapFooXmlToList() throws IOException {
        //given
        String content = "<?xml version = \"1.0\"?>\n" +
                "<accounts>\n" +
                "    <account iban=\"PLL1109010140000071219812876\">\n" +
                "        <name>name7</name>\n" +
                "        <currency>PLN</currency>\n" +
                "        <balance>1.00</balance>\n" +
                "        <closingDate>2010-01-01</closingDate>\n" +
                "    </account>\n" +
                "<!--rekord prawidłowy-->\n" +
                "    <account iban=\"PL61109010140000071219812874\">\n" +
                "        <name>name8</name>\n" +
                "        <currency>PLN</currency>\n" +
                "        <balance>0</balance>\n" +
                "        <closingDate>2039-05-15</closingDate>\n" +
                "    </account>\n" +
                "\n" +
                "</accounts>\n";
        Account account1 = new Account("PLL1109010140000071219812876", "name7", "PLN", 1.00, LocalDate.of(2010, 1, 1));
        Account account2 = new Account("PL61109010140000071219812874", "name8", "PLN", 0.00, LocalDate.of(2039, 5, 15));
        accounts.add(account1);
        accounts.add(account2);
        FileUtils.writeStringToFile(file, content);
        //when
        List<Account> result = service.mapXmlToList(file);
        //then
        assertEquals(accounts, result);
    }

    @Test
    public void shouldMapFooXmlToListButExpectedObjectIsWrong() throws IOException {
        //given
        String content = "<?xml version = \"1.0\"?>\n" +
                "<accounts>\n" +
                "    <account iban=\"PLL1109010140000071219812876\">\n" +
                "        <name>name7</name>\n" +
                "        <currency>PLN</currency>\n" +
                "        <balance>1.00</balance>\n" +
                "        <closingDate>2010-01-01</closingDate>\n" +
                "    </account>\n" +
                "<!--rekord prawidłowy-->\n" +
                "    <account iban=\"PL61109010140000071219812874\">\n" +
                "        <name>name8</name>\n" +
                "        <currency>PLN</currency>\n" +
                "        <balance>0</balance>\n" +
                "        <closingDate>2039-05-15</closingDate>\n" +
                "    </account>\n" +
                "\n" +
                "</accounts>\n";
        Account account1 = new Account("PLL1109010140000071219812876", "name7", "PLN", 1.00, LocalDate.of(2010, 1, 1));
        Account account2 = new Account("PL61109010140000071219812874", "name8", "PLN", 0.00, LocalDate.of(2039, 5, 15));
        accounts.add(account1);
//        accounts.add(account2);
        FileUtils.writeStringToFile(file, content);
        //when
        List<Account> result = service.mapXmlToList(file);
        //then
        assertNotEquals(accounts, result);
    }

    @Test(expected = JsonParseException.class)
    public void testShouldThrowJsonParseExceptionWhileFileContentIsNull() throws IOException {
        //given
        String content = null;
        FileUtils.writeStringToFile(file, content);
        //when
        List<Account> result = service.mapXmlToList(file);
        //then
        assertNotEquals(accounts, result);
    }

    @Test
    public void mapListToXml() throws IOException {
        //given
        Account account1 = new Account("PLL1109010140000071219812876", "name7", "PLN", 1.00, LocalDate.of(2010, 1, 1));
        Account account2 = new Account("PL61109010140000071219812874", "name8", "PLN", 0.00, LocalDate.of(2039, 5, 15));
        accounts.add(account1);
        accounts.add(account2);
        AccountContainer container = new AccountContainer(accounts);
        //when
        service.mapListToXml(outputFile, container);
        //then
        assertNotNull(outputFile);
    }

    @After
    public void tearDown() throws Exception {
        file.delete();
        outputFile.delete();
        accounts.clear();
    }
}
