package pl.com.eLeader.accountXmlProcessor.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "accounts")
public class AccountContainer {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "account")
    private List<Account> accounts;

    public AccountContainer(List<Account> accounts) {
        this.accounts = accounts;
    }
}
