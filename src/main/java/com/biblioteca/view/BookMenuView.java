package com.biblioteca.view;

import java.util.Scanner;

import com.biblioteca.utils.colors;

public class BookMenuView {

    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        int option = -1;
        do {
            System.out.println(colors.MAGENTA + "\n==============================" + colors.RESET);
            System.out.println(colors.MAGENTA + "     GESTIÓN DE LIBROS" + colors.RESET);
            System.out.println(colors.MAGENTA + "==============================" + colors.RESET);
            System.out.println(
                    colors.GREEN + "1." + colors.RESET + colors.CYAN + " Listar todos los libros" + colors.RESET);
            System.out.println(colors.GREEN + "2." + colors.RESET + colors.CYAN + " Añadir nuevo libro" + colors.RESET);
            System.out.println(colors.GREEN + "3." + colors.RESET + colors.CYAN + " Editar libro" + colors.RESET);
            System.out.println(colors.GREEN + "4." + colors.RESET + colors.CYAN + " Eliminar libro" + colors.RESET);
            System.out.println(
                    colors.GREEN + "5." + colors.RESET + colors.CYAN + " Buscar libro por título" + colors.RESET);
            System.out.println(
                    colors.GREEN + "6." + colors.RESET + colors.CYAN + " Buscar libros por autor" + colors.RESET);
            System.out.println(
                    colors.GREEN + "7." + colors.RESET + colors.CYAN + " Buscar libros por género" + colors.RESET);
            System.out.println(colors.GREEN + "0." + colors.RESET + colors.CYAN + " Menú principal" + colors.RESET);
            System.out.print(colors.BG_GREEN + "Seleccione una opción:" + colors.RESET + " ");

            String input = scanner.nextLine();
            try {
                option = Integer.parseInt(input);
                if (option < 0 || option > 7) {
                    System.out.println(colors.BG_YELLOW + "Opción incorrecta, introduce de nuevo." + colors.RESET);
                    option = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println(colors.BG_RED + "Opción incorrecta, introduce de nuevo." + colors.RESET);
            }

        } while (option == -1);

        return option;
    }
}
