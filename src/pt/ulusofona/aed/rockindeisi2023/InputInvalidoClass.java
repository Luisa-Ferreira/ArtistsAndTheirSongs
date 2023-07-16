package pt.ulusofona.aed.rockindeisi2023;

public class InputInvalidoClass {

    String leitura;
    int linhasOK;
    int linhasNOK;
    int primeiraLinhaNOK;

    InputInvalidoClass() {

    }

    public InputInvalidoClass(String leitura, int linhasOK, int linhasNOK, int primeiraLinhaNOK) {
        this.leitura = leitura;
        this.linhasOK = linhasOK;
        this.linhasNOK = linhasNOK;
        this.primeiraLinhaNOK = primeiraLinhaNOK;
    }

    @Override
    public String toString() {
        return leitura + " | " + linhasOK + " | " + linhasNOK + " | " + primeiraLinhaNOK;
    }

}
