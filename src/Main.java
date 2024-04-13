import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String uriCurrencies = "https://openexchangerates.org/api/currencies.json";
        String currenciesName = Connection.FetchHTTP(uriCurrencies);
        JsonObject currenciesNameJson = gson.fromJson(currenciesName, JsonObject.class);

        String[] currenciesCode = {"PEN", "ARS", "USD", "EUR", "BRL", "MXN", "VES"};
        int currenciesLength = currenciesCode.length;

        String operating = "y";
        while (operating.equals("y")) {

            UI.menu();
            for (int i = 0; i < currenciesLength; i++) {
                try {
                    String currencyName = currenciesNameJson.get(currenciesCode[i]).getAsString();
                    System.out.println((i + 1) + ". " + currencyName);
                } catch (NullPointerException e) {
                    System.out.println("Moneda no encontrada");
                }
            }

            String inputFirstCurrencyMessage = "Ingrese la opción de la moneda que quiere CAMBIAR: ";
            String inputSecondCurrencyMessage = "Ingrese la opción de la moneda DESTINO: ";
            String inputAmountMessage = "Ingrese el MONTO que quiere cambiar: ";
            String errorMessage = "Opción invalida";
            int inputBase = Input.inputNumber(inputFirstCurrencyMessage, errorMessage, currenciesLength);
            int inputTarget = Input.inputNumber(inputSecondCurrencyMessage, errorMessage, currenciesLength);
            int inputAmount = Input.inputNumber(inputAmountMessage, errorMessage, 0);

            String baseCurrency = currenciesCode[inputBase - 1];
            String targetCurrency = currenciesCode[inputTarget - 1];
            String uriExchange = "https://v6.exchangerate-api.com/v6/7a90f6fbf47eb99b1a8d78e8/pair/"
                    + baseCurrency + "/" + targetCurrency + "/" + inputAmount;

            String currencyExchange = Connection.FetchHTTP(uriExchange);
            CurrencyExchangeAPI currencyExchangeAPI = gson.fromJson(currencyExchange, CurrencyExchangeAPI.class);
            Currency currency = new Currency(currencyExchangeAPI);
            String currencyName = currenciesNameJson.get(targetCurrency).getAsString();

            System.out.println(Colors.GREEN + "La conversión es: " + currency.getConversion() + " "
                    + currencyName + Colors.RESET);

            System.out.println("Desea continuar: [y/n]");
            operating = keyboard.nextLine();
        }
    }
}
