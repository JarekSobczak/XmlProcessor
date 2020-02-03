package pl.com.eLeader.accountXmlProcessor;

import pl.com.eLeader.accountXmlProcessor.model.Account;
import pl.com.eLeader.accountXmlProcessor.model.AccountContainer;
import pl.com.eLeader.accountXmlProcessor.services.implementations.DataServiceImplementation;
import pl.com.eLeader.accountXmlProcessor.services.implementations.XmlServiceImplementation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountXmlProcessor {

    public static void main(String[] args) {

        XmlServiceImplementation xmlService = new XmlServiceImplementation();
        String filePath = xmlService.getFilePath();
        File inputFile = new File(filePath);
        File outputFile=new File("output.xml");

        List<Account> accounts = new ArrayList<>();

        try {
            accounts = xmlService.mapXmlToList(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Account> result = new DataServiceImplementation(accounts)
                .filterPLN()
                .filterPositiveBallance()
                .filterUnexpiredAccounts()
                .filterValidIBAN()
                .buildOrderedList();

        AccountContainer container = new AccountContainer(result);
        try {
            xmlService.mapListToXml(outputFile,container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
