package util;

import models.StopWord;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public abstract class TextoUtils {

    private final static String[] EMPTY_STRING_ARRAY = new String[0];


    public static String normalizaPalavra(String palavra) {
        return removeCaracteresEspeciais(normalizaPontuação(palavra)).toUpperCase();
    }

    public static String[] separaTextoPorEspacoEmBranco(String textoEntrada) {
        return removeVaziosArray(new ArrayList<>(Arrays.asList(textoEntrada.split(" "))));
    }

    public static String[] separaTextoPorDelimitador(String textoEntrada, String delimitador) {

        return removeVaziosArray(new ArrayList<>(Arrays.asList(textoEntrada.split(delimitador))));
    }

    public static String removeCaracteresEspeciais(String textoEntrada) {
        return StringUtils.stripAccents(textoEntrada);
    }

    public static String[] separaFraseSubtopico(String texto) {
        String osSystemDelimiter = System.lineSeparator();
        String[] subTopicos = StringUtils.stripAll(separaTextoPorDelimitador(texto, osSystemDelimiter));
        subTopicos = removeVaziosArray(new ArrayList<>(Arrays.asList(subTopicos)));

        return subTopicos;
    }

    private static String removeStopWordsTexto(List<StopWord> stopWords, String[] palavras) {
        String novaFrase = "";
        List<String> auxiliar = new ArrayList<>(new ArrayList<>(Arrays.asList(palavras)));

        for (String palavra : palavras) {
            if (isStopWord(palavra, stopWords)) {
                auxiliar.remove(palavra);
            }
        }
        novaFrase = constroiTexto(novaFrase, auxiliar);

        return novaFrase;
    }

    private static String constroiTexto(String novaFrase, List<String> auxiliar) {
        for (int i = 0; i < auxiliar.size(); i++) {
            String string = auxiliar.get(i);

            if (string.equals(auxiliar.get(auxiliar.size() - 1))) {
                if (string.contains("\n")) {
                    novaFrase += string;
                    break;
                }
                novaFrase += string + "\n";
                break;
            }
            novaFrase += string + " ";
        }
        return novaFrase;
    }

    private static String removePequenasPalavrasTexto(String[] palavras) {
        String novaFrase = "";
        List<String> palavrasTexto = new ArrayList<>(Arrays.asList(palavras));
        for (String palavra : palavras) {
            if (palavra.length() < 3 && !comecaComNumero(palavra)) {
                palavrasTexto.remove(palavra);
            }
        }

        novaFrase = constroiTexto(novaFrase, palavrasTexto);

        return novaFrase;

    }



    private static boolean isStopWord(String palavra, List<StopWord> stopWords) {
        for (StopWord stopWord : stopWords) {
            if (palavra.equals(stopWord.getNome())) {
                return true;
            }
        }
        return false;

    }

    public static List<String[]> separaTextoPorPalavras(String[] textos) {
        List<String[]> palavrasSegmentadas = new ArrayList<>();

        for (String subTopico : textos) {
            palavrasSegmentadas.add(removeVaziosArray(new ArrayList<>(Arrays.asList(subTopico.split(" ")))));
        }

        return palavrasSegmentadas;
    }

    public static boolean hasHyperLink(String text) {
        return text.matches(".{0,}<http.{0,}://.{0,}>.{0,}");
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

    /**
     * @param textos
     * @return textos sem espaços
     * <h3>Método para remover possiveis lacunas na segmentação de texto
     * </h3>
     * <p>
     * Esse método usa o padrão Iterator do java para remover possiveis
     * espaços entre o array gerado do armazenamento
     * </p>
     */
    public static String[] removeVaziosArray(List<String> textos) {

        // erro. Manoel que diabo esse metodo faz?
        // Li e reli. nao faz nada.

        ListIterator<String> textosAuxiliares = textos.listIterator();
        while (textosAuxiliares.hasNext()) {
            String texto = textosAuxiliares.next();
            if (texto.isEmpty()) {
                textosAuxiliares.remove();
            }
        }
        // por acaso faltou: textos=textosAuxiliares ??

        return textos.toArray(EMPTY_STRING_ARRAY);
    }

    public static String normalizaPontuação(String texto) {

        return texto.replaceAll(",\\D|\\.|\\?|\\!|\\w\\$|,|'|:|\"|;", " ");
    }

    public static String[] mantemApenasFraseAlvo(String[] texto, String alvo) {
        List<String> frases = new ArrayList<>(Arrays.asList(texto));
        ListIterator<String> frasesAuxiliares = frases.listIterator();
        while (frasesAuxiliares.hasNext()) {
            String frase = frasesAuxiliares.next();
            if (GeneralTextoUtils.verificaOcorrenciasTexto(frase, alvo) == 0) {
                frasesAuxiliares.remove();
            }
        }


        return frases.toArray(EMPTY_STRING_ARRAY);
    }


}
