
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class EditorDeTexto extends JFrame implements ActionListener {
    private final JTextArea areaTexto;
    private final JFileChooser fileChooser;
    private String nombreArchivo;
    private JTextField buscarCampo;
    private final Highlighter highlighter;
<<<<<<< HEAD
    private JTable resultadosTabla = new JTable(new DefaultTableModel(new Object[]{"Expresión Aritmética", "Resultado"}, 0));
    private ResultadosTabla resultadosTablaVentana;
    private ResultadosTabla resultadosTablas;

    private EvaluadorExpresiones evaluador = new EvaluadorExpresiones();
=======
    private final JTable resultadosTabla = new JTable(new DefaultTableModel(new Object[]{"Expresión Aritmética", "Resultado"}, 0));
>>>>>>> 4e9660e25376bc09a3a961b400a32111fd945a0e


    private boolean modoOscuro = true;

    public EditorDeTexto() {
        setTitle("Editor de Texto");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30)); // Color oscuro para el fondo de la ventana

        areaTexto = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        JMenuBar menuBar = new JMenuBar();

        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem nuevoItem = new JMenuItem("Nuevo");
        JMenuItem abrirItem = new JMenuItem("Abrir");
        JMenuItem guardarItem = new JMenuItem("Guardar");
        JMenuItem guardarComoItem = new JMenuItem("Guardar como");
        JMenuItem cerrarItem = new JMenuItem("Cerrar");
       

        archivoMenu.add(nuevoItem);
        archivoMenu.add(abrirItem);
        archivoMenu.add(guardarItem);
        archivoMenu.add(guardarComoItem);
        archivoMenu.add(cerrarItem);

        JMenu edicionMenu = new JMenu("Edición");
        JMenuItem buscarItem = new JMenuItem("Buscar");
        JMenuItem reemplazarItem = new JMenuItem("Reemplazar");
        JMenuItem copiarItem = new JMenuItem("Copiar");
        JMenuItem cortarItem = new JMenuItem("Cortar");
        JMenuItem pegarItem = new JMenuItem("Pegar");
        JMenuItem analizarItem = new JMenuItem("Analizar");
        JMenuItem resolverExpresionesItem = new JMenuItem("Resolver Expresiones");

        edicionMenu.add(buscarItem);
        edicionMenu.add(reemplazarItem);
        edicionMenu.add(copiarItem);
        edicionMenu.add(cortarItem);
        edicionMenu.add(pegarItem);
        edicionMenu.add(analizarItem);
        edicionMenu.add(resolverExpresionesItem);

        JMenuItem cambiarModoItem = new JMenuItem("Cambiar Modo");
        archivoMenu.add(cambiarModoItem);

        menuBar.add(archivoMenu);
        menuBar.add(edicionMenu);

        setJMenuBar(menuBar);

        // Asociar combinaciones de teclas a las funciones
        nuevoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        abrirItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        guardarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        guardarComoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        cerrarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));

        buscarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        reemplazarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        copiarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        cortarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        pegarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        analizarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        nuevoItem.addActionListener(this);
        abrirItem.addActionListener(this);
        guardarItem.addActionListener(this);
        guardarComoItem.addActionListener(this);
        cerrarItem.addActionListener(this);

        buscarItem.addActionListener(this);
        reemplazarItem.addActionListener(this);
        copiarItem.addActionListener(this);
        cortarItem.addActionListener(this);
        pegarItem.addActionListener(this);
        analizarItem.addActionListener(this);
        resolverExpresionesItem.addActionListener(this);
        cambiarModoItem.addActionListener(this);
        ResultadosTabla resultadosTablaVentana = new ResultadosTabla();
        ResultadosTabla resultadosTablas = new ResultadosTabla();

        fileChooser = new JFileChooser();

        if (modoOscuro) {
            configurarTemaOscuro();
        }

        highlighter = areaTexto.getHighlighter();

        setVisible(true);
    }

    private void configurarTemaOscuro() {
        areaTexto.setBackground(new Color(30, 30, 30));
        areaTexto.setForeground(new Color(200, 200, 200));
        getContentPane().setBackground(new Color(30, 30, 30)); // Color oscuro para el fondo de la ventana
    }

    private void configurarTemaClaro() {
        areaTexto.setBackground(Color.WHITE);
        areaTexto.setForeground(Color.BLACK);
        getContentPane().setBackground(Color.WHITE); // Color claro para el fondo de la ventana
    }

    private void alternarModo() {
        modoOscuro = !modoOscuro;
        if (modoOscuro) {
            configurarTemaOscuro();
        } else {
            configurarTemaClaro();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Nuevo":
                areaTexto.setText("");
                nombreArchivo = null;
                break;

            case "Abrir":
                int opcion = fileChooser.showOpenDialog(this);
                if (opcion == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    nombreArchivo = archivo.getAbsolutePath();
                    try {
                        BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
                        areaTexto.setText("");
                        String linea;
                        while ((linea = lector.readLine()) != null) {
                            areaTexto.append(linea + "\n");
                        }
                        lector.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;

            case "Guardar":
                if (nombreArchivo != null) {
                    try {
                        BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo));
                        escritor.write(areaTexto.getText());
                        escritor.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    guardarComo();
                }
                break;

            case "Guardar como":
                guardarComo();
                break;

            case "Cerrar":
                System.exit(0);
                break;

            case "Buscar":
                buscarTexto();
                break;

            case "Reemplazar":
                reemplazarTexto();
                break;

            case "Copiar":
                areaTexto.copy();
                break;

            case "Cortar":
                areaTexto.cut();
                break;

            case "Pegar":
                areaTexto.paste();
                break;

            case "Analizar":
                analizarTexto();
                
                break;
            case "Resolver Expresiones":
                analizarYResolverExpresiones();
                System.out.println("Eligio resoolver expresiones");
                break;

            case "Cambiar Modo":
                alternarModo();
                break;
        }
    }

    private void analizarYResolverExpresiones() {
        String codigo = areaTexto.getText();
        AnalizadorLexico analizador = new AnalizadorLexico();
        ArrayList<Token> tokens = analizador.analizarCodigo(codigo);

        // Filtrar expresiones aritméticas
        ArrayList<Token> expresionesAritmeticas = new ArrayList<>();
        for (Token token : tokens) {
            if (token.getTipo().startsWith("ELEMENTO_")) {
                expresionesAritmeticas.add(token);
            }
        }

        // Resolver expresiones aritméticas y obtener resultados
        ArrayList<Token> resultados = resolverExpresionesAritmeticas(expresionesAritmeticas);

        // Actualizar la tabla de resultados
        actualizarTablaResultados(resultados);

        if (!tokens.isEmpty()) {
            StringBuilder resultadosTexto = new StringBuilder();
            for (Token resultado : resultados) {
                resultadosTexto.append("Tipo: ").append(resultado.getTipo()).append(", Valor: ").append(resultado.getValor()).append("\n");
            }

            // Mostrar resultados en la tabla
            resultadosTablas.agregarResultado(codigo, resultadosTexto.toString());
            resultadosTablas.mostrarVentana();
        } else {
            JOptionPane.showMessageDialog(this, "La lista de tokens está vacía.", "Error", JOptionPane.ERROR_MESSAGE);
        }
}


private ArrayList<Token> resolverExpresionesAritmeticas(ArrayList<Token> expresionesAritmeticas) {
    //EvaluadorExpresiones evaluador = new EvaluadorExpresiones();
    ArrayList<Token> resultados = new ArrayList<>();

    for (Token expresion : expresionesAritmeticas) {
        try {
            System.out.println("Evaluando expresión: " + expresion.getValor()); // Mensaje de depuración
            String resultado = evaluador.evaluarExpresion(expresion.getValor());
            Token resultadoToken = new Token("RESULTADO", resultado);
            resultados.add(resultadoToken);
            System.out.println("Resultado: " + resultado); // Mensaje de depuración
        } catch (Exception e) {
            Token errorToken = new Token("ERROR", "Error al evaluar la expresión");
            resultados.add(errorToken);
            e.printStackTrace(); // Imprimir detalles del error
        }
    }

    return resultados;

}
    private void obtenerYMostrarResultados(ArrayList<Token> tokens) {
        ResultadosTabla resultadosTabla = new ResultadosTabla();
    
        for (Token token : tokens) {
            if (token.getTipo().equals("RESULTADO")) {
                resultadosTabla.agregarResultado(token.getValor(), token.getValor());
            }
        }
    
        resultadosTabla.setVisible(true);
    }

    
    private void actualizarVentanaResultados(ArrayList<Token> tokens) {
        // Crear una instancia de la ventana de resultados
        ResultadosTabla resultadosTabla = new ResultadosTabla();
    
        // Filtrar los tokens que representan resultados de expresiones
        ArrayList<Token> resultadosExpresiones = new ArrayList<>();
        for (Token token : tokens) {
            if (token.getTipo().equals("RESULTADO")) {
                resultadosExpresiones.add(token);
            }
        }
    
        // Agregar los resultados a la ventana
        for (Token resultado : resultadosExpresiones) {
            resultadosTabla.agregarResultado(resultado.getValor(), resultado.getTipo());
        }
    
        // Mostrar la ventana de resultados
        resultadosTabla.setVisible(true);
    }
    
    private void actualizarTablaResultados(ArrayList<Token> tokens) {
        DefaultTableModel modelo = (DefaultTableModel) resultadosTabla.getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

        for (Token token : tokens) {
            modelo.addRow(new Object[]{token.getValor(), token.getTipo()});
        }
    }


    private void buscarTexto() {
        buscarCampo = new JTextField();
        Object[] message = {"Texto a buscar:", buscarCampo};
        int option = JOptionPane.showConfirmDialog(this, message, "Buscar", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String textoBuscar = buscarCampo.getText();
            buscarEnTexto(textoBuscar);
        }
    }

    private void buscarEnTexto(String textoBuscar) {
        if (textoBuscar != null && !textoBuscar.isEmpty()) {
            String texto = areaTexto.getText();
            int inicio = texto.indexOf(textoBuscar);
            if (inicio != -1) {
                int fin = inicio + textoBuscar.length();
                resaltarTexto(inicio, fin);
            } else {
                JOptionPane.showMessageDialog(this, "Texto no encontrado.", "Resultado de la búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void resaltarTexto(int inicio, int fin) {
        // Limpiar resaltados anteriores
        Highlighter.Highlight[] highlights = highlighter.getHighlights();
        for (Highlighter.Highlight highlight : highlights) {
            highlighter.removeHighlight(highlight);
        }

        try {
            highlighter.addHighlight(inicio, fin, DefaultHighlighter.DefaultPainter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void reemplazarTexto() {
        buscarCampo = new JTextField();
        JTextField reemplazarCampo = new JTextField();
        Object[] message = {"Texto a buscar:", buscarCampo, "Reemplazar con:", reemplazarCampo};
        int option = JOptionPane.showConfirmDialog(this, message, "Reemplazar", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String textoBuscar = buscarCampo.getText();
            String textoReemplazar = reemplazarCampo.getText();
            reemplazarEnTexto(textoBuscar, textoReemplazar);
        }
    }

    private void reemplazarEnTexto(String textoBuscar, String textoReemplazar) {
        if (textoBuscar != null && !textoBuscar.isEmpty()) {
            String texto = areaTexto.getText();
            int inicio = texto.indexOf(textoBuscar);
            if (inicio != -1) {
                int fin = inicio + textoBuscar.length();
                areaTexto.replaceRange(textoReemplazar, inicio, fin);
            } else {
                JOptionPane.showMessageDialog(this, "Texto no encontrado.", "Resultado del reemplazo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void guardarComo() {
        int opcion = fileChooser.showSaveDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            nombreArchivo = archivo.getAbsolutePath();
            try {
                BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo));
                escritor.write(areaTexto.getText());
                escritor.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void analizarTexto() {
        System.out.println("Analizando texto");
        String codigo = areaTexto.getText();
        AnalizadorLexico analizador = new AnalizadorLexico();
        ArrayList<Token> tokens = analizador.analizarCodigo(codigo);

        String codigoModificado = reemplazarIdentificadores(tokens, codigo);

        if (!tokens.isEmpty()) {
            StringBuilder resultados = new StringBuilder();

            for (Token token : tokens) {
                System.out.println("Tipo: " + token.getTipo() + ", Valor: " + token.getValor()); // Mensaje de depuración
                resultados.append("Tipo: ").append(token.getTipo()).append(", Valor: ").append(token.getValor()).append("\n");
            }
            JOptionPane.showMessageDialog(this, resultados.toString(), "Resultados del análisis léxico", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("La lista de tokens está vacía."); // Mensaje de depuración
        }

        areaTexto.setText(codigoModificado);
    }

    private String reemplazarIdentificadores(ArrayList<Token> tokens, String codigo) {
        StringBuilder codigoModificado = new StringBuilder(codigo);

        for (Token token : tokens) {
            if (token.getTipo().equals("IDENTIFICADOR")) {
                int inicio = token.getPosicionInicio();
                int fin = inicio + token.getValor().length();
                codigoModificado.replace(inicio, fin, token.getValor());
            }
        }

        return codigoModificado.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorDeTexto::new);
    }
}