package automata;

// Para manejar los diseÃ±os de la ventana
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList; // Lista dinÃ¡mica
import java.util.List; // Lista genÃ©rica

// Importamos las bibliotecas necesarias para el programa
// Interfaz grÃ¡fica (ventanas, botones, etc.)
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel; // Modelo de tabla para almacenar datos

public class AnalizadorLexicoGUI {
    private static final String REGEX_ID = "[a-zA-Zá-úÁ-Ú][a-zA-Z0-9á-úÁ-Ú]*";
    private static final String REGEX_INT = "-?\\d+";
    private static final String REGEX_REAL = "-?\\d+\\.\\d+";
    private static final String REGEX_STRING = "\"[^\"]*\"";
  

    // Componentes de la interfaz grÃ¡fica
    private JTextArea inputArea, outputArea; // Ã�reas de texto para entrada y salida
    private JTable symbolTable; // Tabla de sÃ­mbolos
    private DefaultTableModel tableModel; // Modelo para manejar los datos de la tabla

    // Constructor: Inicializa la interfaz grÃ¡fica
    public AnalizadorLexicoGUI() {
        // Crear la ventana principal
        JFrame frame = new JFrame("Analizador LÃ©xico");
        frame.setSize(700, 500); // TamaÃ±o de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el programa al salir
        frame.setLayout(new BorderLayout()); // DiseÃ±o de la ventana

        // Panel superior: Entrada de cÃ³digo
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BorderLayout());

        inputArea = new JTextArea(7, 50); // Ã�rea de texto donde el usuario escribe el cÃ³digo
        JScrollPane inputScroll = new JScrollPane(inputArea); // Agregamos scroll para mejor visualizaciÃ³n
        panelTop.add(new JLabel("Ingresea un cÃ³digo:"), BorderLayout.NORTH); // Etiqueta arriba
        panelTop.add(inputScroll, BorderLayout.CENTER); // Agregamos el Ã¡rea de texto al panel

        frame.add(panelTop, BorderLayout.NORTH); // Agregamos el panel superior a la ventana

        // Panel central: Salida de datos y tabla de sÃ­mbolos
        outputArea = new JTextArea(10, 50); // Ã�rea de texto para mostrar los resultados
        outputArea.setEditable(false); // No se puede editar manualmente
        JScrollPane outputScroll = new JScrollPane(outputArea); // Agregamos scroll

        // ConfiguraciÃ³n de la tabla de sÃ­mbolos
        tableModel = new DefaultTableModel(new String[] { "Lexema", "Tipo", "Línea" }, 0);
        symbolTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(symbolTable); // Scroll para la tabla

        // Panel que contiene la salida de datos y la tabla
        JPanel panelCenter = new JPanel(new GridLayout(2, 1));
        panelCenter.add(outputScroll);
        panelCenter.add(tableScroll);
        frame.add(panelCenter, BorderLayout.CENTER); // Agregamos el panel al centro de la ventana

        // Panel inferior: Botones para analizar y limpiar
        JPanel panelButtons = new JPanel();
        JButton analyzeButton = new JButton("Ejecutar"); // BotÃ³n para analizar el cÃ³digo
        JButton clearButton = new JButton("Limpiar"); // BotÃ³n para limpiar la entrada y salida

        // AcciÃ³n cuando se presiona el botÃ³n "Analizar"
        analyzeButton.addActionListener(e -> analizarCodigo());

        // AcciÃ³n cuando se presiona el botÃ³n "Limpiar"
        clearButton.addActionListener(e -> limpiarCampos());

        // Agregamos los botones al panel
        panelButtons.add(analyzeButton);
        panelButtons.add(clearButton);

        // Agregamos el panel de botones a la ventana
        frame.add(panelButtons, BorderLayout.SOUTH);

        // Hacemos visible la ventana
        frame.setVisible(true);
    }


    private void analizarCodigo() {
        String codigo = inputArea.getText();
        outputArea.setText("");
        tableModel.setRowCount(0);
        List<Simbolo> tablaSimbolos = new ArrayList<>();

        String[] lineas = codigo.split("\\n");
        int linea = 1;

        // Expresiones regulares para identificar tipos y variables
        final String REGEX_KEYWORD = "^(ent->|rea->|cad->|id->)$";
        final String REGEX_INT = "-?\\d+";
        final String REGEX_REAL = "-?\\d+\\.\\d+";
        final String REGEX_STRING = "\"[^\"]*\"";
        final String REGEX_ID = "[a-zA-Zá-úÁ-Ú_][a-zA-Z0-9á-úÁ-Ú_]*";
        final String REGEX_SEPARATOR = "[,;\\s]+";

        String tipoActual = "";

        for (String lineaCodigo : lineas) {
            String[] palabras = lineaCodigo.split(REGEX_SEPARATOR);

            for (String palabra : palabras) {
                palabra = palabra.trim();
                if (palabra.isEmpty()) continue; // Ignorar espacios vacíos

                if (palabra.matches(REGEX_KEYWORD)) { // Si es un tipo de variable
                    tipoActual = palabra;
                    tablaSimbolos.add(new Simbolo(palabra, "", linea)); // Añadir el tipo sin definir otro tipo
                } else {
                    // Asociar variables con el tipo actual
                    switch (tipoActual) {
                        case "ent->":
                            if (palabra.matches(REGEX_ID) || palabra.matches(REGEX_INT)) {
                                tablaSimbolos.add(new Simbolo(palabra, "ent->", linea));
                            }
                            break;
                        case "rea->":
                            if (palabra.matches(REGEX_ID) || palabra.matches(REGEX_REAL)) {
                                tablaSimbolos.add(new Simbolo(palabra, "rea->", linea));
                            }
                            break;
                        case "cad->":
                            if (palabra.matches(REGEX_ID) || palabra.matches(REGEX_STRING)) {
                                tablaSimbolos.add(new Simbolo(palabra, "cad->", linea));
                            }
                            break;
                        default:
                            outputArea.append("SIMBOLO NO RECONOCIDO: " + palabra + "\n");
                            break;
                    }
                }
            }
            linea++;
        }

        for (Simbolo simbolo : tablaSimbolos) {
            tableModel.addRow(new Object[]{simbolo.lexema, simbolo.tipo, simbolo.linea});
        }
    }


    /**
     * FunciÃ³n para limpiar la entrada, salida y la tabla.
     */
    private void limpiarCampos() {
        inputArea.setText(""); // Borra el texto de entrada
        outputArea.setText(""); // Borra el texto de salida
        tableModel.setRowCount(0); // Borra la tabla de sÃ­mbolos
    }

    /**
     * MÃ©todo principal para ejecutar la aplicaciÃ³n.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AnalizadorLexicoGUI::new);
    }
}

/**
 * Clase para representar un sÃ­mbolo en la tabla de sÃ­mbolos.
 */
class Simbolo {
    String lexema, tipo; // El lexema y su tipo (entero, real, cadena, identificador)
    int linea; // LÃ­nea en la que aparece el sÃ­mbolo

    /**
     * Constructor de la clase Simbolo.
     */
    public Simbolo(String lexema, String tipo, int linea) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.linea = linea;
    }
}
