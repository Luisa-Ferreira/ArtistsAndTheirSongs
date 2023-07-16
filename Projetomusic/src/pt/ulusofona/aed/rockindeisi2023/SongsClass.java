package pt.ulusofona.aed.rockindeisi2023;

import java.util.ArrayList;

public class SongsClass {

    String id;
    String titulo;
    int anoLancamento;
    ExtrasClass details;
    ArrayList<String> artists = new ArrayList<>();

    public SongsClass(String id, String titulo, int anoLancamento, ExtrasClass details, ArrayList<String> artists) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.details = details;
        this.artists = artists;
    }

    SongsClass() {

    }

    @Override
    public String toString() {
//        return "id: " + id + ", titulo: " + titulo + ", anolancamento: " + anoLancamento + ", details: " + details + ", artistas: " + artists + "\n";

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
        } else if (anoLancamento >= 1995 && anoLancamento < 2000) {
            str = id + " | " + titulo + " | " + anoLancamento + " | " + formatoDuracao + " | " + details.popularidade;
        } else if (anoLancamento >= 2000) {
            str = id + " | " + titulo + " | " + anoLancamento + " | " + formatoDuracao + " | " + details.popularidade + " | " + artists.size();
        }

        return str;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String artista) {
        this.titulo = artista;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }


    public String getId() {
        return id;
    }

    public String gettitulo() {
        return titulo;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void adicionarArtistaAssociado(String artista) {
        this.artists.add(artista);
    }
}
