package org.galaxia.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GalaxiaControllerTest {

    private GalaxiaController galaxiaController;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ByteArrayInputStream inContent;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    private void setUpController(String input) {
        inContent = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inContent);
        galaxiaController = new GalaxiaController(scanner);
    }

    @Test
    void testShowMenuAndHelp() {
        setUpController("2\n3\n");
        galaxiaController.start();
        String output = outContent.toString();
        assertTrue(output.contains("MENÚ DE OPCIONES"));
        assertTrue(output.contains("Manual de usuario"));
    }

    @Test
    void testValidComand() {
        setUpController("glob es\n");
        galaxiaController.handleNoteInput();
        assertTrue(outContent.toString().contains("Nota incorrecta..."));
    }

    @Test
    void testErrorRegisterGalacticUnit() {
        setUpController("glob es Z\n");
        galaxiaController.handleNoteInput();
        assertTrue(outContent.toString().contains("Número romano no existe..."));
    }

    @Test
    void testRegisterGalacticUnit() {
        setUpController("glob es I\n");
        galaxiaController.handleNoteInput();
        assertTrue(outContent.toString().contains("Unidad registrada..."));
    }

    @Test
    void testRegisterCredit() {
        setUpController("glob es I\n");
        galaxiaController.handleNoteInput();
        outContent.reset();

        setUpController("glob glob Silver es 34 Créditos\n");
        galaxiaController.handleNoteInput();
        assertTrue(outContent.toString().contains("Nota registrada..."));
    }

    @Test
    void testCalculateRomanSum() {
        setUpController("glob es I\n");
        galaxiaController.handleNoteInput();
        setUpController("prok es V\n");
        galaxiaController.handleNoteInput();
        setUpController("pish es X\n");
        galaxiaController.handleNoteInput();
        setUpController("tegj es L\n");
        galaxiaController.handleNoteInput();
        outContent.reset();

        setUpController("¿Cuánto es pish tegj glob glob?\n");
        galaxiaController.handleNoteInput();
        assertTrue(outContent.toString().contains("pish tegj glob glob es 42"));
    }

    @Test
    void testCalculateCredits() {
        setUpController("glob es I\n");
        galaxiaController.handleNoteInput();
        setUpController("prok es V\n");
        galaxiaController.handleNoteInput();
        setUpController("glob glob Silver es 34 Créditos\n");
        galaxiaController.handleNoteInput();
        outContent.reset();

        setUpController("¿Cuántos créditos tiene glob prok Silver?\n");
        galaxiaController.handleNoteInput();
        assertTrue(outContent.toString().contains("glob prok Silver es 68 Créditos"));
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalOut);
    }

    @Test
    void testValidWrongComand() {
        setUpController("¿Cuánta madera podría roer una marmota si una marmota pudiera roer madera?\n");
        galaxiaController.handleNoteInput();
        assertTrue(outContent.toString().contains("No tengo idea de qué estás hablando"));
    }
}
