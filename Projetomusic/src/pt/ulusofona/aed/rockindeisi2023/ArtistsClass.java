package pt.ulusofona.aed.rockindeisi2023;

class ArtistsClass {

    String grupoNomes;
    int numbMusicas;

    ArtistsClass() {

    }

    ArtistsClass(String nomes, int numbMusicas) {
        this.grupoNomes = nomes;
        this.numbMusicas = numbMusicas;
    }


    @Override
    public String toString() {
        String str = "";

        if (grupoNomes.charAt(0) == 'A' || grupoNomes.charAt(0) == 'B' || grupoNomes.charAt(0) == 'C' || grupoNomes.charAt(0) == 'D') {
            str = "Artista: [" + grupoNomes.trim() + "]";
        } else {
            str = "Artista: [" + grupoNomes.trim() + "]" + " | " + numbMusicas;
        }


        return str;
    }


    void setGrupoNomes(String nomes) {
        this.grupoNomes = nomes;
    }


//    String getid() {
//        return id;
//    }

    String getgrupoNomes() {
        return grupoNomes;
    }

    public Object getGrupoNomes() {
        return grupoNomes;
    }
}
