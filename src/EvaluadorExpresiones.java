import java.util.ArrayList;
import java.util.Stack;

public class EvaluadorExpresiones {

    public String evaluarExpresion(String expresion) {
        try {
            // Tokenizar la expresión
            ArrayList<Token> tokens = tokenize(expresion);

            // Convertir la expresión a notación postfix
            ArrayList<Token> expresionPostfix = convertirAExpresionPostfix(tokens);

            // Resolver la expresión postfix y convertir el resultado a String
            double resultado = resolverExpresionPostfix(expresionPostfix);
            return String.valueOf(resultado);
        } catch (IllegalArgumentException e) {
            return "Error al evaluar la expresión";
        }
    }

    private ArrayList<Token> tokenize(String expresion) {
        ArrayList<Token> tokens = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        for (char c : expresion.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                // Carácter es un dígito o un punto decimal
                buffer.append(c);
            } else if (esOperador(String.valueOf(c))) {
                // Carácter es un operador
                agregarToken(tokens, buffer.toString());
                tokens.add(new Token("OPERADOR", String.valueOf(c)));
                buffer.setLength(0);  // Reiniciar el buffer
            } else if (Character.isWhitespace(c)) {
                // Ignorar espacios en blanco
            } else {
                // Carácter no reconocido (puedes manejar paréntesis, funciones, etc. aquí)
                // Agregar lógica según tus necesidades.
            }
        }

        // Agregar el último token si hay algún contenido en el buffer.
        agregarToken(tokens, buffer.toString());

        return tokens;
    }
    private void agregarToken(ArrayList<Token> tokens, String valor) {
        if (!valor.isEmpty()) {
            tokens.add(new Token("NUMERO", valor));
        }
    }

    private ArrayList<Token> convertirAExpresionPostfix(ArrayList<Token> tokens) {
        ArrayList<Token> expresionPostfix = new ArrayList<>();
        Stack<Token> pila = new Stack<>();

        for (Token token : tokens) {
            if (token.getTipo().equals("NUMERO")) {
                expresionPostfix.add(token);
            } else if (token.getTipo().equals("OPERADOR")) {
                while (!pila.isEmpty() && prioridadOperador(pila.peek().getValor()) >= prioridadOperador(token.getValor())) {
                    expresionPostfix.add(pila.pop());
                }
                pila.push(token);
            }
            // Puedes agregar lógica para otros tipos de tokens (paréntesis, funciones, etc.) aquí.
        }

        while (!pila.isEmpty()) {
            expresionPostfix.add(pila.pop());
        }

        return expresionPostfix;

    }
    private int prioridadOperador(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            // Puedes ajustar la prioridad según tus necesidades.
            default:
                return 0;  // Prioridad predeterminada si el operador no es reconocido.
        }
    }

    private double resolverExpresionPostfix(ArrayList<Token> expresionPostfix) {
        Stack<Double> pila = new Stack<>();

        for (Token token : expresionPostfix) {
            if (token.getTipo().equals("NUMERO")) {
                pila.push(Double.parseDouble(token.getValor()));
            } else if (esOperador(token.getValor())) {
                double operand2 = pila.pop();
                double operand1 = pila.pop();
                double resultado = aplicarOperacion(token.getValor(), operand1, operand2);
                pila.push(resultado);
            }
        }

        return pila.pop();
    }

    private boolean esOperador(String valor) {
        return valor.equals("+") || valor.equals("-") || valor.equals("*") || valor.equals("/");
    }

    private double aplicarOperacion(String operador, double operand1, double operand2) {
        switch (operador) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new IllegalArgumentException("División por cero");
                }
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }
}
