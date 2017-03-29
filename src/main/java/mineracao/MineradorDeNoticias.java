package mineracao;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.TextUtilsPDT;

import javax.ejb.Stateless;

/**
 * Created by manoel on 29/10/15.
 */
 @Stateless
public class MineradorDeNoticias extends Minerador {
    // TODO FAZER A MINERAÇÃO DE NOTICIAS REMOVENDO AS STOPWORDS

    public MineradorDeNoticias() {
        super();
    }



    public StringBuilder getProvalvelNoticia(Elements noticias) {
        StringBuilder provalvelNoticia = new StringBuilder();
        String provalvelNoticiaAux = "";
        String[] palavrasTitulo = TextUtilsPDT.removeStopWords(getTitleDocument(document).split(" "));
        Elements paragrafosElegiveis = new Elements();
        for (Element noticia : noticias) {
            provalvelNoticiaAux = noticia.text();
            String[] palavrasNoticia = provalvelNoticiaAux.split(" ");
            if (TextUtilsPDT.comparaFrases(palavrasTitulo, palavrasNoticia) >= 0.75) {
                paragrafosElegiveis.add(noticia);
            }
        }
        return provalvelNoticia;
    }

    public String buildPossibleNews(Elements elements) {
        Elements possibleNews = getPossibleNews(elements);
        StringBuilder builder = new StringBuilder();
        for (Element element : possibleNews) {
            builder.append(element.text()+"\n");
        }
        return builder.toString();
    }

    private Elements getPossibleNews(Elements elements) {
        Elements possibleNews = new Elements();
        Element elemetAtual = elements.first();

        for (int i = 0; i < elements.size(); i++) {
            for (Element element : elements) {
                if (elemetAtual.equals(element))
                    continue;
                String[] palavrasNewsAtual = elemetAtual.text().split(" ");
                String[] palavrasNewsAComparar = element.text().split(" ");
                if (TextUtilsPDT.comparaFrases(palavrasNewsAtual, palavrasNewsAComparar) >= 0.75) {
                    possibleNews.add(element);
                }
            }
            if (i + 1 < elements.size())
                elemetAtual = elements.get(i + 1);
        }

        /*while (i\terator.hasNext()) {
            Element newsAtual = iterator.next();

            if (iterator.hasNext())++++-
                elemetAtual = iterator.next();
        }*/


        return possibleNews;
    }


    private String getTitleDocument(Document document) {
        return document.title();
    }


}
