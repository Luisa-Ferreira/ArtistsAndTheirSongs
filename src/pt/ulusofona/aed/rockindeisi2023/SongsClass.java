package pt.ulusofona.aed.rockindeisi2023;

import java.util.ArrayList;

public class SongsClass {

    String id;
    String titulo;
    int anoLancamento;
    ExtrasClass details;
    ArrayList<ArtistsClass> artists;

    public SongsClass(String id, String titulo, int anoLancamento, ExtrasClass details, ArrayList<ArtistsClass> artists) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.details = details;
        this.artists = new ArrayList<>();


    }

    public void adicionarArtistaAssociado(ArtistsClass artista) {
        this.artists.add(artista);
    }


    SongsClass() {

    }

    public String toString() {
        String str = "", formatoDuracao = "";
        if (details != null) {
            int minutos = details.duracao / 60000;
            int segundos = details.duracao / 1000 - minutos * 60;
            if (segundos >= 10) {
                formatoDuracao = minutos + ":" + segundos;
            } else {
                formatoDuracao = minutos + ":0" + segundos;
            }
        }

        if (anoLancamento < 1995) {
            str = id + " | " + titulo + " | " + anoLancamento;
        } else if (anoLancamento < 2000) {
            str = id + " | " + titulo + " | " + anoLancamento + " | " + formatoDuracao + " | " + details.popularidade;
        } else {
            str = id + " | " + titulo + " | " + anoLancamento + " | " + formatoDuracao + " | " + details.popularidade + " | " + artists.size();
        }

        return str;
    }

    public String toString1() {
        return "SongsClass{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anoLancamento=" + anoLancamento +
                ", details=" + details +
                '}';
    }


    public String getId() {
        return id;
    }

    public ExtrasClass getDetails() {
        return details;
    }
}
