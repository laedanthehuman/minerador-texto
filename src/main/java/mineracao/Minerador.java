package mineracao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by manoel on 29/10/15.
 */
public abstract class Minerador {

    protected  Document document;

    public  Elements getMostElementsByTagFromBody(Document document, String tag) {
        Elements body = document.getElementsByTag("body");
        Elements p = null;
        int contadorDeTag = 0;
        int tagP = 0;
        int filhosBody = body.get(0).children().size();
        for (int i = 0; i < filhosBody; i++) {
            Element elementoAtual = body.get(0).child(i);
            contadorDeTag = elementoAtual.getElementsByTag(tag).size();
            if (tagP < contadorDeTag) {
                tagP = contadorDeTag;
                p = elementoAtual.getElementsByTag("p");
            }
        }
        return p;
    }

    public  Document getDocumentByUrl(String url) throws IOException {
        document= Jsoup.connect(url).get();
        return document;
    }



    public  Elements getLinksFromParent(Element parent) {
        return parent.select("a[href]");
    }

    public  Map<Element, Elements> getLinksFromTag(Elements p) {
        Map<Element, Elements> elementos = new Hashtable<>();
        for (Element paragrafo : p) {
            elementos.put(paragrafo,getLinksFromParent(paragrafo));
        }
        return elementos;
    }



}
