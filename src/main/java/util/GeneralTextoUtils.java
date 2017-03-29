package util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public abstract class GeneralTextoUtils {

    private static final String REGEX_VERIFICADOR_URL = ".{0,}<http.{0,}://.{0,}>.{0,}";
    private static final String REGEX_NORMALIZADOR_DE_TEXTO = ",\\D|\\.|\\?|\\!|\\w\\$|,|'|:|\"|;";
    private static final String CONJUNTIVO = "+";
    private static final String DISJUNTIVO = "-";

    /**
     * Exemplo: Falta de água; Falta + água; Falta - água
     * falta+
     * agua
     */

    public static boolean detectaFraseAlvo(String textoAlvo, String fraseOcorrencia) {
        String[][] palavrasAlvo = separaTextoPorOperadoresConjuntivos(fraseOcorrencia);
        textoAlvo = textoAlvo.toUpperCase();
        // caso o texto alvo seja mal construido do tipo {palavra+}
        if (palavrasAlvo[0].length < 2) {
            return hasPalavraTexto(textoAlvo, palavrasAlvo[1][0]);
        }

        //result inicializado com falsos
        boolean[] result = new boolean[palavrasAlvo[0].length];

        boolean resultfinal;

        for (int i = 0; i < palavrasAlvo[0].length; i++) {
            result[i] = hasPalavraTexto(textoAlvo, palavrasAlvo[1][i]);
        }

        resultfinal = result[0];

        // compor resposta final
        for (int i = 1; i <= palavrasAlvo[0].length - 1; i++) {

            if (palavrasAlvo[0][i - 1].equals(DISJUNTIVO)) {
                // se no futuro houver mais operadores trocar para swhitch
                resultfinal = resultfinal && !result[i];
            } else {

                // se no futuro houver mais operadores trocar para swhitch
                resultfinal = resultfinal && result[i];
            }

        }

        return resultfinal;
    }

    private static boolean achaPalavrasExceto(String texto, String palavra) {
        if (hasPalavraTexto(texto, palavra)) {
            return true;
        } else
            return false;
    }

    private static int verificaOcorrenciaspalavrasDesconsideradas(String fraseOcorrencia) {
        int flagSeparadoraDesconsideradas = verificaOcorrenciasTexto(fraseOcorrencia, DISJUNTIVO);
        return flagSeparadoraDesconsideradas;
    }

    private static int verificaOcorrenciasPalavrasConsideradas(String fraseOcorrencia) {
        int flagSeparadoraConsideradas = verificaOcorrenciasTexto(fraseOcorrencia, CONJUNTIVO);
        return flagSeparadoraConsideradas;
    }

    private static boolean ocorrenciaFraseInteira(String texto, String fraseOcorrencia) {
        int contadoraFrases = 0;
        contadoraFrases = verificaOcorrenciasTexto(texto, fraseOcorrencia);
        return contadoraFrases > 0;
    }

    public static String normalizaPalavra(String palavra) {
        return removeCaracteresEspeciais(normalizaPontuacao(palavra)).toUpperCase();
    }

    public static String[] separaTextoPorEspacoEmBranco(String fraseAProcurar) {
        return removeVaziosArray(fraseAProcurar.split(" "));
    }

    public static String[][] separaTextoPorOperadoresConjuntivos(String fraseAProcurar) {
        if (fraseAProcurar == null) {
            String[][] result = {{""}, {""}};
            return result;
        }
        if (!(fraseAProcurar.contains(CONJUNTIVO) || fraseAProcurar.contains(DISJUNTIVO))) {
            String[][] result = {{fraseAProcurar}, {" "}};
            return result;
        }
        while (fraseAProcurar.contains("  ")) {
            fraseAProcurar = fraseAProcurar.replace("  ", " ").toUpperCase();
        }

        fraseAProcurar = fraseAProcurar.trim();

        if ((fraseAProcurar.length() < 1) && !(((fraseAProcurar.charAt(0)) >= 65) || ((fraseAProcurar.charAt(0)) <= 90))) {
            String[][] result = {{fraseAProcurar}, {" "}};
            return result;
        }

        List<String> operadoresConjuntivos = new ArrayList<>();
        List<String> palavras = new ArrayList<>();

        while (fraseAProcurar.length() > 1) {
            boolean retirado = false;
            for (int i = 0; i < fraseAProcurar.length() - 1; i++) {
                String caractere = fraseAProcurar.substring(i, i + 1);
                if (CONJUNTIVO.equals(caractere) || DISJUNTIVO.equals(caractere)) {
                    //Achamos um operador
                    operadoresConjuntivos.add(caractere);
                    palavras.add(fraseAProcurar.substring(0, i));
                    fraseAProcurar = fraseAProcurar.substring(i + 1);
                    retirado = true;
                    break;
                }
            }
            if (retirado) {
                retirado = false;
            } else {
                operadoresConjuntivos.add(" ");
                palavras.add(fraseAProcurar);
                fraseAProcurar = "";
            }
        }

        String[][] result = {operadoresConjuntivos.toArray(new String[0]), palavras.toArray(new String[0])};

        return result;
    }

    public static float contaPalavrasIguais(String[] palavrasFrasePivo, String[] palavrasFraseAdjacente) {
        float acertos = 0;
        for (int i = 0; i < palavrasFrasePivo.length; i++) {
            String palavra = palavrasFrasePivo[i];
            for (int j = 0; j < palavrasFraseAdjacente.length; j++) {
                String palavraAComparar = palavrasFraseAdjacente[j];
                if (palavra.equals(palavraAComparar)) {
                    acertos++;
                }
            }
        }
        return acertos;
    }

    public static boolean hasPalavraTexto(String textoAlvo, String textoAProcurar) {
/*	String[] palavrasTexto = separaTextoPorEspacoEmBranco(textoAlvo);
    int qtdPalavra=0;
	for (int i = 0; i < palavrasTexto.length; i++) {
	    String palavraAtual = palavrasTexto[i];
	    if(palavraAtual.equals(textoAProcurar)){
		qtdPalavra++;
	    }
	}
	return qtdPalavra>0;*/

        return textoAlvo.contains(textoAProcurar);
    }

    public static boolean isNull(String texto) {
        if (texto != null)
            return false;
        else
            return true;

    }

    public static boolean isEmpty(String texto) {
        return texto.isEmpty();

    }

    public static int verificaOcorrenciasTexto(String texto, String textoOcorrencia) {
        String[] palavrasTexto = separaTextoPorEspacoEmBranco(texto);
        String[] palavrasTextoOcorrencia = separaTextoPorEspacoEmBranco(textoOcorrencia);
        Integer qtdPalavrasIguais = (int) contaPalavrasIguais(palavrasTexto, palavrasTextoOcorrencia);
        return qtdPalavrasIguais;

    }

    public static String[] separaTextoPorDelimitador(String textoEntrada, String delimitador) {

        return removeVaziosArray(textoEntrada.split(delimitador));
    }

    public static String removeCaracteresEspeciais(String textoEntrada) {
        return StringUtils.stripAccents(textoEntrada);
    }

    public static String[] separaFraseSubtopico(String texto) {
        String osSystemDelimiter = System.lineSeparator();

//MUDAR AQUI

        //REMOVER OS \n
        texto = texto.replace("\n", " ");
        texto = separaFrasePorPontuacao(texto);


        String[] subTopicos = StringUtils.stripAll(separaTextoPorDelimitador(texto, osSystemDelimiter));
        subTopicos = removeVaziosArray(subTopicos);
        return subTopicos;
    }

    public static String separaFrasePorPontuacao(String textoParaSeparar) {

        textoParaSeparar = textoParaSeparar.replace("?", "?\n");
        textoParaSeparar = textoParaSeparar.replace("!", "!\n");

        boolean continuar = true;
        int indice = 0;

        while (continuar) {

            boolean achei = false;

            indice = textoParaSeparar.indexOf(".", indice);
            if (indice > -1) {
                //na esquerda é letra?
                char letra = textoParaSeparar.toUpperCase().charAt(indice - 1);

                if ((letra > 64) && (letra < 91)) {
                    // acrescentar um \n
                    textoParaSeparar = textoParaSeparar.substring(0, indice) + ".\n" + textoParaSeparar.substring(indice + 1);
                    achei = true;
                    indice++;
                }

            }

            if (!achei) {
                continuar = false;
            }

        }

        return textoParaSeparar;
    }

    public static List<String[]> separaTextoPorPalavras(String[] textos) {
        List<String[]> palavrasSegmentadas = new ArrayList<>();


        for (String subTopico : textos) {
            palavrasSegmentadas.add(removeVaziosArray(subTopico.split(" ")));
        }

        return palavrasSegmentadas;
    }

    public static boolean hasHyperLink(String text) {
        return text.matches(REGEX_VERIFICADOR_URL);
    }

    public static int wordNumbers(String text) {
        return new StringTokenizer(text).countTokens();
    }

    public static boolean comecaComNumero(String palavra) {

        char codigoAscii = palavra.charAt(0);
        if (codigoAscii >= 48 && codigoAscii <= 57) {
            return true;
        } else {
            return false;
        }
    }

    private static String[] removeVaziosArray(String[] arrayString) {
        final String[] EMPTY_STRING_ARRAY = new String[0];
        List<String> stringAuxiliar = new ArrayList<>(Arrays.asList(arrayString));
        for (String string : arrayString) {
            if (string.isEmpty()) {
                stringAuxiliar.remove(string);
            }
        }
        return stringAuxiliar.toArray(EMPTY_STRING_ARRAY);
    }

    public static String normalizaPontuacao(String texto) {

        return texto.replaceAll(REGEX_NORMALIZADOR_DE_TEXTO, " ");
    }

    public static String constroiTextoParaProcessar(String texto) {
        StringBuilder sb = new StringBuilder();

        String[] textoSegmentato = separaFraseSubtopico(texto);
        for (int i = 0; i < textoSegmentato.length; i++) {
            String string2 = textoSegmentato[i];
            sb.append(string2 + "\n");
        }
        return sb.toString();
    }


}
