package pt.ulusofona.aed.rockindeisi2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain {

    @Test
    public void stringSongMenor1995() {
        ArrayList<ArtistsClass> arrayartists = new ArrayList<>();
        ArtistsClass artist = new ArtistsClass("Marco");
        arrayartists.add(artist);
        ExtrasClass details = new ExtrasClass("123", 347951, "0", 2, 0.2, 2.2, "awda");
        SongsClass song = new SongsClass("123", "Mamamia", 1965, details, arrayartists);
        String str = "123 | Mamamia | 1965";

        assertEquals(str, song.toString());


    }

    @Test
    public void stringArtistas() {
        ArtistsClass artista = new ArtistsClass("João Maria", 2, null);
        String str = "Artista: [João Maria] | 2";


        assertEquals(str, artista.toString());
    }


    @Test
    public void stringSongMenor2000() {
        ExtrasClass details = new ExtrasClass("123", 347951, "0", 2, 0.2, 2.2, "awda");
        ArrayList<ArtistsClass> artistarray = new ArrayList<>();
        ArtistsClass artist = new ArtistsClass("Marco");
        artistarray.add(artist);
        SongsClass song = new SongsClass("123", "Mamamia", 1999, details, artistarray);
        String str = "123 | Mamamia | 1999 | 5:47 | 2";

        assertEquals(str, song.toString());
    }

    @Test
    public void ficheiroSemErros() {

        File folder = new File("FicheirosDeTest");
        try {
            folder.mkdirs();
            File file1 = new File(folder, "songs.txt");
            File file2 = new File(folder, "song_artists.txt");
            File file3 = new File(folder, "song_details.txt");

            FileWriter fw1 = new FileWriter(file1);
            FileWriter fw2 = new FileWriter(file2);
            FileWriter fw3 = new FileWriter(file3);

            BufferedWriter bw1 = new BufferedWriter(fw1);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            BufferedWriter bw3 = new BufferedWriter(fw3);

            bw1.write("7dD5DEzjhofoItSG7QwVoY @ Shiver @ 2012");
            bw2.write("7dD5DEzjhofoItSG7QwVoY @ ['Frank Sinatra']");
            bw3.write("7dD5DEzjhofoItSG7QwVoY @ 167624 @ 0 @ 32 @ 0.5770033333333331 @ 0.223 @ -9.812");

            bw1.newLine();
            bw2.newLine();
            bw3.newLine();

            bw1.write("5TnAnWRXDM6awfvAveK91N @ Crazy Kids @ 1963");
            bw2.write("5TnAnWRXDM6awfvAveK91N @ ['Mountain John']");
            bw3.write("5TnAnWRXDM6awfvAveK91N @ 205613 @ 0 @ 34 @ 0.608 @ 0.115 @ -10.265999999999998");

            bw1.close();
            fw1.close();
            bw2.close();
            fw2.close();
            bw3.close();
            fw3.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean expected = true;
        Assertions.assertTrue(Main.loadFiles(folder));
    }

    @Test
    public void ficheirosComErros() {

        File folder = new File("FicheirosDeTest");
        try {
            folder.mkdirs();
            File file1 = new File(folder, "songs.txt");
            File file2 = new File(folder, "song_artists.txt");
            File file3 = new File(folder, "song_details.txt");

            FileWriter fw1 = new FileWriter(file1);
            FileWriter fw2 = new FileWriter(file2);
            FileWriter fw3 = new FileWriter(file3);

            BufferedWriter bw1 = new BufferedWriter(fw1);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            BufferedWriter bw3 = new BufferedWriter(fw3);

            bw1.write("7dD5DEzjhofoItSG7QwVoY @ Shiver @ 2012");
            bw2.write("7dD5DEzjhofoItSG7QwVoY @ ['Frank Sinatra']");
            bw3.write("7dD5DEzjhofoItSG7QwVoY @ 167624 @ 0 @ 32 @ 0.5770033333333331 @ 0.223 @ -9.812");

            bw1.newLine();
            bw2.newLine();
            bw3.newLine();

            bw1.write("5TnAnWRXDM6awfvAveK91N @ Crazy Kids @ 1963");
            bw2.write("5TnAnWRXDM6awfvAveK91N @ ['Mountain John'] @ 392432");
            bw3.write("5TnAnWRXDM6awfvAveK91N @ 205613 @ 0 @ 34 @ 0.608 @ 0.115 @ -10.265999999999998");

            bw1.newLine();
            bw2.newLine();
            bw3.newLine();

            bw1.write("5TnAnWFESM6awfvAveK91N @ Crazy Kids ");
            bw2.write("5TnQERQ23A6awfvAveK91N @ ['Mountain John']");
            bw3.write("5TwcWfwAw1dgSw45veVuTA @  @ -10.265999999999998");


            bw1.close();
            fw1.close();
            bw2.close();
            fw2.close();
            bw3.close();
            fw3.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean expected = true;
        Assertions.assertTrue(Main.loadFiles(folder));
    }

    @Test
    public void countSongsYear() {
        File folder = new File("test-files");
        Main.loadFiles(folder);
        QueryResult queryResult = Main.execute("COUNT_SONGS_YEAR 1991");
        Assertions.assertNotNull(queryResult);
        Assertions.assertNotNull(queryResult.result);
        Assertions.assertEquals(3, Integer.parseInt(queryResult.result));
        System.out.println(Integer.parseInt(queryResult.result));
    }

    @Test
    public void GetSongsByArtist() {
        File folder = new File("test-files");
        Main.loadFiles(folder);
        QueryResult resultado = Main.execute("GET_SONGS_BY_ARTIST 2 Queen");
        String mensagemErro = "song1 : 1991\nsong3 : 1990\n";

        assertEquals(mensagemErro, resultado.result);
    }

    @Test
    public void GetMostDanceable() {
        File folder = new File("test-files");
        Main.loadFiles(folder);
        QueryResult resultado = Main.execute("GET_MOST_DANCEABLE 2022 2020 10");
        String mensagemErro = "a ordem dos anos está trocada";
        assertEquals(mensagemErro, resultado.result);
    }

    @Test
    public void getArtistsReleasedInYear() {
        File folder = new File("test-files");
        Main.loadFiles(folder);
        QueryResult queryResult = Main.execute("GET_ARTIST_RELEASE_IN_YEAR 1991");
        String expectedResult = "Mountain John | Crazy Kids\nQueen | song1\nRage Against The Machine | song4";
        assert queryResult != null;
        Assertions.assertEquals(expectedResult, queryResult.result);
    }

    @Test
    public void getArtistsForTags() {
        File folder = new File("test-files");
        Main.loadFiles(folder);
        MainFunctions.artistsTags = new HashMap<>();
        Main.execute("ADD_TAGS Radiohead;Nirvana");
        QueryResult queryResult = Main.execute("GET_ARTISTS_FOR_TAG Nirvana");
        String expectedResult = "Radiohead";
        assert queryResult != null;
        Assertions.assertEquals(expectedResult, queryResult.result);
    }

    @Test
    public void getArtistsForTags2() {
        File folder = new File("test-files");
        Main.loadFiles(folder);
        MainFunctions.artistsTags = new HashMap<>();
        Main.execute("ADD_TAGS Nirvana;Radiohead");
        Main.execute("ADD_TAGS Queen;Breakupwithyourgirlfriend;Radiohead");
        QueryResult queryResult = Main.execute("GET_ARTISTS_FOR_TAG Radiohead");
        String expectedResult = "Nirvana;Queen";
        assert queryResult != null;
        Assertions.assertEquals(expectedResult, queryResult.result);
    }

    @Test
    public void REMOVE_TAGS_Test_OGB() {
        File folder = new File("test-files/Test");
        Main.loadFiles(folder);
        QueryResult queryResult = Main.execute("ADD_TAGS Frank Sinatra;anao;bacalhau");
        Assertions.assertNotNull(queryResult);
        Assertions.assertNotNull(queryResult.result);
        QueryResult queryRevome = Main.execute("REMOVE_TAGS Frank Sinatra;anao");
        Assertions.assertNotNull(queryRevome);
        Assertions.assertNotNull(queryRevome.result);
        String[] reslutParts = queryRevome.result.split("\\|");
        Assertions.assertEquals(2, reslutParts.length);
        String expectedResult = "Frank Sinatra | BACALHAU";
        String atualResualt = queryRevome.result;
        Assertions.assertEquals(expectedResult, atualResualt);
        queryRevome = Main.execute("REMOVE_TAGS Frank Sinatra;bacalhau");
        expectedResult = "Frank Sinatra | No tags";
        Assertions.assertNotNull(queryRevome);
        Assertions.assertNotNull(queryRevome.result);
        atualResualt = queryRevome.result;
        Assertions.assertEquals(expectedResult, atualResualt);
    }

    @Test
    public void Add_TAGS_Test_OGB() {
        File folder = new File("test-files");
        Main.loadFiles(folder);
        QueryResult queryResult = Main.execute("ADD_TAGS Queen;anao;bacalhau");
        Assertions.assertNotNull(queryResult);
        Assertions.assertNotNull(queryResult.result);
        String[] reslutParts = queryResult.result.split("\\|");
        Assertions.assertEquals(2, reslutParts.length);
        String[] resultParts = reslutParts[1].split(",");
        Assertions.assertEquals(2, resultParts.length);
        String expectedResult = "Queen | ANAO,BACALHAU";
        String atualResualt = queryResult.result;
        Assertions.assertEquals(expectedResult, atualResualt);
    }

    @Test
    public void getArtistsForTagsTest() {
        File folder = new File("test-files/Test");
        Main.loadFiles(folder);
        QueryResult queryResult = Main.execute("ADD_TAGS Frank Sinatra;anao;bacalhau;fixe");
        QueryResult queryResult1 = Main.execute("ADD_TAGS Queen;anao;bacalhau;coiso");
        QueryResult queryResult2 = Main.execute("ADD_TAGS Nirvana;anao;bacalhau;bonito");
        Assertions.assertNotNull(queryResult);
        Assertions.assertNotNull(queryResult.result);
        Assertions.assertNotNull(queryResult1);
        Assertions.assertNotNull(queryResult1.result);
        Assertions.assertNotNull(queryResult2);
        Assertions.assertNotNull(queryResult2.result);
        QueryResult queryResult3 = Main.execute("GET_ARTISTS_FOR_TAG fixe");
        Assertions.assertNotNull(queryResult3);
        Assertions.assertNotNull(queryResult3.result);
        Assertions.assertEquals(queryResult3.result, "Frank Sinatra");
        QueryResult queryResult4 = Main.execute("GET_ARTISTS_FOR_TAG anao");
        Assertions.assertNotNull(queryResult4);
        Assertions.assertNotNull(queryResult4.result);
        Assertions.assertEquals("Frank Sinatra;Nirvana;Queen", queryResult4.result);
    }

    @Test
    public void getArtistsWithMinDuration() {
        File folder = new File("test-files");
        Main.loadFiles(folder);
        QueryResult queryResult = Main.execute("GET_ARTISTS_WITH_MIN_DURATION 1991 0002");

        String input = "Mountain John | Crazy Kids | 205613\n" +
                "Queen | song1 | 349467\n"+
                "Rage Against The Machine | song4 | 983457\n"+
                "Nirvana | song4 | 983457";

        assert queryResult != null;
        assertEquals(input,queryResult.result);
    }

    @Test
    public void getSongTitlesConsideringWords() {
        File folder = new File("test-files/getSongTitlesTest");
        Main.loadFiles(folder);
        QueryResult queryResult = Main.execute("GET_SONG_TITLES_CONSIDERING_WORDS 3 song0 adeus");
        Assertions.assertNotNull(queryResult);
        Assertions.assertNotNull(queryResult.result);
        String expected = "song0\nsong0 ola\nsong0 mais\n";
        Assertions.assertEquals(expected, queryResult.result);
    }

    // não obrigatório

    @Test
    public void getArtistsOneSong() {
        File folder = new File("test-files/Test");
        Main.loadFiles(folder);


        QueryResult queryResult = Main.execute("GET_ARTISTS_ONE_SONG 2000 2003");
        String expectedResult = "Nirvana | song5 | 2001\n" +
                "Queen | song5 | 2001";

        assert queryResult != null;
        Assertions.assertEquals(expectedResult, queryResult.result);
    }

    @Test
    public void getTopArtistsWithSongsBetween() {
        File folder = new File("test-files/GET_TOP_ARTISTS_WITH_SONGS_BETWEEN");
        Main.loadFiles(folder);

        QueryResult queryResult = Main.execute("GET_TOP_ARTISTS_WITH_SONGS_BETWEEN 5 0 2");
        String expectedResult = "Frank Sinatra 2\n" +
                "Rage Against The Machine 1\n" +
                "Mountain John 1";

        assert queryResult != null;
        Assertions.assertEquals(expectedResult, queryResult.result);
    }

    @Test
    public void mostFrenquentWordsInArtistName() {
        File folder = new File("test-files/mostFrenquentWordsInArtistName");
        Main.loadFiles(folder);

        QueryResult queryResult = Main.execute("MOST_FREQUENT_WORDS_IN_ARTIST_NAME 4 0 0");
        String expectedResult = "Mariss 1\n"+
        "Stolz 1\n"+
        "Robert 1\n"+
        "Philharmoniker 2";

        assert queryResult != null;
        Assertions.assertEquals(expectedResult, queryResult.result);
    }

    @Test
    public void getUniqueTags() {
        File folder = new File("test-files");
        Main.loadFiles(folder);

        QueryResult queryResult = Main.execute("ADD_TAGS Queen;anao");
        assert queryResult != null;
        QueryResult queryResult2 = Main.execute("GET_UNIQUE_TAGS");
        String expectedResult = "ANAO 1";

        Assertions.assertNotNull(queryResult2);
        System.out.println(queryResult2.result);
        Assertions.assertEquals(expectedResult, queryResult2.result);
    }

    @Test
    public void getUniqueTagsInBetweenYears() {
        File folder = new File("test-files/Test");
        Main.loadFiles(folder);

        QueryResult queryResult0 = Main.execute("ADD_TAGS Frank Sinatra;anao;bacalhau;fixe");
        QueryResult queryResult1 = Main.execute("ADD_TAGS Queen;anao;bacalhau;coiso");
        QueryResult queryResult2 = Main.execute("ADD_TAGS Nirvana;anao;bacalhau;bonito");
        Assertions.assertNotNull(queryResult0);
        Assertions.assertNotNull(queryResult0.result);
        Assertions.assertNotNull(queryResult1);
        Assertions.assertNotNull(queryResult1.result);
        Assertions.assertNotNull(queryResult2);
        Assertions.assertNotNull(queryResult2.result);
        QueryResult queryResult = Main.execute("GET_UNIQUE_TAGS_IN_BETWEEN_YEARS 2001 2002");
        String expectedResult = "BACALHAU 2\n" +
                "ANAO 2\n" +
                "COISO 1\n" +
                "BONITO 1";

        assert queryResult != null;
        Assertions.assertEquals(expectedResult, queryResult.result);
    }

//    @Test
//    public void getRisingStars() {
//        //test-files/getSongTitlesTest
//        File folder = new File("PackageLong");
//        Main.loadFiles(folder);
//
//        QueryResult queryResult = Main.execute("GET_RISING_STARS 2000 2001 ASC");
//        String expectedResult = "Radiohead";
//
//        assert queryResult != null;
//        Assertions.assertEquals(expectedResult, queryResult.result);
//    }

}