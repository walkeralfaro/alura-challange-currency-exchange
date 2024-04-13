import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    public static int inputNumber(String inputMessage, String errorMessage,int option) {
        Scanner keyboard = new Scanner(System.in);
        int valueInput;
        while (true) {
            System.out.println(Colors.BLUE + inputMessage + Colors.RESET);
            try {
                valueInput = keyboard.nextInt();
                if (valueInput > option && option != 0) {
                    throw new InputMismatchException();
                }
                keyboard.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println(Colors.RED + errorMessage + Colors.RESET);
                keyboard.nextLine();
            }
        }
        return valueInput;
    }
}
