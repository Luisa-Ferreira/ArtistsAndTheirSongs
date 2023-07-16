package pt.ulusofona.aed.rockindeisi2023;

import java.util.ArrayList;

class ArtistsClass {

    String grupoNomes;
    int numMusicas;
    ArrayList<SongsClass> musicas;

    double popularidademedia;
    ArtistsClass() {

    }

    ArtistsClass(String nomes, int numbMusicas, ArrayList<SongsClass> musicas) {
        this.grupoNomes = nomes;
        this.numMusicas = numbMusicas;
        this.musicas = new ArrayList<>();
    }

    ArtistsClass(String nomes) {
        this.grupoNomes = nomes;
    }

    @Override
    public String toString() {
        String str = "";

        if (grupoNomes.charAt(0) == 'A' || grupoNomes.charAt(0) == 'B' || grupoNomes.charAt(0) == 'C' || grupoNomes.charAt(0) == 'D') {
            str = "Artista: [" + grupoNomes.trim() + "]";
        } else {
            str = "Artista: [" + grupoNomes.trim() + "]" + " | " + numMusicas;
        }


        return str;
    }


    void setGrupoNomes(String nomes) {
        this.grupoNomes = nomes;
    }


    String getgrupoNomes() {
        return grupoNomes;
    }

    public Object getGrupoNomes() {
        return grupoNomes;
    }

    public int size() {
        return grupoNomes.length();
    }
}
