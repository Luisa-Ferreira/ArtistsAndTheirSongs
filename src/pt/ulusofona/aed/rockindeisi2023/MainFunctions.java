package pt.ulusofona.aed.rockindeisi2023;

import java.lang.reflect.Array;
import java.text.Collator;
import java.util.*;

import static pt.ulusofona.aed.rockindeisi2023.Main.*;

public class MainFunctions {
    static String textostr;
    public static HashMap<String, ArrayList<String>> artistsTags = new HashMap<>();

    public static QueryResult countSongsYear(Integer ano) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        int count = 0;
        textostr = "";
        if (ano == null || ano == 0) {
            textostr += "Falta por o ano";
            result.result = textostr;
            return null;
        }


        for (SongsClass song : Main.mapaSongs.values()) {
            if (song.anoLancamento == ano) {
                count++;
            }
        }
        result.result = Integer.toString(count);
        long end = System.currentTimeMillis();
        result.time = end - start;
        return result;
    }

    public static QueryResult getSongsByArtist(Integer numeroresul, String nomeartista) {

        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        textostr = "";

        if (numeroresul == null || nomeartista == null || numeroresul == 0) {
            textostr += "Falta adicionar informação";
            result.result = textostr;
            long end = System.currentTimeMillis();
            result.time = end - start;
            return result;
        }

        int count = 0;

        for (SongsClass song : Main.mapaSongs.values()) {
            for (ArtistsClass artista : song.artists) {
                if (nomeartista.equals(artista.grupoNomes)) {
                    count++;
                    textostr += song.titulo + " : " + song.anoLancamento + "\n";
                    if (count == numeroresul) {
                        result.result = textostr;
                        long end = System.currentTimeMillis();
                        result.time = end - start;
                        return result;
                    }
                }
                break;
            }
        }
        if (textostr.length() == 0) {
            textostr += "No songs";
        }
        result.result = textostr;
        long end = System.currentTimeMillis();
        result.time = end - start;
        return result;

    }

    public static QueryResult getMostDanceable(Integer anoinicio, Integer anofim, Integer numresult) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        StringBuilder textostr = new StringBuilder();

        if (anoinicio == null || anofim == null || numresult == null) {
            textostr = new StringBuilder("Falta adicionar informação");
            result.result = String.valueOf(textostr);
            long end = System.currentTimeMillis();
            result.time = end - start;
            return result;

        } else if (anofim < anoinicio) {
            textostr = new StringBuilder("a ordem dos anos está trocada");
            result.result = String.valueOf(textostr);
            long end = System.currentTimeMillis();
            result.time = end - start;
            return result;
        }
        List<SongsClass> maisdancaveis = new ArrayList<>();

        for (SongsClass song : Main.songs) {
            if (song.anoLancamento >= anoinicio && song.anoLancamento <= anofim) {
                maisdancaveis.add(song);
            }
        }

        if (maisdancaveis.isEmpty()) {
            textostr = new StringBuilder("Não foi encontrado");
            result.result = String.valueOf(textostr);
            long end = System.currentTimeMillis();
            result.time = end - start;
            return result;
        }

        maisdancaveis.sort(Comparator.comparingDouble(songs -> {
            double dancabilidade = songs.getDetails().getDancabilidade();
            return -1 * Math.round(dancabilidade * 1000.0) / 1000.0;
        }));

        if (maisdancaveis.size() < numresult) {
            numresult = maisdancaveis.size();
        }

        for (SongsClass song : maisdancaveis) {
            if (numresult == 0) {
                break;
            }
            textostr.append(song.titulo).append(" : ").append(song.anoLancamento).append(" : ").append(song.getDetails().getDancabilidade()).append("\n");
            numresult--;
        }

        if (textostr.isEmpty()) {
            textostr = new StringBuilder("Não foi encontrado");
            result.result = String.valueOf(textostr);
            long end = System.currentTimeMillis();
            result.time = end - start;
            return result;
        }

        textostr.deleteCharAt(textostr.length() - 1);
        result.result = String.valueOf(textostr);
        long end = System.currentTimeMillis();
        result.time = end - start;
        return result;

    }

    public static QueryResult addTags(String artista, ArrayList<String> tag) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();

        StringBuilder str = new StringBuilder("Inexistent artist");
        String artistaNome = artista.replace("_", " ").trim();

        for (ArtistsClass artistas : songsArtist) {
            if (artistas.grupoNomes.equals(artistaNome)) {
                ArrayList<String> listaDeTags = artistsTags.get(artistaNome);
                if (listaDeTags != null) {
                    for (String strg : tag) {
                        String tagUpperCase = strg.toUpperCase();
                        if (!listaDeTags.contains(tagUpperCase)) {
                            listaDeTags.add(tagUpperCase);
                        }
                    }
                } else {
                    listaDeTags = new ArrayList<>();
                    for (String strg : tag) {
                        listaDeTags.add(strg.toUpperCase());
                    }
                    artistsTags.put(artista, listaDeTags);
                }

                str = new StringBuilder(artistaNome + " | ");
                for (String add : listaDeTags) {
                    str.append(add).append(",");
                }
                str.deleteCharAt(str.length() - 1);
                break;
            }
        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;

        return result;
    }

    public static QueryResult removeTags(String artista, ArrayList<String> tag) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();

        StringBuilder str = new StringBuilder("Inexistent artist");
        String artistaNome = artista.replace("_", " ").trim();

        for (ArtistsClass artistas : songsArtist) {
            if (artistas.grupoNomes.equals(artistaNome)) {
                ArrayList<String> listaDeTags = artistsTags.get(artistaNome);
                if (listaDeTags != null) {
                    for (String tagdelet : tag) {
                        listaDeTags.remove(tagdelet.toUpperCase());
                    }
                    artistsTags.put(artista, listaDeTags);
                    if (listaDeTags.isEmpty()) {
                        str = new StringBuilder(artistaNome + " | No tags");
                    } else {
                        str = new StringBuilder(artistaNome + " | ");
                        for (String arttag : listaDeTags) {
                            str.append(arttag).append(",");
                        }
                        str.deleteCharAt(str.length() - 1);
                    }
                } else {
                    listaDeTags = new ArrayList<>();
                    artistsTags.put(artista, listaDeTags);
                    str = new StringBuilder(artistaNome + " | No tags");
                }
            }
        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;

        return result;
    }

    public static QueryResult getArtistsForTags(String tagDesejada) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();

        StringBuilder str = new StringBuilder();
        tagDesejada = tagDesejada.toUpperCase();

        TreeMap<String, ArrayList<String>> sortedArtistsTags = new TreeMap<>(artistsTags);
        boolean found = false;
        List<String> artistasEncontrados = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> entry : sortedArtistsTags.entrySet()) {

            String artistName = entry.getKey();
            ArrayList<String> tags = entry.getValue();


            for (String tag : tags) {
                if (tag.equals(tagDesejada)) {
                    found = true;
                    artistasEncontrados.add(artistName);
                    break;
                }
            }
        }

        if (!found) {

            str.append("No results");

        } else {

            str.append(String.join(";", artistasEncontrados));

        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;

        return result;
    }

    public static QueryResult getArtistsReleasedInYear(int ano) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();

        textostr = "";
        Set<String> artistasIncluidos = new HashSet<>();

        for (SongsClass song : songs) {
            if (song != null) {
                boolean temMusicasAnteriores = false;
                for (ArtistsClass artist : song.artists) {
                    if (artistasIncluidos.contains(artist.getgrupoNomes())) {
                        temMusicasAnteriores = true;
                        break;
                    }
                }

                if (!temMusicasAnteriores && song.anoLancamento <= ano) {
                    for (ArtistsClass artist : song.artists) {
                        if (song.anoLancamento == ano) {
                            String nomeArtista = artist.getgrupoNomes();
                            String titulo = song.titulo;
                            if (!textostr.isEmpty()) {
                                textostr += "\n";
                            }
                            textostr += nomeArtista + " | " + titulo;
                            artistasIncluidos.add(nomeArtista);
                            break;
                        }
                    }
                }
            }
        }

        if (textostr.isEmpty()) {
            textostr = "Não foi encontrado nada";
        }

        long end = System.currentTimeMillis();
        result.result = textostr;
        result.time = end - start;

        return result;
    }

    public static QueryResult countDuplicateSongsYear(int anolancado) {

        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        int count = 0;

        for (int i = 0; i < Main.songs.size(); i++) {

            SongsClass song = songs.get(i);

            if (song.anoLancamento == anolancado) {

                int anoverf = song.anoLancamento;
                String nome = song.titulo;

                for (int j = Main.songs.size() - 1; j > i; j--) {
                    if (Main.songs.get(j).anoLancamento == anoverf && Main.songs.get(j).titulo.equals(nome)) {
                        count++;
                        break;
                    }
                }

            }

        }

        long end = System.currentTimeMillis();
        result.result = Integer.toString(count);
        result.time = end - start;

        return result;

    }

    public static QueryResult getArtistsWithMinDuration(int ano, int duracao) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        StringBuilder str = new StringBuilder();
        duracao = duracao * 1000;

        for (SongsClass song : Main.mapaSongs.values()) {
            if (song.anoLancamento == ano && song.details.duracao >= duracao) {
                for (ArtistsClass artist : song.artists) {
                    str.append(artist.grupoNomes).append(" | ").append(song.titulo).append(" | ").append(song.details.duracao).append("\n");
                }
            }
        }
        if (str.toString().length() == 0) {
            str = new StringBuilder("No artists");
        } else {
            str.deleteCharAt(str.length() - 1);
        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;
        return result;
    }

    public static QueryResult getSongTitlesConsideringWords(int nMusicas, String include, String exclude) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        StringBuilder str = new StringBuilder();
        int count = 0;

        for (SongsClass song : Main.mapaSongs.values()) {
            boolean contemPalavraAIncluir = false;
            boolean contemPalavraAExcluir = false;
            String[] nomes = song.titulo.split(" ");

            for (String nome : nomes) {
                String verfStr = limpaNome(nome);

                if (verfStr.trim().equalsIgnoreCase(include)) {
                    contemPalavraAIncluir = true;
                }

                if (verfStr.trim().equalsIgnoreCase(exclude)) {
                    contemPalavraAExcluir = true;
                }
            }

            if (contemPalavraAIncluir && !contemPalavraAExcluir) {
                str.append(song.titulo).append("\n");
                count++;
            }
            if (count == nMusicas) {
                break;
            }
        }

        if (str.toString().isEmpty()) {
            str = new StringBuilder("No results");
        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;
        return result;
    }

    public static String limpaNome(String nome) {
        String trimnome = nome.trim();
        if (trimnome.startsWith("(") || trimnome.startsWith("'") || trimnome.startsWith("\"") || trimnome.startsWith(",")) {
            trimnome = trimnome.substring(1);
        }

        if (trimnome.endsWith(")") || trimnome.endsWith("\"") || trimnome.endsWith("'") || trimnome.endsWith(",")) {
            trimnome = trimnome.substring(0, trimnome.length() - 1);
        }

        return trimnome;
    }

    //não obrigatórias abaixo

    public static QueryResult getArtistsOneSong(int anoInit, int anoEnd) {
        long start = System.currentTimeMillis();

        QueryResult result = new QueryResult();
        StringBuilder str = new StringBuilder();

        if (anoEnd <= anoInit) {
            str = new StringBuilder("Invalid period");
            long end = System.currentTimeMillis();
            result.result = str.toString();
            result.time = end - start;
            return result;
        }

        TreeMap<String, SongsClass> artistSort = new TreeMap<>();

        ArrayList<String> artistHasMoreThanOneSong = new ArrayList<>();

        for (SongsClass song : mapaSongs.values()) {

            if (song != null && song.anoLancamento >= anoInit && song.anoLancamento <= anoEnd) {

                for (ArtistsClass artist : song.artists) {
                    String artistName = artist.grupoNomes;
                    if (!artistSort.containsKey(artistName) && !artistHasMoreThanOneSong.contains(artistName)) {
                        artistSort.put(artistName, song);
                    } else {
                        artistSort.remove(artistName);
                        artistHasMoreThanOneSong.add(artistName);
                    }
                }
            }
        }

        for (Map.Entry<String, SongsClass> entry : artistSort.entrySet()) {
            str.append(entry.getKey()).append(" | ").append(entry.getValue().titulo).append(" | ").append(entry.getValue().anoLancamento).append("\n");
        }

        if (!(str.toString().length() == 0)) {
            str.deleteCharAt(str.length() - 1);
        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;
        return result;
    }

    public static QueryResult getTopArtistsWithSongsBetween(int num_resultados, int min, int max) {
        long start = System.currentTimeMillis();
        StringBuilder musicas = new StringBuilder();
        QueryResult result = new QueryResult();

        int count = 0;
        LinkedHashMap<String, Integer> sortedArtistas = new LinkedHashMap<>();
        ArrayList<String> sortedKeys = new ArrayList<>();

        for (ArtistsClass entry : mapaArtists.values()) {
            int numMusicas = entry.numMusicas;
            if (numMusicas >= min && numMusicas <= max) {
                String key = entry.grupoNomes;
                sortedArtistas.put(key, numMusicas);
                sortedKeys.add(key);
            }
        }

        sortedKeys.sort((key1, key2) -> {
            Integer value1 = sortedArtistas.get(key1);
            Integer value2 = sortedArtistas.get(key2);
            return value2.compareTo(value1);
        });

        for (String key : sortedKeys) {
            count++;
            if (count <= num_resultados) {
                musicas.append(key).append(" ").append(sortedArtistas.get(key)).append("\n");
            } else {
                break;
            }
        }

        if (count == 0) {
            musicas.append("No results");
        } else {
            musicas.deleteCharAt(musicas.length() - 1);
        }

        String resultado = musicas.toString();
        long end = System.currentTimeMillis();
        result.result = resultado;
        result.time = end - start;
        return result;
    }

    public static QueryResult mostFrenquentWordsInArtistName(int nomesrepe, int temasmusicais, int tamanhodonome) {

        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        StringBuilder str = new StringBuilder();
        int quantas = 0;

        LinkedHashMap<String, Integer> sortedArtistas = new LinkedHashMap<>();

        for (ArtistsClass entry : Main.mapaArtists.values()) {
            String[] compara = entry.grupoNomes.split(" ");

            if (entry.numMusicas >= temasmusicais) {
                for (String nome : compara) {
                    if (nome.length() >= tamanhodonome) {
                        sortedArtistas.put(nome, sortedArtistas.getOrDefault(nome, 0) + 1);

                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedNames = new ArrayList<>(sortedArtistas.entrySet());
        sortedNames.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> entry : sortedNames) {
            String artistName = entry.getKey();
            int artistCount = entry.getValue();
            if (quantas < nomesrepe) {
                String reuslt = artistName + " " + artistCount + "\n";
                str.insert(0, reuslt);
            } else {
                break;
            }
            quantas++;
        }

        if (!str.isEmpty()) {
            str.deleteCharAt(str.length() - 1);
        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;
        return result;
    }

    public static QueryResult getUniqueTags() {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        StringBuilder str = new StringBuilder();
        HashMap<String, Integer> nDeTags = new HashMap<>();


        for (ArrayList<String> arr : artistsTags.values()) {
            for (String tag : arr) {
                if (nDeTags.get(tag) == null || nDeTags.get(tag) == 0) {
                    nDeTags.put(tag, 1);
                } else {
                    int countTag = nDeTags.get(tag);
                    countTag++;
                    nDeTags.put(tag, countTag);
                }
            }
        }

        ArrayList<Map.Entry<String, Integer>> sortTags = new ArrayList<>(nDeTags.entrySet());
        sortTags.sort(Map.Entry.comparingByValue());

        for (Map.Entry<String, Integer> entry : sortTags) {
            String tagName = entry.getKey();
            str.append(tagName).append(" ").append(nDeTags.get(tagName)).append("\n");
        }

        if (str.isEmpty()) {
            str = new StringBuilder("No results");
        } else {
            str.deleteCharAt(str.length() - 1);
        }


        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;
        return result;
    }

    public static QueryResult getUniqueTagsInBetweenYears(int ano1, int ano2) {
        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        StringBuilder str = new StringBuilder();
        HashMap<String, Integer> nTagsEntre = new HashMap<>();

        for (String artista : artistsTags.keySet()) {
            ArrayList<String> tags = artistsTags.get(artista);
            ArtistsClass artistVerf = Main.mapaArtists.get(artista);
            for (SongsClass song : artistVerf.musicas) {
                if (song.anoLancamento >= ano1 && song.anoLancamento <= ano2) {
                    for (String tag : tags) {
                        nTagsEntre.put(tag, nTagsEntre.getOrDefault(tag, 0) + 1);
                        ;
                    }
                    break;
                }
            }
        }

        ArrayList<Map.Entry<String, Integer>> sortTags = new ArrayList<>(nTagsEntre.entrySet());
        sortTags.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> entry : sortTags) {
            String tagName = entry.getKey();
            int tagCount = entry.getValue();
            str.append(tagName).append(" ").append(tagCount).append("\n");
        }

        if (str.isEmpty()) {
            str = new StringBuilder("No results");
        } else {
            str.deleteCharAt(str.length() - 1);
        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;
        return result;
    }

    public static QueryResult getRisingStars(int ano1, int ano2, String ordenacao) {

        long start = System.currentTimeMillis();
        QueryResult result = new QueryResult();
        StringBuilder str = new StringBuilder();
        HashMap<String, ArtistsClass> musicacomanostodos = new HashMap<>();
        TreeMap<Integer, ArrayList<SongsClass>> ocupatodososanos = new TreeMap<>();
        TreeMap<String, TreeMap<Integer, ArrayList<SongsClass>>> artistData = new TreeMap<>();

        for (ArtistsClass artist : mapaArtists.values()) {
            ocupatodososanos = new TreeMap<>();
            boolean anolancaincorreto = false;
            boolean naoexiste = false;

            for (SongsClass musicas : artist.musicas) {

                int anolancamento = musicas.anoLancamento;

                if (anolancamento >= ano1 && anolancamento <= ano2) {
                    ArrayList<SongsClass> musicasAno = ocupatodososanos.get(anolancamento);

                    if (musicasAno == null) {
                        musicasAno = new ArrayList<>();
                        ocupatodososanos.put(anolancamento, musicasAno);
                    }
                    musicasAno.add(musicas);
                }

            }

            int anoatual = ano1;
            while (anoatual <= ano2) {
                if (ocupatodososanos.get(anoatual) == null) {
                    naoexiste = true;

                    //este artista nao tem musicas para todos os anos
                    break;
                }
                anoatual++;
            }


            if (!naoexiste) {
                // ha musicas do artista em todos os anos

                ArrayList<Double> anolancamedia = new ArrayList<>();

                double anolancamediaconta = 0;
                //array todas as musicas por cada ano
                for (ArrayList<SongsClass> anosinfo : ocupatodososanos.values()) {
                    //array de musicas do ano
                    anolancamediaconta = 0;
                    for (SongsClass musicasanorep : anosinfo) {
                        anolancamediaconta += musicasanorep.details.popularidade;

                    }

                    anolancamedia.add(anolancamediaconta / anosinfo.size());
                }
                double anolancamediacontafinal = 0;

                for (int i = 0; i < anolancamedia.size(); i++) {
                    anolancamediacontafinal += anolancamedia.get(i);

                    if (!(i == anolancamedia.size() - 1)) {
                        if (anolancamedia.get(i) > anolancamedia.get(i + 1)) {
                            anolancaincorreto = true;
                            break;
                        }
                    } else {
                        anolancamediacontafinal += anolancamedia.get(i);
                    }
                }

                if (anolancaincorreto) {
                    continue;
                }

                if (anolancamediacontafinal == 0) {
                    artist.popularidademedia = 0;
                } else {
                    artist.popularidademedia = (anolancamediacontafinal / anolancamedia.size());
                }

                System.out.println(artist.popularidademedia);

                // as medias dos popularidade nao vao aumentando consecutivamente

                musicacomanostodos.put(artist.grupoNomes, artist);
                artistData.put(artist.grupoNomes, ocupatodososanos);
            }

        }


        if (ordenacao.equals("ASC")) {
            List<ArtistsClass> sortedValues = new ArrayList<>(musicacomanostodos.values());
            Collections.sort(sortedValues, new Comparator<ArtistsClass>() {
                public int compare(ArtistsClass a1, ArtistsClass a2) {
                    int result = Integer.compare((int) a1.popularidademedia, (int) a2.popularidademedia);
                    if (result == 0) {
                        result = a1.grupoNomes.compareTo(a2.grupoNomes);
                    }
                    return result;
                }
            });

            int count = 0;

            for (ArtistsClass artistas : sortedValues) {
                count++;
                if (count <= 15) {
                   System.out.println(artistas.grupoNomes + " - " + artistas.popularidademedia);
                    str.append(artistas.grupoNomes).append(" <=> ").append(artistas.popularidademedia).append("\n");
                }
            }

        } else if (ordenacao.equals("DESC")) {

            List<ArtistsClass> sortedValues = new ArrayList<>(musicacomanostodos.values());
            Collections.sort(sortedValues, new Comparator<ArtistsClass>() {
                public int compare(ArtistsClass a1, ArtistsClass a2) {
                    int result = Integer.compare((int) a2.popularidademedia, (int) a1.popularidademedia);
                    if (result == 0) {
                        result = a1.grupoNomes.compareTo(a2.grupoNomes);
                    }
                    return result;
                }
            });

            int count = 0;

            for (ArtistsClass artistas : sortedValues) {
                count++;
                if (count <= 15) {
//                    System.out.println(artistas.grupoNomes + " - " + artistas.popularidade);
                    str.append(artistas.grupoNomes).append(" <=> ").append(artistas.popularidademedia).append("\n");
                }
            }
        }

        if (str.isEmpty()) {
            str = new StringBuilder("No results");
        } else {
            str.deleteCharAt(str.length() - 1);
        }

        long end = System.currentTimeMillis();
        result.result = str.toString();
        result.time = end - start;
        return result;
    }

}
