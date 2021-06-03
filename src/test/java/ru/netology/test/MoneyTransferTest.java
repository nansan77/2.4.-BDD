package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getBalanceOfFirstCardAfterTransfer;
import static ru.netology.data.DataHelper.getBalanceOfSecondCardAfterTransfer;

public class MoneyTransferTest {
    @BeforeEach
    void setUp() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    void shouldNoTransferMoreThanBalanceOfCard() {
        val dashboardPage = new DashboardPage();
        val amount = 120000;
        val transferPage = dashboardPage.pushFirstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.transferCard(cardInfo, amount);
        transferPage.getError();
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        val dashboardPage = new DashboardPage();
        val amount = 10000;
        val balanceOfFirstCardBefore = dashboardPage.getCardBalanceFirstCard();
        val balanceOfSecondCardBefore = dashboardPage.getCardBalanceSecondCard();
        val transferPage = dashboardPage.pushSecondCard();
        val cardInfo = DataHelper.getFirstCardInfo();
        transferPage.transferCard(cardInfo, amount);
        val balanceAfterTransactionFirstCard = getBalanceOfFirstCardAfterTransfer(balanceOfFirstCardBefore, amount);
        val balanceAfterTransactionSecondCard = getBalanceOfSecondCardAfterTransfer(balanceOfSecondCardBefore, amount);
        val balanceOfFirstCardAfter = dashboardPage.getCardBalanceFirstCard();
        val balanceOfSecondCardAfter = dashboardPage.getCardBalanceSecondCard();
        assertEquals(balanceAfterTransactionFirstCard, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransactionSecondCard, balanceOfSecondCardAfter);
    }
}
