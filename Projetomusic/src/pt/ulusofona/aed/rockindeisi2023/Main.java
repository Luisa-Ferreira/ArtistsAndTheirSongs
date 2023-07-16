package pt.ulusofona.aed.rockindeisi2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static String line;
    static ArrayList<InputInvalidoClass> readFileDetails = new ArrayList<>();
    static ArrayList<SongsClass> songs = new ArrayList<>();
    static ArrayList<ArtistsClass> songsArtist = new ArrayList<>();
    static ArrayList<ExtrasClass> songDetails = new ArrayList<>();


    static boolean skip = false;

    public static boolean loadFiles(File folder) {
        line = "";
        readFileDetails = new ArrayList<>();
        songs = new ArrayList<>();
        songsArtist = new ArrayList<>();
        songDetails = new ArrayList<>();

        boolean leubem = true;
        int count = 0;
        int counterradas = 0, linhaOK = 0;
        int primeiralinhaErrada = 0;

        try {
            skip = false;
            InputInvalidoClass songLaodInfo = new InputInvalidoClass();
            File leitura = new File(folder, "songs.txt");
            Scanner scanner = new Scanner(leitura);
            ArrayList<String> musicIds = new ArrayList<>();

            while (scanner.hasNext()) {
                line = scanner.nextLine();
                String[] data = line.split("@");
                count++;

                if (line.split("@").length != 3 || musicIds.contains(data[0])) {
                    // qual a primeira linha com erro
                    counterradas++;

                    if (!skip) {
                        skip = true;
                        primeiralinhaErrada = count;
                    }
                    continue;
                } else {
                    musicIds.add(data[0]);
                    linhaOK++;
                }

                SongsClass song = new SongsClass();
                song.setId(data[0].trim());
                song.setTitulo(data[1].trim());
                song.setAnoLancamento(Integer.parseInt(data[2].trim()));
                songs.add(song);
            }

            if (primeiralinhaErrada == 0) {
                primeiralinhaErrada = -1;
            }

            songLaodInfo.leitura = "songs.txt";
            songLaodInfo.linhasNOK = counterradas;
            songLaodInfo.linhasOK = linhaOK;
            songLaodInfo.primeiraLinhaNOK = primeiralinhaErrada;
            readFileDetails.add(songLaodInfo);

        } catch (FileNotFoundException e) {
            leubem = false;
            return leubem;
        }

        try {
            skip = false;
            linhaOK = 0;
            counterradas = 0;
            primeiralinhaErrada = 0;
            count = 0;
            InputInvalidoClass song_detailsInfo = new InputInvalidoClass();
            File leitura = new File(folder, "song_details.txt");
            Scanner scanner = new Scanner(leitura);

            while (scanner.hasNext()) {
                line = scanner.nextLine();

                String[] data = line.split("@");

                count++;

                if (line.split("@").length != 7) {

//                    System.out.println("line wrong");
                    counterradas++;

                    if (!skip) {
                        skip = true;
                        primeiralinhaErrada = count;
                    }
                    continue;
                } else {
                    linhaOK++;
                }

                for (SongsClass s : songs) {
                    if (Objects.equals(s.id, data[0].trim())) {
                        ExtrasClass songsDetails = new ExtrasClass();
                        songsDetails.setid(data[0].trim());
                        songsDetails.setduracao(Integer.parseInt(data[1].trim()));
                        songsDetails.setletra(data[2].trim());
                        songsDetails.setpopularidade(Integer.parseInt(data[3].trim()));
                        songsDetails.setdancabilidade(Double.parseDouble(data[4].trim()));
                        songsDetails.setvivacidade(Double.parseDouble(data[5].trim()));
                        songsDetails.setvolume(data[6].trim());
                        songDetails.add(songsDetails);

                        s.details = songsDetails;
                    }
                }
            }

            if (primeiralinhaErrada == 0) {
                primeiralinhaErrada = -1;
            }

            song_detailsInfo.leitura = "song_details.txt";
            song_detailsInfo.linhasNOK = counterradas;
            song_detailsInfo.linhasOK = linhaOK;
            song_detailsInfo.primeiraLinhaNOK = primeiralinhaErrada;
            readFileDetails.add(song_detailsInfo);

        } catch (FileNotFoundException e) {
            leubem = false;
            return leubem;
        }

        try {
            skip = false;
            linhaOK = 0;
            counterradas = 0;
            primeiralinhaErrada = 0;
            count = 0;
            InputInvalidoClass song_artistsInfo = new InputInvalidoClass();

            File leitura = new File(folder, "song_artists.txt");
            Scanner scanner = new Scanner(leitura);

            while (scanner.hasNext()) {
                count++;
                line = scanner.nextLine();


                String[] data = line.split("@");


                if (line.split("@").length != 2) {

                    System.out.println("line wrong");

                    counterradas++;

                    if (!skip) {
                        skip = true;
                        primeiralinhaErrada = count;
                    }
                    continue;
                } else {
                    linhaOK++;
                }

                String[] nomes = data[1].split(",");

                if (nomes.length >= 2 && data[1].charAt(0) != '\"' && data[1].charAt(data[1].length() - 1) != '\"') {
                    continue;
                }

                for (SongsClass s : songs) {
                    for (ExtrasClass j : songDetails) {
                        if (Objects.equals(j.id, data[0].trim())) {
                            if (Objects.equals(s.id, data[0].trim())) {
                                ArrayList<String> nomesArts = new ArrayList<>();


                                for (int i = 0; i <= nomes.length - 1; i++) {
                                    String nomeArt = nomes[i].trim().replace("[", "").replace("'", "").replace("\"", "").replace("]", "");
                                    boolean found = false;
                                    for (ArtistsClass artist : songsArtist) { // por que dois for's
                                        boolean skip = false;
                                        if (Objects.equals(artist.getGrupoNomes(), nomeArt)) {
                                            found = true;
                                            break;

                                        }
                                    }

                                    if (!found) {
                                        ArtistsClass songsartists = new ArtistsClass();
                                        songsartists.setGrupoNomes(nomeArt);
                                        songsArtist.add(songsartists);
                                    }
                                    s.adicionarArtistaAssociado(nomeArt);
                                }
                            }
                        }
                }
                }

            }

            for (ArtistsClass artist : songsArtist) {
                String names = artist.getgrupoNomes();
                int numMusicas = 0;
                ArrayList<String> musicIds = new ArrayList<>();
                for (SongsClass song : songs) {
                    for (String artistMusica : song.artists) {
                        if (Objects.equals(names, artistMusica) && !musicIds.contains(song.id)) {
                            numMusicas++;
                            musicIds.add(song.id);
                        }
                    }
                }
                artist.numbMusicas = numMusicas;
            }

            if (primeiralinhaErrada == 0) {
                primeiralinhaErrada = -1;
            }


            song_artistsInfo.leitura = "song_artists.txt";
            song_artistsInfo.linhasNOK = counterradas;
            song_artistsInfo.linhasOK = linhaOK;
            song_artistsInfo.primeiraLinhaNOK = primeiralinhaErrada;
            readFileDetails.add(song_artistsInfo);

        } catch (FileNotFoundException e) {
            leubem = false;
            return leubem;
        }


        songs.removeIf(song -> song.details == null);
        songs.removeIf(song -> song.artists == null);


        // Código para remover ids de musica repetidos
        for (int i = 0; i < songs.size() - 1; i++) {
            SongsClass song = songs.get(i);
            for (int j = songs.size() - 1; j >= 0; j--) {
                SongsClass songRemove = songs.get(j);
                if (Objects.equals(song.getId(), songRemove.getId()) && i != j) {
                    songs.remove(j);
                }
            }


        }

        return leubem;
    }

    public static boolean isValid(String line) {
        String[] arr = line.split(",");
        boolean estaCerto = false;
        if (arr.length >= 2) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].charAt(0) == '\"' && arr[i].charAt(1) == '[' && arr[i].charAt(3) == '\'' && i == 0) {
                    estaCerto = true;
                } else if (arr[i].charAt(arr[i].length() - 1) == '\"' && arr[i].charAt(arr[i].length() - 2) == ']' && arr[i].charAt(arr[i].length() - 3) == '\'' && i == arr.length - 1) {
                    estaCerto = true;
                }
            }
        } else if (arr.length == 1) {
            if (arr[0].charAt(0) == '[' && arr[0].charAt(1) == '\'' && arr[0].charAt(arr[0].length() - 1) == ']' && arr[0].charAt(arr[0].length() - 2) == '\'') {
                estaCerto = true;
            }
        }
        return estaCerto;
    }

    public static ArrayList getObjects(TipoEntidade tipo) {
        if (tipo == TipoEntidade.TEMA) {
            return songs;
        } else if (tipo == TipoEntidade.ARTISTA) {
            return songsArtist;
        } else if (tipo == TipoEntidade.INPUT_INVALIDO) {
            return readFileDetails;
        }
        return null;
    }

    public static int countMusicBeforeYear(ArrayList<SongsClass> lista, int ano) {
        int anoscontados = 0;
        for (SongsClass songs : lista) {
            if (ano == 1995 && songs.anoLancamento < 1995) {
                anoscontados++;
            } else if (ano == 2000 && songs.anoLancamento >= 1995 && songs.anoLancamento < 2000) {
                anoscontados++;
            } else if (ano >= 2001 && songs.anoLancamento >= 2000) {
                anoscontados++;
            }
        }
        return anoscontados;
    }


    public static void main(String[] args) {

        File songBla = new File("Package");
        boolean songBlaBla = loadFiles(songBla);
        boolean songBlaBla2 = loadFiles(songBla);
        boolean songBlaBla3 = loadFiles(songBla);

        if (!songBlaBla || !songBlaBla2 || !songBlaBla3) {
            System.out.println("File not found");
        }
        System.out.println(getObjects(TipoEntidade.ARTISTA).toString()+"\n");
        System.out.println();
    }
}
