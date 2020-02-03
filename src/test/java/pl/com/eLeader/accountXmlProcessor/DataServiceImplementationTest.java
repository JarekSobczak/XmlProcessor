package pl.com.eLeader.accountXmlProcessor;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import pl.com.eLeader.accountXmlProcessor.model.Account;
import pl.com.eLeader.accountXmlProcessor.services.implementations.DataServiceImplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DataServiceImplementationTest {

    private List<Account> accounts;
    DataServiceImplementation service;

    @Before
    public void setUp(){
        accounts = new ArrayList<>();
        service = new DataServiceImplementation(accounts);
    }

    @Test
    public void testShouldReturnOrderedList() {
        //given
        Account account1 = new Account(null, "Asome", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account(null, "Bsome", "PLN", null, LocalDate.now());
        Account account3 = new Account(null, "Csome", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        //when
        List<Account> result = service.buildOrderedList();
        //then
        assertEquals(account1, result.get(0));
        assertEquals(account2, result.get(1));
        assertEquals(account3, result.get(2));
    }

    @Test
    public void testShouldReturnOrderedListButOrderIsReversed() {
        //given
        Account account1 = new Account(null, "csome", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account(null, "Bsome", "PLN", null, LocalDate.now());
        Account account3 = new Account(null, "asome", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        //when
        List<Account> result = service.buildOrderedList();
        //then
        assertEquals(account1, result.get(2));
        assertEquals(account2, result.get(1));
        assertEquals(account3, result.get(0));
    }

    @Test
    public void testShouldReturnOrderedListButOneIsNull() {
        //given
        Account account1 = new Account(null, "Asome", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account(null, null, "PLN", null, LocalDate.now());
        Account account3 = new Account(null, "Csome", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        //when
        List<Account> result = service.buildOrderedList();
        //then
        assertEquals(account1, result.get(0));
        assertEquals(account2, result.get(2));
        assertEquals(account3, result.get(1));
    }

    @Test
    public void testShouldFilterAccountsWithPLNCurrency() {
        //given
        Account account1 = new Account("some iban", "some", "ECU", 100.0, null);
        Account account2 = new Account("some iban", "some", "PLN", 100.0, null);
        Account account3 = new Account("some iban", "some", "pln", 100.0, null);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account2, account3);
        //when
        List<Account> result = service.filterPLN().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterAccountsWithPLNCurrencyButExpectedIsWrong() {
        //given
        Account account1 = new Account("some iban", "some", "ECU", 100.0, null);
        Account account2 = new Account("some iban", "some", "PLN", 100.0, null);
        Account account3 = new Account("some iban", "some", "pln", 100.0, null);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account1, account2, account3);
        //when
        List<Account> result = service.filterPLN().buildOrderedList();
        //then
        assertNotEquals(expected, result);
    }

    @Test
    public void testShouldFilterAccountsWithPLNCurrencyButOneContainsNull() {
        //given
        Account account1 = new Account("some iban", "some", null, 100.0, null);
        Account account2 = new Account("some iban", "some", "PLN", 100.0, null);
        Account account3 = new Account("some iban", "some", "pln", 100.0, null);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account2, account3);
        //when
        List<Account> result = service.filterPLN().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterAccountWithPositiveBallance() {
        //given
        Account account1 = new Account("some iban", "some", null, 100.0, null);
        Account account2 = new Account("some iban", "some", "PLN", -100.0, null);
        Account account3 = new Account("some iban", "some", "pln", 100.0, null);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account1, account3);
        //when
        List<Account> result = service.filterPositiveBallance().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterAccountWithPositiveBallanceButExpectedIsWrong() {
        //given
        Account account1 = new Account("some iban", "some", null, 100.0, null);
        Account account2 = new Account("some iban", "some", "PLN", -100.0, null);
        Account account3 = new Account("some iban", "some", "pln", 100.0, null);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account1, account3, account2);
        //when
        List<Account> result = service.filterPositiveBallance().buildOrderedList();
        //then
        assertNotEquals(expected, result);
    }

    @Test
    public void testShouldFilterAccountWithPositiveBallanceButOneContainsNull() {
        //given
        Account account1 = new Account("some iban", "some", null, 100.0, null);
        Account account2 = new Account("some iban", "some", "PLN", null, null);
        Account account3 = new Account("some iban", "some", "pln", 100.0, null);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account1, account3);
        //when
        List<Account> result = service.filterPositiveBallance().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterUnexpiredAccounts() {
        //given
        Account account1 = new Account("some iban", "some", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account("some iban", "some", "PLN", null, LocalDate.now());
        Account account3 = new Account("some iban", "some", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account1, account2);
        //when
        List<Account> result = service.filterUnexpiredAccounts().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterUnexpiredAccountsButExpectedIsWrong() {
        //given
        Account account1 = new Account("some iban", "some", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account("some iban", "some", "PLN", null, LocalDate.now());
        Account account3 = new Account("some iban", "some", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account1, account2, account3);
        //when
        List<Account> result = service.filterUnexpiredAccounts().buildOrderedList();
        //then
        assertNotEquals(expected, result);
    }

    @Test
    public void testShouldFilterUnexpiredAccountsButOneIsNull() {
        //given
        Account account1 = new Account("some iban", "some", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account("some iban", "some", "PLN", null, LocalDate.now());
        Account account3 = new Account("some iban", "some", "pln", 100.0, null);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account1, account2);
        //when
        List<Account> result = service.filterUnexpiredAccounts().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterValidIBAN() {
        //given
        Account account1 = new Account("PL61109010140000071219812872", "some", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account("PL61109010140000071219812873", "some", "PLN", null, LocalDate.now());
        Account account3 = new Account("PL61109010140000071219812874"/*valid*/, "some", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account3);
        //when
        List<Account> result = service.filterValidIBAN().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterValidIBANButExpectedIsWrong() {
        //given
        Account account1 = new Account("PL61109010140000071219812872", "some", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account("PL61109010140000071219812873", "some", "PLN", null, LocalDate.now());
        Account account3 = new Account("PL61109010140000071219812874"/*valid*/, "some", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account3, account2);
        //when
        List<Account> result = service.filterValidIBAN().buildOrderedList();
        //then
        assertNotEquals(expected, result);
    }

    @Test
    public void testShouldFilterValidIBANButOneIsNull() {
        //given
        Account account1 = new Account(null, "some", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account("PL61109010140000071219812873", "some", "PLN", null, LocalDate.now());
        Account account3 = new Account("PL61109010140000071219812874"/*valid*/, "some", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account3);
        //when
        List<Account> result = service.filterValidIBAN().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterValidIBANButOneHasNonPolishCountryCode() {
        //given
        Account account1 = new Account(null, "some", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account("DE61109010140000071219812873", "some", "PLN", null, LocalDate.now());
        Account account3 = new Account("PL61109010140000071219812874"/*valid*/, "some", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account3);
        //when
        List<Account> result = service.filterValidIBAN().buildOrderedList();
        //then
        assertEquals(expected, result);
    }

    @Test
    public void testShouldFilterValidIBANButOneHasWrongNumberLenght() {
        //given
        Account account1 = new Account(null, "some", null, 100.0, LocalDate.now().plusDays(1));
        Account account2 = new Account("PLL09010140000071219812", "some", "PLN", null, LocalDate.now());
        Account account3 = new Account("PL61109010140000071219812874"/*valid*/, "some", "pln", 100.0, LocalDate.now().minusDays(1));
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        List<Account> expected = Lists.newArrayList(account3);
        //when
        List<Account> result = service.filterValidIBAN().buildOrderedList();
        //then
        assertEquals(expected, result);
    }
}
