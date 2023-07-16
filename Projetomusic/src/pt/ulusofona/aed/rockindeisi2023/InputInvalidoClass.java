package pt.ulusofona.aed.rockindeisi2023;

public class InputInvalidoClass {

    String leitura;
    int linhasOK;
    int linhasNOK;
    int primeiraLinhaNOK;

    InputInvalidoClass() {

    }

    @Override
    public String toString() {
        return leitura + " | " + linhasOK + " | " + linhasNOK + " | " + primeiraLinhaNOK;
    }

    public String getLeitura() {
        return leitura;
    }

    public void setLeitura(String leitura) {
        this.leitura = leitura;
    }

    public int getlinhasOK() {
        return linhasOK;
    }

    public void setlinhasOK(int linhasOK) {
        linhasOK = linhasOK;
    }

    public int getLinhasNOK() {
        return linhasNOK;
    }

    public void setLinhasNOK(int linhasNOK) {
        linhasNOK = linhasNOK;
    }

    public int getPrimeiraLinhaNOK() {
        return primeiraLinhaNOK;
    }

    public void setPrimeiraLinhaNOK(int primeiraLinhaNOK) {
        primeiraLinhaNOK = primeiraLinhaNOK;
    }
}
