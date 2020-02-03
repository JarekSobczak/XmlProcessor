package pl.com.eLeader.accountXmlProcessor.services;

import pl.com.eLeader.accountXmlProcessor.model.Account;
import pl.com.eLeader.accountXmlProcessor.model.AccountContainer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface XmlService {

    String getFilePath();

    List<Account> mapXmlToList(File file) throws IOException;

    void mapListToXml(File file,AccountContainer container) throws IOException;
}
