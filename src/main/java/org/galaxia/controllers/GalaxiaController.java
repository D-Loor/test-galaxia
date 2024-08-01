package org.galaxia.controllers;

import org.galaxia.records.Credit;
import org.galaxia.records.GalacticUnit;
import org.galaxia.utils.enums.RomanNumeralEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.logging.Logger;

public class GalaxiaController {
    private final Scanner scanner;
    private static final Map<String, GalacticUnit> galacticUnits = new HashMap<>();
    private static final Map<String, Credit> credits = new HashMap<>();
    private static final Logger logger = Logger.getLogger(GalaxiaController.class.getName());

    public GalaxiaController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("MENÚ DE OPCIONES: \n\n 1)Agregar nota \n 2)Ayuda \n 3)Salir");
            System.out.println("\n Opción:");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> handleNoteInput();
                case "2" -> displayHelp();
                case "3" -> {
                    return;
                }
                default -> System.out.println("Opción inválida.\n");
            }
        }
    }

    public void handleNoteInput() {
        System.out.println("Escribir nota:");
        String note = scanner.nextLine();
        processInput(note);
    }

    private static void displayHelp() {
        String manual = """
                \n\n\t\t -----------------Manual de usuario:------------------\n
                1.- Para registrar un alias a un número romano debe ingresar:
                    Nombre-Unidad 'es' Número-Romano  Ejemplo: glob es I
                2.- Para agregar una nueva unidad intergaláctica debe ingresar:
                    Alias-Unidad 'es' Cantidad 'Créditos'  Ejemplo: glob glob Silver es 34 Créditos
                3.- Para calcular una suma de números romanos debe ingresar:
                    '¿Cuánto es' Alias1 Alias2 ...'?'  Ejemplo: ¿Cuánto es pish tegj glob glob?
                4.- Para consultar el crédito de una unidad intergaláctica por alias debe ingresar:
                    '¿Cuántos créditos tiene' Alias1 Alias2 ... Unidad-Intergaláctica '?'
                    Ejemplo: ¿Cuántos créditos tiene glob prok Silver?
                \n\nNota: Se debe tener en cuenta la sintaxis establecida, en caso de cometer un error al escribir una nota se presentará el siguiente mensaje: Nota incorrecta
                \n\t\t -----------------Entrada de Prueba:------------------\n
                glob es I
                prok es V
                pish es X
                tegj es L
                glob glob Silver es 34 Créditos
                glob prok Gold es 57800 Créditos
                pish pish Iron es 3910 Créditos
                ¿Cuánto es pish tegj glob glob?
                ¿Cuántos créditos tiene glob prok Silver?
                ¿Cuántos créditos tiene glob prok Gold?
                ¿Cuántos créditos tiene glob prok Iron?
                \n\t\t -----------------Salida de Prueba:------------------\n
                pish tegj glob glob es 42
                glob prok Silver es 68 créditos
                glob prok Gold es 57800 créditos
                glob prok Iron es 782 créditos
                No tengo idea de lo que estás hablando
                """;
        System.out.println(manual);
    }

    private static void processInput(String note) {
        String[] words = note.split("\\s+");

        if (words.length < 3) {
            System.out.println("Nota incorrecta...\n");
        } else if (words.length == 3 && !note.contains("¿Cuánto es")) {
            if (note.contains("es")) {
                registerGalacticUnit(words);
            } else {
                System.out.println("Nota incorrecta...\n");
            }
        } else if (note.contains("¿Cuánto es")) {
            calculateRomanSum(note);
        } else if (words[words.length - 1].equals("Créditos") &&
                words[words.length - 3].equals("es")) {
            registerCredit(note);
        } else if (note.contains("¿Cuántos créditos tiene")) {
            calculateCredits(note);
        } else if (!note.contains("créditos") && !note.contains("Créditos")) {
            System.out.println("No tengo idea de qué estás hablando\n");
        } else {
            System.out.println("Nota incorrecta...\n");
        }
    }

    private static void registerGalacticUnit(String[] words) {
        try {
            String roman = words[2];

            if (!galacticUnits.containsKey(words[0])) {
                RomanNumeralEnum romanNumeral = RomanNumeralEnum.fromString(roman);

                if (romanNumeral != null) {
                    galacticUnits.put(words[0], new GalacticUnit(words[0], romanNumeral));
                    System.out.println("Unidad registrada...\n");
                } else {
                    System.out.println("Número romano no existe...\n");
                }
            } else {
                System.out.println("Unidad galáctica ya se encuentra registrada...\n");
            }

        } catch (Exception e) {
            logger.severe("registerGalacticUnit " + e);
        }

    }

    private static void calculateRomanSum(String note) {
        try {
            note = note.replaceAll("¿Cuánto es ", "").replaceFirst("\\?$", "");
            String romanString = convertToRoman(note);

            if (!romanString.contains("Error")) {
                if (isValidRoman(romanString)) {
                    System.out.println(note + " es " + sumRoman(romanString));
                } else {
                    System.out.println("Nota incorrecta...\n");
                }
            } else {
                System.out.println("Al parecer uno de los alias que has ingresado no se encuentra registrado...\n");
            }
        } catch (Exception e) {
            logger.severe("calculateRomanSum " + e);
        }
    }

    private static void registerCredit(String note) {
        try {
            String[] words = note.split("\\s+");
            String galacticUnitsString = Arrays.stream(words, 0, words.length - 4).collect(Collectors.joining(" "));
            String romanString = convertToRoman(galacticUnitsString);

            if (!romanString.contains("Error")) {
                if (isValidRoman(romanString)) {
                    float creditValue = Float.parseFloat(words[words.length - 2]);
                    float unitCredit = calculateCreditValue(romanString, creditValue);
                    credits.put(words[words.length - 4], new Credit(words[words.length - 5], unitCredit));
                    System.out.println("Nota registrada...\n");
                } else {
                    System.out.println("Nota incorrecta...\n");
                }
            } else {
                System.out.println("Al parecer uno de los alias que has ingresado no se encuentra registrado...\n");
            }
        } catch (Exception e) {
            logger.severe("registerCredit " + e);
        }
    }

    private static void calculateCredits(String note) {
        try {
            note = note.replaceAll("¿Cuántos créditos tiene ", "").replaceFirst("\\?$", "");
            String[] words = note.split("\\s+");
            String galacticUnitsString = Arrays.stream(words, 0, words.length - 1).collect(Collectors.joining(" "));
            String romanString = convertToRoman(galacticUnitsString);

            if (!romanString.contains("Error")) {
                if (isValidRoman(romanString)) {
                    Credit credit = credits.get(words[words.length - 1]);
                    if (credit != null) {
                        float totalCredits = calculateTotalCredits(romanString, credit.value());
                        System.out.println(note + " es " + (int) totalCredits + " Créditos");
                    } else {
                        System.out.println("Nota incorrecta...\n");
                    }
                } else {
                    System.out.println("Nota incorrecta...\n");
                }
            } else {
                System.out.println("Al parecer uno de los alias o unidad intergaláctica que has ingresado no se encuentra registrado...\n");
            }
        } catch (Exception e) {
            logger.severe("calculateCredits " + e);
        }
    }

    private static String convertToRoman(String galacticUnitsString) {
        return Arrays.stream(galacticUnitsString.split("\\s+"))
                .map(galacticUnits::get)
                .map(gu -> gu != null ? gu.romanNumeral().name() : "Error")
                .collect(Collectors.joining());
    }

    private static boolean isValidRoman(String romanString) {
        int[] repeatCount = {0};
        return romanString.chars()
                .mapToObj(c -> RomanNumeralEnum.valueOf(String.valueOf((char) c)))
                .reduce((prev, curr) -> {
                    if (prev.getValue() < curr.getValue() && !isValidSubtraction(prev, curr)) {
                        return null;
                    } else if (prev == curr) {
                        repeatCount[0]++;
                        if (repeatCount[0] >= 3 && curr != RomanNumeralEnum.M) return null;
                    } else {
                        repeatCount[0] = 0;
                    }
                    return curr;
                }).isPresent();
    }

    private static boolean isValidSubtraction(RomanNumeralEnum previous, RomanNumeralEnum current) {
        return (previous == RomanNumeralEnum.I && (current == RomanNumeralEnum.V || current == RomanNumeralEnum.X)) ||
                (previous == RomanNumeralEnum.X && (current == RomanNumeralEnum.L || current == RomanNumeralEnum.C)) ||
                (previous == RomanNumeralEnum.C && (current == RomanNumeralEnum.D || current == RomanNumeralEnum.M));
    }

    private static int sumRoman(String romanString) {
        return romanString.chars()
                .mapToObj(c -> RomanNumeralEnum.valueOf(String.valueOf((char) c)))
                .reduce(0, (sum, numeral) -> {
                    int value = numeral.getValue();
                    return sum + value - 2 * (sum % value);
                }, Integer::sum);
    }

    private static float calculateCreditValue(String romanString, float totalCredits) {
        return totalCredits / sumRoman(romanString);
    }

    private static float calculateTotalCredits(String romanString, float unitCreditValue) {
        return unitCreditValue * sumRoman(romanString);
    }
}
