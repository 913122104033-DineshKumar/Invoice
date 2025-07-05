package invoice.utils;

import java.util.List;
import java.util.Scanner;

public class MenuContext<T> {

    private List<T> list;
    private Scanner scanner;

    static class MenuOption {
        int optionNumber;
        String description;
        Runnable action;

        public MenuOption (int optionNumber, String description, Runnable action) {
            this.optionNumber = optionNumber;
            this.description = description;
            this.action = action;
        }

    }

    

}
