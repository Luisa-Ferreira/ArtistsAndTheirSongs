package pt.ulusofona.aed.rockindeisi2023;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static ArrayList<InputInvalidoClass> readFileDetails = new ArrayList<>();

    static ArrayList<SongsClass> songs = new ArrayList<>();
    static ArrayList<ArtistsClass> songsArtist = new ArrayList<>();

    public static LinkedHashMap<String, SongsClass> mapaSongs = new LinkedHashMap<>();
    public static LinkedHashMap<String, ArtistsClass> mapaArtists = new LinkedHashMap<>();

    public static boolean ficheiroSong(File folder) {

        int countErradas = 0;
        int linhaOK = 0;
        int primeiralinhaErrada = -1;

        try {

            File leitura = new File(folder, "songs.txt");
            BufferedReader reader = new BufferedReader(new FileReader(leitura));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");

                if (data.length == 3) {
                    String songId = data[0].trim();
                    if (mapaSongs.get(songId) == null) {
                        SongsClass song = new SongsClass(songId, data[1].trim(), Integer.parseInt(data[2].trim()), null, null);

                        mapaSongs.put(song.id, song);
                        linhaOK++;
                    } else {
                        if (primeiralinhaErrada == -1) {
                            primeiralinhaErrada = linhaOK + countErradas + 1;
                        }
                        countErradas++;
                    }
                } else {
                    if (primeiralinhaErrada == -1) {
                        primeiralinhaErrada = linhaOK + countErradas + 1;
                    }
                    countErradas++;
                }
            }

            InputInvalidoClass song_detailsInfo = new InputInvalidoClass("songs.txt", linhaOK, countErradas,
                    primeiralinhaErrada);
            readFileDetails.add(song_detailsInfo);

            reader.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static boolean ficheiroDetails(File folder) {

        int counterradas = 0;
        int linhaOK = 0;
        int primeiralinhaErrada = -1;

        try {

            File leitura = new File(folder, "song_details.txt");
            BufferedReader reader = new BufferedReader(new FileReader(leitura));

            String line;
            while (((line = reader.readLine()) != null)) {

                String[] data = line.split("@");

                if (data.length != 7) {
                    counterradas++;

                    if (primeiralinhaErrada == -1) {
                        primeiralinhaErrada = counterradas + linhaOK;
                    }
                    continue;
                } else {
                    linhaOK++;
                }

                String songId = data[0].trim();
                SongsClass song = mapaSongs.get(songId);
                if (song == null || Integer.parseInt(data[1].trim()) == 0) {
                    continue;
                }


                song.details = new ExtrasClass(songId,
                        Integer.parseInt(data[1].trim()), data[2].trim(), Integer.parseInt(data[3].trim()),
                        Double.parseDouble(data[4].trim()), Double.parseDouble(data[5].trim()), data[6].trim());
            }

            InputInvalidoClass song_detailsInfo = new InputInvalidoClass("song_details.txt", linhaOK, counterradas,
                    primeiralinhaErrada);
            readFileDetails.add(song_detailsInfo);

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean ficheiroArtists(File folder) {
        int counterradas = 0;
        int linhaOK = 0;
        int primeiralinhaErrada = -1;

        try {
            File leitura = new File(folder, "song_artists.txt");
            BufferedReader reader = new BufferedReader(new FileReader(leitura));

            String line;
            while ((line = reader.readLine()) != null) {

                String[] data = line.split("@");
                String id = data[0].trim();
                SongsClass song = mapaSongs.get(id);

                if (data.length != 2 || mapaSongs.get(id) == null) {
                    counterradas++;

                    if (primeiralinhaErrada == -1) {
                        primeiralinhaErrada = linhaOK + counterradas;
                    }
                    continue;
                } else {
                    linhaOK++;
                }

                if (song != null && song.details != null) {

                    ArrayList<String> nomesArtistas = parseMultipleArtists(data[1]);

                    if (nomesArtistas.size() >= 2 && data[1].charAt(0) != '\"' && data[1].charAt(data[1].length() - 1) != '\"') {
                        continue;
                    }

                    for (int i = 0; i <= nomesArtistas.size() - 1; i++) {
                        String nomeArt = nomesArtistas.get(i).trim();
                        if (nomeArt.length() == 0) {
                            continue;
                        }

                        ArtistsClass artist = mapaArtists.get(nomeArt);
                        if (artist == null) {
                            artist = new ArtistsClass(nomeArt, 1, null);
                            mapaArtists.put(nomeArt, artist);
                            artist.musicas.add(song);
                            song.adicionarArtistaAssociado(artist);

                        } else if (!artist.musicas.contains(song)) {
                            artist.numMusicas++;
                            artist.musicas.add(song);
                            song.adicionarArtistaAssociado(artist);
                        }
                    }
                }
            }
            InputInvalidoClass song_detailsInfo = new InputInvalidoClass("song_artists.txt", linhaOK, counterradas,
                    primeiralinhaErrada);
            readFileDetails.add(song_detailsInfo);

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean loadFiles(File folder) {

        readFileDetails = new ArrayList<>();
        songs = new ArrayList<>();
        songsArtist = new ArrayList<>();

        mapaSongs = new LinkedHashMap<>();
        mapaArtists = new LinkedHashMap<>();

        MainFunctions.artistsTags = new HashMap<>();

        if (ficheiroSong(folder) && ficheiroDetails(folder) && ficheiroArtists(folder)) {

            mapaSongs.entrySet().removeIf(entries -> entries.getValue().details == null || entries.getValue().artists.size() == 0);

            for (Map.Entry<String, SongsClass> entry : mapaSongs.entrySet()) {
                SongsClass song = entry.getValue();
                songs.add(song);
            }

            for (Map.Entry<String, ArtistsClass> entry : mapaArtists.entrySet()) {
                ArtistsClass artista = entry.getValue();
                songsArtist.add(artista);
            }

            return true;

        }

        return false;

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
        }

        if (tipo == TipoEntidade.ARTISTA) {
            return songsArtist;
        }

        if (tipo == TipoEntidade.INPUT_INVALIDO) {
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

    public static Query parseCommand(String command) {
        String[] partes = command.split(" ");
        String primeiraposi = partes[0];
        String argstr = "";
        String[] args = null;
        HashSet<String> vervalido = new HashSet<>();
        vervalido.add("EXIT");
        vervalido.add("COUNT_SONGS_YEAR");
        vervalido.add("GET_SONGS_BY_ARTIST");
        vervalido.add("GET_MOST_DANCEABLE");
        vervalido.add("ADD_TAGS");
        vervalido.add("REMOVE_TAGS");
        vervalido.add("GET_ARTISTS_FOR_TAG");
        vervalido.add("GET_ARTIST_RELEASE_IN_YEAR");
        vervalido.add("COUNT_DUPLICATE_SONGS_YEAR");
        vervalido.add("GET_ARTISTS_ONE_SONG");
        vervalido.add("GET_TOP_ARTISTS_WITH_SONGS_BETWEEN");
        vervalido.add("MOST_FREQUENT_WORDS_IN_ARTIST_NAME");
        vervalido.add("GET_UNIQUE_TAGS");
        vervalido.add("GET_UNIQUE_TAGS_IN_BETWEEN_YEARS");
        vervalido.add("GET_RISING_STARS");
        vervalido.add("GET_ARTISTS_WITH_MIN_DURATION");
        vervalido.add("GET_SONG_TITLES_CONSIDERING_WORDS");


        for (int i = 1; i <= partes.length - 1; i++) {

            if (i == partes.length - 1) {
                argstr += partes[i];
            } else {
                argstr += partes[i] + " ";
            }
        }

        if (vervalido.contains(primeiraposi)) {

            args = switch (primeiraposi) {
                case "ADD_TAGS", "REMOVE_TAGS" -> argstr.trim().split(";");
                case "GET_MOST_DANCEABLE", "GET_TOP_ARTISTS_WITH_SONGS_BETWEEN", "MOST_FREQUENT_WORDS_IN_ARTIST_NAME", "GET_RISING_STARS", "GET_SONG_TITLES_CONSIDERING_WORDS" ->
                        argstr.split(" ", 3);
                case "GET_SONGS_BY_ARTIST", "GET_ARTISTS_ONE_SONG", "GET_UNIQUE_TAGS_IN_BETWEEN_YEARS", "GET_ARTISTS_WITH_MIN_DURATION" ->
                        argstr.split(" ", 2);
                case "GET_UNIQUE_TAGS", "EXIT" -> new String[0];
                default -> argstr.split(" ", 1);
            };

            return new Query(primeiraposi, args);
        }
        return null;

    }

    public static ArrayList<String> parseMultipleArtists(String str) {
        ArrayList<String> result = new ArrayList<>();

        String cleanInput = str.trim().substring(1, str.length() - 1);
        Pattern pattern = Pattern.compile("\"\"(.*?)\"\"|'(.*?)'");

        Matcher matcher = pattern.matcher(cleanInput);
        while (matcher.find()) {
            String match = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            result.add(match);
        }
        return result;
    }


    static QueryResult execute(String command) {
        QueryResult resultQuery = new QueryResult();
        Query queryToExec = parseCommand(command);

        if (queryToExec != null && queryToExec.name != null) {
            String valorString = "";
            String valor1String = "";
            String valor2String = "";


            if (queryToExec.args.length == 1) {
                valorString = queryToExec.args[0];
            } else if (queryToExec.args.length == 2) {
                valorString = queryToExec.args[0];
                valor1String = queryToExec.args[1];
            } else if (queryToExec.args.length >= 3) {
                valorString = queryToExec.args[0];
                valor1String = queryToExec.args[1];
                valor2String = queryToExec.args[2];
            }
            switch (queryToExec.name) {

                case "EXIT" -> {
                    return null;
                }

                case "COUNT_SONGS_YEAR" -> resultQuery = MainFunctions.countSongsYear(Integer.parseInt(valorString));
                case "GET_SONGS_BY_ARTIST" ->
                        resultQuery = MainFunctions.getSongsByArtist(Integer.parseInt(valorString), valor1String);
                case "GET_MOST_DANCEABLE" ->
                        resultQuery = MainFunctions.getMostDanceable(Integer.parseInt(valorString), Integer.parseInt(valor1String), Integer.parseInt(valor2String));
                case "ADD_TAGS" -> {
                    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(queryToExec.args).subList(1, queryToExec.args.length));
                    resultQuery = MainFunctions.addTags(valorString, arrayList);
                }
                case "REMOVE_TAGS" -> {
                    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(queryToExec.args).subList(1, queryToExec.args.length));
                    resultQuery = MainFunctions.removeTags(valorString, arrayList);
                }
                case "GET_ARTISTS_FOR_TAG" -> resultQuery = MainFunctions.getArtistsForTags(valorString);
                case "GET_ARTIST_RELEASE_IN_YEAR" ->
                        resultQuery = MainFunctions.getArtistsReleasedInYear(Integer.parseInt(valorString));
                case "COUNT_DUPLICATE_SONGS_YEAR" ->
                        resultQuery = MainFunctions.countDuplicateSongsYear(Integer.parseInt(valorString));
                case "GET_ARTISTS_WITH_MIN_DURATION" ->
                        resultQuery = MainFunctions.getArtistsWithMinDuration(Integer.parseInt(valorString), Integer.parseInt(valor1String));
                case "GET_SONG_TITLES_CONSIDERING_WORDS" ->
                        resultQuery = MainFunctions.getSongTitlesConsideringWords(Integer.parseInt(queryToExec.args[0]), queryToExec.args[1], queryToExec.args[2]);
                case "GET_ARTISTS_ONE_SONG" ->
                        resultQuery = MainFunctions.getArtistsOneSong(Integer.parseInt(valorString), Integer.parseInt(valor1String));
                case "GET_TOP_ARTISTS_WITH_SONGS_BETWEEN" ->
                        resultQuery = MainFunctions.getTopArtistsWithSongsBetween(Integer.parseInt(valorString), Integer.parseInt(valor1String), Integer.parseInt((valor2String)));
                case "MOST_FREQUENT_WORDS_IN_ARTIST_NAME" ->
                        resultQuery = MainFunctions.mostFrenquentWordsInArtistName(Integer.parseInt(valorString), Integer.parseInt(valor1String), Integer.parseInt((valor2String)));
                case "GET_UNIQUE_TAGS" -> resultQuery = MainFunctions.getUniqueTags();
                case "GET_UNIQUE_TAGS_IN_BETWEEN_YEARS" ->
                        resultQuery = MainFunctions.getUniqueTagsInBetweenYears(Integer.parseInt(valorString), Integer.parseInt(valor1String));
                case "GET_RISING_STARS" ->
                        resultQuery = MainFunctions.getRisingStars(Integer.parseInt(valorString), Integer.parseInt(valor1String), valor2String);
                default -> System.out.println("Illegal command. Try again");
            }
            return resultQuery;

        } else {
            System.out.println("Illegal command. Try again");
            return null;
        }
    }


    public static void main(String[] args) {

        File packageFile = new File("PackageLong");
        boolean packageLoaded = loadFiles(packageFile);

        if (!packageLoaded) {
            System.out.println("Error reading files");
            return;
        }
        System.out.println(getObjects(TipoEntidade.ARTISTA));
        System.out.println("Welcome to DEISI Rockstar!");
        String ch;

        do {
            Scanner teclado = new Scanner(System.in);
            String entrada = teclado.nextLine();

            QueryResult result = execute(entrada);

            if (result != null) {
                System.out.print(result.result);
                System.out.println("\n(took " + result.time + " ms)");
                ch = entrada.split(" ")[0];
            }else{
                System.out.println("Adeus.");
                return;
            }

        } while (!ch.equals("EXIT"));
    }
}
