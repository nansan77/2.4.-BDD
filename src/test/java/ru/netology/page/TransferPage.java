package ru.netology.page;

public class TransferPage {
    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement fromField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");


    public void transferCard(DataHelper.CardInfo CardInfo, int amount) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(CardInfo.getCardNumber());
        transferButton.click();
        new DashboardPage();
    }

    public void getError() {
        $(byText("Недостаточно средств для перевода!")).shouldBe(visible);
    }
}
