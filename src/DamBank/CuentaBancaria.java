package DamBank;
/**
 * Clase que representa una cuenta bancaria con IBAN, titular, saldo y movimientos.
 */
public class CuentaBancaria {
    public static final int TOTAL_MOVIMIENTOS = 10;
    private final String iban;
    private final String titularCuenta;
    private double saldo;
    private final double[] movimientos;
    private int movimientosRealizados;

    /**
     * Constructor de la cuenta bancaria.
     * @param iban Número IBAN (2 letras + 22 dígitos).
     * @param titular Objeto Persona como titular.
     * @throws IllegalArgumentException Si el IBAN es inválido.
     */

    public CuentaBancaria(String iban, String titular) {
        if (!validarIBAN(iban)){
            throw new IllegalArgumentException("IBAN no válido");
        }
        this.iban = iban.toUpperCase(); //Almacenamos en mayúsculas
        this.titularCuenta = titular;
        this.saldo = 0.0;
        this.movimientos = new double[TOTAL_MOVIMIENTOS];
        this.movimientosRealizados = 0;
    }


    /**
     * Valida un IBAN verificando:
     * - Longitud total de 24 caracteres.
     * - Primeros 2 caracteres son letras (A-Z, a-z).
     * - Siguientes 22 caracteres son dígitos (0-9).
     */

    private boolean validarIBAN(String iban){
        if (iban == null || iban.length() !=24) {
            return false;
        }

        // Validación de las dos primeras letras
        String letras = iban.substring(0,2);
        for (int cont = 0; cont < letras.length(); cont++) {
            char caracter = letras.charAt(cont);
            if (!Character.isLetter(caracter)) {
                return false;
            }
        }

        // Validar siguientes 22 dígitos
        for (int i = 2; i < 22; i++) {
            char caracter = iban.charAt(i);
            if (!Character.isDigit(caracter)) {
                return false;
            }
        }

        return true; // IBAN válido
    }

    /**
     * Realizar ingreso en la cuenta
     * @Param importe Cantidad a ingresar (debe ser positiva)
     * @return true si la operación fue exitosa, false si hubo un error.
     */
    public boolean ingresar(double importe) {

        // Se verifica que la cantidad no sea negativa
        if (importe <= 0) {
            System.out.println("ERROR: La cantidad debe ser positiva");
            return false;
        }

        //realizar ingreso
        saldo += importe;
        agregarMovimiento(importe);
        avisoHacienda(importe);
        return true;
    }

    /**
     * Realiza una retirada de la cuenta.
     * @param importe Cantidad a retirar (debe ser positiva).
     * @return true si la operación fue exitosa, false si hubo un error.
     */
    public boolean retirar(double importe) {

        // Se verifica que la cantidad no sea negativa
        if (importe <= 0) {
            System.out.println("ERROR: La cantidad debe ser positiva");
            return false;
        }

        //realizar ingreso
        saldo -= importe;
        agregarMovimiento(importe);
        avisoHacienda(importe); // LLama a la función para las advertencias
        return true;

    }

    public boolean avisoHacienda(double importe) {
        if (importe > 300) {
            System.out.println("AVISO: Notificar a hacienda");
        }
        if (saldo < 0 && saldo >= -50) {
            System.out.println("AVISO: Saldo negativo");
        }
        return true;

    }

    /**
     * Agrega un movimiento al array de movimientos, manteniendo un máximo de TOTAL_MOVIMIENTOS (10).
     * Si el array está lleno, elimina el movimiento más antiguo para hacer espacio al nuevo.
     * @param monto Monto del movimiento a agregar (positivo para ingresos, negativo para retiradas).
     */
    private void agregarMovimiento(double monto) {
        if (movimientosRealizados < TOTAL_MOVIMIENTOS) {
            // Caso 1: Aún hay espacio en el array.
            movimientos[movimientosRealizados] = monto; // Almacena el monto en la posición actual.
            movimientosRealizados++;
        } else {
            // Caso 2: El array está lleno (10 movimientos).
            // Desplaza todos los elementos una posición hacia la izquierda.
            for (int i = 0; i < TOTAL_MOVIMIENTOS - 1; i++) {
                movimientos[i] = movimientos[i + 1]; // Sobrescribe el elemento actual con el siguiente
            }
            movimientos[TOTAL_MOVIMIENTOS - 1] = monto; // Agrega el nuevo monto en la última posición
        }
    }

    /**
     * Muestra el historial de movimientos de la cuenta.
     * Los movimientos se muestran en formato: "Tipo: ±Monto€".
     * - Ingresos: con signo positivo (+).
     * - Retiradas: con signo negativo (-).
     * Si no hay movimientos, muestra un mensaje indicándolo.
     */
    public void mostrarMovimientos() {
        System.out.println("--- HISTORIAL DE MOVIMIENTOS ---");
        if (movimientosRealizados == 0) {
            System.out.println("No hay movimientos registrados.");
            return;
        }

        // Iterar sobre los movimientos y formatearlos
        for (int i = 0; i < TOTAL_MOVIMIENTOS - 1; i++) {
            double monto = movimientos[i];

            String tipo = monto > 0 ? "Ingresar" : "Retidara";
            String signo = monto > 0 ? "+" : "-";
            System.out.printf("%s: %s%.2f€\n", tipo, signo, Math.abs(monto));

        }
    }

    // Getters
    public String getIban() {
        return iban;
    }

    public String getTitularCuenta() {
        return titularCuenta;
    }

    public double getSaldo() {
        return saldo;
    }
}
