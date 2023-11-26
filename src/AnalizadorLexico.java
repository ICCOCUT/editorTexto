import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorLexico {

    private int counter = 0;
    private final HashMap<String, Integer> tablaDeSimbolos = new HashMap<>();

    public ArrayList<Token> analizarCodigo(String codigo) {
        ArrayList<Token> tokens = new ArrayList<>();

        // Patrones para reconocer palabras clave, identificadores y símbolos
        String patronPalabraClave = "\\b(nebulizar|cúmulo|fragmento|enigma|nexo|pordefecto|espiral|nube|enlaceNebular|nebulafloracion|LeerEntero|LeerDecimal|LeerCaracter|LeerCadena|intng|doubleng|charng|stringNG|boolng)\\b";
        String patronIdentificador = "\\b[a-zA-Z][a-zA-Z0-9]*\\b";
        String patronSimbolo = "[(){}\\[\\]+\\-*/=]";
    
        String patronNumero = "\\b\\d+(\\.\\d+)?\\b";
        String patronCompleto = String.format("(%s|%s|%s|%s)", patronPalabraClave, patronIdentificador, patronNumero, patronSimbolo);
       

        // Crear objetos Pattern y Matcher
        Pattern pattern = Pattern.compile(patronCompleto);
        Matcher matcher = pattern.matcher(codigo);

        // Analizar el código fuente
        while (matcher.find()) {
            String valor = matcher.group();
            String tipo = determinarTipo(valor);

            // Agregar el token a la lista
            tokens.add(new Token(tipo, valor));
        }

        return tokens;
    }

    private  String determinarTipo(String valor) {

        switch (valor) {
            case "nebulizar":
            case "cumulo":
            case "fragmento":
            case "nexo":
            case "enigma":
            case "pordefecto":
            case "espiral":
            case "nube":
            case "nebulafloracion":
            case "enlaceNebular":
            case "LeerEntero":
            case "LeerDecimal":
            case "LeerCaracter":
            case "LeerCadena":
            case "intng":
            case "doubleng":
            case "charng":
            case "stringNG":
            case "boolng":
                return "PALABRA_CLAVE";
            case "+":
            case "-":
            case "*":
            case "/":
            case "=":
            case "[":
            case "]":
            case "{":
            case "}":
            case "(":
            case ")":
                return "SIMBOLO";
            default:
                if (valor.matches("\\b[a-zA-Z0-9]*\\b")) {
                    // Si es un identificador, asignar un "id" único y agregar a la tabla de símbolos
                    if (!tablaDeSimbolos.containsKey(valor)) {
                        counter++;
                        tablaDeSimbolos.put(valor, counter);
                    }
                    return "ELEMENTO_" + tablaDeSimbolos.get(valor);
                }else if (valor.matches(".*[+\\-*/=].*")) {
                    return "EXPRESION_ARITMETICA";                
                } else {
                    return "DESCONOCIDO";
                }
        }
    }
}
