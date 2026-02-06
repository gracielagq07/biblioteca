package com.biblioteca.view;

import java.util.Scanner;
import com.biblioteca.utils.*;

public class MainMenuView {

    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        int option = -1;
        do {
            System.out.println(
                    colors.MAGENTA + "\n========================================================" + colors.RESET);
            System.out
                    .println(colors.MAGENTA + "     BIBLIOTECA GABRIEL GARCÍA MÁRQUEZ - MENÚ PRINCIPAL" + colors.RESET);
            System.out.println(
                    colors.MAGENTA + "========================================================" + colors.RESET);
            System.out.println(colors.GREEN + "1." + colors.RESET + colors.CYAN + " Gestión de libros" + colors.RESET);
            System.out.println(colors.GREEN + "2." + colors.RESET + colors.CYAN + " Gestión de autores" + colors.RESET);
            System.out.println(
                    colors.GREEN + "3." + colors.RESET + colors.CYAN + " Gestión de editoriales" + colors.RESET);
            System.out.println(colors.GREEN + "4." + colors.RESET + colors.CYAN + " Gestión de géneros" + colors.RESET);
            System.out.println(colors.GREEN + "0." + colors.RESET + colors.CYAN + " Salir" + colors.RESET);
            System.out.print(colors.BG_GREEN + "Seleccione una opción:" + colors.RESET + " ");

            String input = scanner.nextLine();
            try {
                option = Integer.parseInt(input);
                if (option < 0 || option > 4) {
                    System.out.println(colors.BG_RED + "Opción incorrecta, introduce de nuevo." + colors.RESET);
                    option = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println(colors.BG_RED + "Opción incorrecta, introduce de nuevo." + colors.RESET);
            }

        } while (option == -1);

        return option;
    }
}
