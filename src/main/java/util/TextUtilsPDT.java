package util;

import dao.ConnectionFactory;
import dao.StopWordDao;
import models.StopWord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by manoel on 17/10/15.
 */
public abstract class TextUtilsPDT {

    private final static String[] EMPTY_STRING_ARRAY = new String[0];

    public static double comparaFrases(String[] frase, String[] fraseASerComparada) {
        if (frase.length == 0 | fraseASerComparada.length == 0) {
            return 0.0;
        }
        double qtdPalavrasIguais = 0.0;
        for (int i = 0; i < frase.length; i++) {
            for (int j = 0; j < fraseASerComparada.length; j++) {
                if (frase[i].equals(fraseASerComparada[j]))
                    qtdPalavrasIguais += 0.1;
            }
        }
        return qtdPalavrasIguais;
    }

    public static String[] removeStopWords(String[] frasesRetirar) {
        StopWordDao dao = new StopWordDao(ConnectionFactory.getConnectionFactory().getConnection());
        List<StopWord> stopWords = dao.all();
        List<String> frases = new ArrayList<>(Arrays.asList(frasesRetirar));
        ListIterator<String> frasesAuxiliares = frases.listIterator();
        while (frasesAuxiliares.hasNext()) {
            String palavraAuxiliar = frasesAuxiliares.next();
            for (StopWord sw : stopWords) {
                if (isStopWord(sw, palavraAuxiliar))
                    frasesAuxiliares.remove();
            }
        }
        return frases.toArray(EMPTY_STRING_ARRAY);
    }

    private static boolean isStopWord(StopWord sw, String palavraAuxiliar) {
        if (sw.getNome().equals(palavraAuxiliar))
            return true;
        else
            return false;
    }

}
