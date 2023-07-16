package pt.ulusofona.aed.rockindeisi2023;

class ExtrasClass {
    String id;
    int duracao;
    String letra;
    int popularidade;
    double dancabilidade;
    double vivacidade;
    String volume;


    ExtrasClass() {
    }

    ExtrasClass(String id, int duracao, String letra, int popularidade, double dancabilidade, double vivacidade, String volume) {
        this.id = id;
        this.duracao = duracao;
        this.letra = letra;
        this.popularidade = popularidade;
        this.dancabilidade = dancabilidade;
        this.vivacidade = vivacidade;
        this.volume = volume;
    }


    @Override
    public String toString() {
        return String.format("[ID: %s | Duração: %d | Letra: %s | Popularidade: %d | Dancabilidade: %f | Vivacidade: %f | Volume: %s]", id, duracao, letra, popularidade, dancabilidade, vivacidade, volume);
    }

    void setid(String id) {
        this.id = id;
    }

    void setduracao(int duracao) {
        this.duracao = duracao;
    }

    void setletra(String letra) {
        this.letra = letra;
    }

    void setpopularidade(int popularidade) {
        this.popularidade = popularidade;
    }

    void setdancabilidade(double dacabilidade) {
        this.dancabilidade = dacabilidade;
    }

    void setvivacidade(double vivacidade) {
        this.vivacidade = vivacidade;
    }

    void setvolume(String volume) {
        this.volume = volume;
    }


    String getid() {
        return id;
    }

    int getduracao() {
        return duracao;
    }

    String getletra() {
        return letra;
    }

    int getpopularidade() {
        return popularidade;
    }

    double getdancabilidade() {
        return dancabilidade;
    }

    double getvivacidade() {
        return vivacidade;
    }

    String getvolume() {
        return volume;
    }
}
