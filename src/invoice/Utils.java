package invoice;

public class Utils {
    public enum CustomerTypes {
        BUSINESS,
        INDIVIDUAL
    }
    public enum ItemTypes {
        GOODS,
        SERVICES
    }

    public enum Status {
        DRAFT,
        SENT,
        PARTIALLY_PAID,
        CLOSED
    }

    public enum PaymentModes {
        CASH,
        BANK_TRANSFER,
        CREDIT_CARD
    }

    public enum Accounts {
        BANK,
        PETTY_CASH,
        UNDEPOSITED_FUNDS
    }
}
