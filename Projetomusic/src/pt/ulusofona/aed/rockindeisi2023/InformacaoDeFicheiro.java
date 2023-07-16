package pt.ulusofona.aed.rockindeisi2023;

public class InformacaoDeFicheiro {
    String nomedeFicheiro;
    int linhasOK;
    int linhasNOK;
    int primeiraLinhaNOK;

    public InformacaoDeFicheiro(String nomedeFicheiro, int linhasOK, int linhasNOK, int primeiraLinhaNOK) {
        this.nomedeFicheiro = nomedeFicheiro;
        this.linhasOK = linhasOK;
        this.linhasNOK = linhasNOK;
        this.primeiraLinhaNOK = primeiraLinhaNOK;
    }
}
