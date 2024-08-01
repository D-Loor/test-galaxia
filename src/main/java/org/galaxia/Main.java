package org.galaxia;

import org.galaxia.controllers.GalaxiaController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GalaxiaController galaxiaController = new GalaxiaController(new Scanner(System.in));
        galaxiaController.start();
    }
}