package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static final String[] OPCJE = {"add", "remove", "list", "exit"};
    //metoda zbierająca opcje
    static final String CSV_FILE = "tasks.csv";
    //metoda pozwalająca wczytać tasks.csv
    static String[][] tasks;
    //metoda z taskami
    static Scanner scanner = new Scanner(System.in);
    //globalny scanner

    public static void main(String[] args) {//metoda główna
        opcje(OPCJE);
        tasks = wczytaj(CSV_FILE);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks, numer(tasks));
                    break;
                case "list":
                    wyswietlTab(tasks);
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Proszę wybrać poprawną opcję.");
            }
            opcje(OPCJE);
        }
    }

    public static void opcje(String[] opcje) {//metoda pojawiająca prośbę na niebiesko i opcje w neutralnym kolorze
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (String opcja : OPCJE) {
            System.out.println(opcja);
        }
    }

    public static String[][] wczytaj(String CSV_FILE) {//metoda wczytująca plik csv i czytająca wszystkie wiersze pliku
        Path path = Paths.get(CSV_FILE);
        if (!Files.exists(path)) {
            System.out.println("Plik nie istnieje");
            System.exit(0);
        }
        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(path);
            tab = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                System.arraycopy(split, 0, tab[i], 0, split.length);
            }
        } catch (IOException e) {
            System.out.println("Plik jest nie poprawny");
        }
        return tab;
    }

    public static void wyswietlTab(String[][] tab) {//metoda wyświetlająca tablicę dwuwymiarową
        for (int i = 0; i < tab.length; i++) {
            System.out.println(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.println(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void addTask() {//metoda dodająca zadanie do listy
        Scanner scanner = new Scanner(System.in);
        System.out.println("Proszę dodać opis zadania :)");
        String opis = scanner.nextLine();
        System.out.println("Proszę dodać datę zadania :)");
        String data = scanner.nextLine();
        System.out.println("Proszę podać czy zadanie jest ważne. Wpisz true jeśli jest ważne lub false jeśli nie");
        String waznosc = scanner.nextLine();
        System.out.println("Zadanie zostało dodane pomyślnie :D");

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = opis;
        tasks[tasks.length - 1][1] = data;
        tasks[tasks.length - 1][2] = waznosc;
    }

    public static boolean czyJednoZZadan(String input, String[][] tab) {//metoda sprawdzająca czy zadanie ma numer, który występuje, a wiedząc, że liczymy od 0 to sprawdzamy czy jest dodatnia
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0 && Integer.parseInt(input)<tab.length;
        }
        return false;
    }

    public static int numer(String[][] tab) {//metoda usuwająca zadanie z listy
        Scanner scanner = new Scanner(System.in);
        System.out.println("Proszę podać numer zadania, który chcesz usunąć");
        String num = scanner.nextLine();
        while (!czyJednoZZadan(num, tab)) {//pętla sprawdzająca czy to jest liczba z listy
            System.out.println("Podałeś niepoprawne dane. Proszę podać CYFRE większą od zera.");
            scanner.nextLine();
            num = scanner.nextLine();
        }
        return Integer.parseInt(num);
    }
    public static void removeTask(String[][] tab, int num){
        try{
            if(num < tab.length) {
                tasks = ArrayUtils.remove(tab, num);
                System.out.println("Zadanie zostało usunięte poprawnie :D");
            }
            } catch (ArrayIndexOutOfBoundsException ex){
                System.out.println("Element nie istnieje");
            }

    }
}