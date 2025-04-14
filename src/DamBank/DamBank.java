package DamBank;

import java.lang.classfile.instruction.SwitchCase;
import java.util.InputMismatchException;
import java.util.Scanner;

import static DamBank.CuentaBancaria.TOTAL_MOVIMIENTOS;

/**
 * Clase principal que representa la interfaz de usuario para operaciones bancarias básicas.
 * Permite crear una cuenta, realizar ingresos, retiros, consultar saldo y movimientos, entre otras operaciones.
 *
 * @author Angel Góemez
 * @version 1.0
 */
public class DamBank {
    /** Scanner global para lectura de entrada de usuario en toda la aplicación */
    public static Scanner scanner = new Scanner(System.in);
    public static final int OPCION_SALIR= 8;

    public static void main(String[] args) {

        CuentaBancaria cuenta = crearCuenta();
        ejecutarMenuPrincipal(cuenta);
        cerrarRecursos();
    }

    public static void ejecutarMenuPrincipal(CuentaBancaria cuenta) {
        int opcion;

        do {

            opcion = leerOpcion();
            procesarOpcion(opcion, cuenta);

        } while (opcion != OPCION_SALIR);
    }

    public static void procesarOpcion(int opcion, CuentaBancaria cuenta) {
        switch (opcion) {
            case 1 -> mostrarDatosCuenta(cuenta);
            case 2 -> System.out.println("IBAN: " + cuenta.getIban());
            case 3 -> System.out.println("Titular: " + cuenta.getTitularCuenta());
            case 4 -> System.out.printf("Saldo actual: %.2f€\n", cuenta.getSaldo());
            case 5 -> procesarIngreso(cuenta);
            case 6 -> procesarRetirada(cuenta);
            case 7 -> cuenta.mostrarMovimientos();
            case OPCION_SALIR -> System.out.println("Saliendo del sistema...");
            default -> System.out.println("❌ Opción no válida. Por favor seleccione 1-8");
        }
    }


    /**
     * Crea una nueva cuenta bancaria validando IBAN y titular mediante entrada de usuario.
     * @return CuentaBancaria válida creada
     * @throws IllegalArgumentException Si el IBAN no cumple el formato requerido
     */

    public static CuentaBancaria crearCuenta() {
        while (true) {
            System.out.print("Introduce el IBAN (2 letras + 22 números): ");
            String iban = scanner.nextLine().trim();
            System.out.println("Introduce el nombre del titular");
            String titular = scanner.nextLine().trim();

            try {
                return new CuentaBancaria(iban, titular);
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

     // Muestra el menú principal.
    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ DAMBANK ---");
        System.out.println("1. Mostrar datos de la cuenta");
        System.out.println("2. Mostrar IBAN");
        System.out.println("3. Mostrar titular");
        System.out.println("4. Consultar saldo");
        System.out.println("5. Ingresar dinero");
        System.out.println("6. Retirar dinero");
        System.out.println("7. Ver movimientos");
        System.out.println("8. Salir");
        System.out.print("Selecciona una opción: ");
    }

    /**
     * Lee y valida la opción seleccionada por el usuario.
     * @return Entero representando la opción seleccionada (-1 para entradas inválidas)
     */
    private static int leerOpcion() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            return -1;
        } finally {
            scanner.nextLine();
        }

    }

    /**
     * Muestra información detallada de la cuenta: IBAN, titular y saldo actual.
     * @param cuenta Cuenta bancaria de la cual mostrar los datos
     */
    private static void mostrarDatosCuenta(CuentaBancaria cuenta) {
        System.out.println("\n--- DATOS DE LA CUENTA ---");
        System.out.println("IBAN: " + cuenta.getIban());
        System.out.println("Titular: " + cuenta.getTitularCuenta());
        System.out.printf("Saldo: %.2f€\n", cuenta.getSaldo());
    }

    /**
     * Procesa una operación de ingreso en la cuenta con validación de entrada numérica.
     * @param cuenta Cuenta bancaria donde realizar el ingreso
     */
    public static void procesarIngreso(CuentaBancaria cuenta) {
        System.out.println("Introduce la cantidad a ingresar: ");
        try {
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer
            if (cuenta.ingresar(cantidad)) {
                System.out.println("¡Ingreso realizado con éxito!");
            } else {
                System.out.println("No se pudo realizar el ingreso.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes introducir un número.");
            scanner.nextLine(); // limpiar el buffer
        }
    }

    /**
     * Procesa una operación de retirada de fondos con validación de entrada numérica.
     * @param cuenta Cuenta bancaria de donde realizar la retirada
     */
    public static void procesarRetirada(CuentaBancaria cuenta) {
        System.out.println("Introduce la cantidad a retirar");
        try {
            double cantidad = scanner.nextDouble();
            if (!cuenta.retirar(cantidad)) {
                System.out.println("No se pudo realizar la retirada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes introducir un número");
            scanner.nextLine(); //Limpiar buffer
        }
    }

    private static void cerrarRecursos() {
        scanner.close();
        System.out.println("Recursos liberados correctamente");
    }
}
