package pl.com.eLeader.accountXmlProcessor.services.implementations;

import lombok.Data;
import pl.com.eLeader.accountXmlProcessor.model.Account;
import pl.com.eLeader.accountXmlProcessor.services.DataService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataServiceImplementation implements DataService {

    private List<Account> accounts;

    public DataServiceImplementation(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public DataServiceImplementation filterPLN() {
        this.accounts = accounts.stream()
                .filter(account -> Optional.ofNullable(account.getCurrency()).orElse("NULL").equalsIgnoreCase("PLN"))
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public DataServiceImplementation filterPositiveBallance() {
        this.accounts = accounts.stream()
                .filter(account -> Optional.ofNullable(account.getBalance()).orElse(-1.0) >= 0)
                .collect(Collectors.toList());
        return this;

    }

    @Override
    public DataServiceImplementation filterUnexpiredAccounts() {
        this.accounts = accounts.stream()
                .filter(account -> Optional.ofNullable(account.getClosingDate()).orElse(LocalDate.now().minusMonths(1)).isAfter(LocalDate.now().minusDays(1)))
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public DataServiceImplementation filterValidIBAN() {
        this.accounts = accounts.stream()
                .filter(account -> isCorrect(account.getIban()))
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public List<Account> buildOrderedList() {
        return accounts.stream().sorted(Comparator.comparing(account -> Optional.ofNullable(account.getName()).orElse("ŻŻ").toLowerCase())).collect(Collectors.toList());
    }

    private boolean isCorrect(String iban) {

        if (!isDigitFormCorrect(iban)) {
            return false;
        }
        String first4Chars = iban.substring(0, 4);
        String number = (iban.substring(4, iban.length()) + first4Chars).replace("PL", "2521");

        BigDecimal parsedNumber;
        if (isDigit(number)) {
            parsedNumber = new BigDecimal(number);
        } else {
            return false;
        }
        BigDecimal remainder = parsedNumber.remainder(BigDecimal.valueOf(97));
        if (!isControllNumberCorrect(remainder)) {
            return false;
        }
        return true;
    }

    private boolean isControllNumberCorrect(BigDecimal remainder) {
        return remainder.equals(BigDecimal.valueOf(1));
    }

    private boolean isDigitFormCorrect(String iban) {
        if (iban == null) {
            return false;
        }
        return iban.matches("[A-Z]{2}\\d{26}");
    }

    private boolean isDigit(String digit) {
        return digit.chars().allMatch(Character::isDigit);
    }
}
