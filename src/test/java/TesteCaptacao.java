import mineracao.MineradorDeNoticias;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

/**
 * Created by apollo on 19/10/15.
 */
public class TesteCaptacao {

    public static void main(String[] args) throws IOException {
        String url = "http://www.opovo.com.br/app/fortaleza/2015/09/22/noticiafortaleza,3507763/taxistas-alternativos-e-sindtaxi-acompanham-votacao-na-camara.shtml";
        String url2 = "http://www.opovo.com.br/app/colunas/segurancapublica/2015/10/19/noticiassegurancapublica,3520939/ciclo-da-discordia.shtml";
        String url3 = "http://diariodonordeste.verdesmares.com.br/cadernos/negocios/online/greve-e-encerrada-em-bancos-privados-1.1419524";
        String url4 = "http://diariodonordeste.verdesmares.com.br/cadernos/negocios/oi-proposta-de-aporte-de-us-4-bi-1.1419549";
        String url5 = "http://diariodonordeste.verdesmares.com.br/cadernos/negocios/online/mcdonald-s-aposta-no-quarterao-para-encerrar-o-ano-em-alta-1.1419523";
        String url6 = "http://oglobo.globo.com/brasil/pf-investiga-se-carvalho-fez-lobby-para-montadoras-17884888";
        String url7 = "http://blogs.oglobo.globo.com/mansur/post/para-zico-indicacao-de-secretario-geral-da-uefa-minou-suas-chances.html";
        String url8 = "http://www.adorocinema.com/noticias/series/noticia-116850/";
        String url9 = "http://www.adorocinema.com/noticias/filmes/noticia-116740/";
        String url10 = "http://legiaodosherois.uol.com.br/2015/supergirl-melissa-benoist-responde-comentarios-de-candidato-presidencia-dos-eua.html";
        String url11 = "http://googlemapsapi.blogspot.com.br/2006/06/geocoding-at-last.html";
        MineradorDeNoticias minerador = new MineradorDeNoticias();
        Document document = minerador.getDocumentByUrl(url3);
        String tag = "p";
        Elements p = minerador.getMostElementsByTagFromBody(document, tag);
        if (p.text().isEmpty()){
            tag="br";
            p=minerador.getMostElementsByTagFromBody(document, tag);
        }
        Map<Element, Elements> links = minerador.getLinksFromTag(p);
        StringBuilder Noticia = minerador.getProvalvelNoticia(p);
        System.out.println(Noticia);
//        System.out.println(minerador.buildPossibleNews(p));
    }





}
