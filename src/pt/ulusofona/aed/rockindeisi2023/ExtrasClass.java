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

    public double getDancabilidade() {
        return dancabilidade;
    }
}
