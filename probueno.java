package automata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class probueno {
    // ExpresiÃ³n regular que valida cadenas que comienzan con una letra (incluyendo
    // caracteres acentuados) y pueden contener nÃºmeros
    private static final String Letra = "[a-zA-ZÃ¡-ÃºÃ�-Ãš][a-zA-Z0-9Ã¡-ÃºÃ�-Ãš]*";

    public static void main(String[] args) {
        // Asegura que la interfaz grÃ¡fica se cree en el hilo de eventos de Swing para
        // evitar problemas de concurrencia
        SwingUtilities.invokeLater(probueno::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Crea la ventana principal de la aplicaciÃ³n con un tÃ­tulo
        JFrame frame = new JFrame("Equipo 11. Validador de ExpresiÃ³n Regular");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        // Crea un campo de texto con una longitud de 20 caracteres para ingresar la
        // cadena a validar
        JTextField textField = new JTextField(20);
        JButton validateButton = new JButton("Validar");
        JButton clearButton = new JButton("Limpiar");
        JLabel resultLabel = new JLabel("");

        // Agrega una etiqueta para indicar al usuario que ingrese un texto en el campo
        JLabel instructionLabel = new JLabel("Ingresa una variable a validar:");

        // Agrega un listener al botÃ³n de validaciÃ³n para verificar si el texto
        // ingresado cumple con la expresiÃ³n regular
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                if (Pattern.matches(Letra, input)) {
                    resultLabel.setText(input + "  es VÃ¡lido");
                    resultLabel.setForeground(Color.GREEN);
                } else {
                    resultLabel.setText(input + " es InvÃ¡lido");
                    resultLabel.setForeground(Color.RED);
                }
            }
        });

        // Agrega un listener al botÃ³n de limpiar para borrar el texto ingresado y el
        // resultado
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                resultLabel.setText("");
            }
        });

        frame.add(instructionLabel);
        frame.add(textField);
        frame.add(validateButton);
        frame.add(clearButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }
}
