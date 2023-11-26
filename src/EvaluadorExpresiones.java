import java.util.ArrayList;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class EvaluadorExpresiones {
     private final ScriptEngine scriptEngine;

    public EvaluadorExpresiones() {
        ScriptEngineManager manager = new ScriptEngineManager();
        scriptEngine = manager.getEngineByName("js");
        if (scriptEngine == null) {
            System.out.println("No se pudo encontrar el motor de script JavaScript.");
        } else {
            System.out.println("Motor de script JavaScript encontrado: " + scriptEngine);
        }
    }

    public String evaluarExpresion(String expresion) throws ScriptException {
        Object resultado = scriptEngine.eval(expresion);
        return resultado.toString();
    }
    public double resolverExpresiones(ArrayList<Token> tokens) {
         Stack<Double> pila = new Stack<>();

        for (Token token : tokens) {
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
        return switch (operador) {
            case "+" -> operand1 + operand2;
            case "-" -> operand1 - operand2;
            case "*" -> operand1 * operand2;
            case "/" -> operand1 / operand2;
            default -> throw new IllegalArgumentException("Operador no válido: " + operador);
        };
    }

}
