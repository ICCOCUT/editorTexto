import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultadosTabla extends JFrame {
    public JTable resultadosTabla;
    private final EvaluadorExpresiones evaluador;

    public ResultadosTabla() {
        // Crear un modelo de tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Expresión Aritmética");
        modelo.addColumn("Resultado");

        // Crear la tabla con el modelo
        resultadosTabla = new JTable(modelo);

        // Configurar el contenedor de la tabla
        JScrollPane scrollPane = new JScrollPane(resultadosTabla);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Configurar la ventana
        setTitle("Resultados de Expresiones Aritméticas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        evaluador = new EvaluadorExpresiones();
    }

    public void agregarResultado(String expresion, String resultado) {
        DefaultTableModel modelo = (DefaultTableModel) resultadosTabla.getModel();
        modelo.addRow(new Object[]{expresion, resultado});
    }

    public void mostrarVentana() {
        SwingUtilities.invokeLater(() -> setVisible(true));

    }
}
