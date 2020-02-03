package pl.com.eLeader.accountXmlProcessor.services;

import pl.com.eLeader.accountXmlProcessor.model.Account;

import java.util.List;

public interface DataService {

    DataService filterPLN();

    DataService filterPositiveBallance();

    DataService filterUnexpiredAccounts();

    DataService filterValidIBAN();

    List<Account> buildOrderedList();

}
