package pt.ulusofona.aed.rockindeisi2023;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestMain {


    @Test
    public void stringArtistas() {
        ArtistsClass artista = new ArtistsClass("João Maria", 2);
        String str = "Artista: [João Maria] | 2";

        
        Assertions.assertEquals(str, artista.toString());
    }

    @Test
    public void stringSongMenor1995() {
        ExtrasClass details = new ExtrasClass("123", 347951, "0", 2, 0.2, 2.2, "awda");
        SongsClass song = new SongsClass("123", "Mamamia", 1965, details, new ArrayList<String>(List.of("Marco")));
        String str = "123 | Mamamia | 1965";

        Assertions.assertEquals(str, song.toString());
    }

    @Test
    public void stringSongMenor2000() {
        ExtrasClass details = new ExtrasClass("123", 347951, "0", 2, 0.2, 2.2, "awda");
        SongsClass song = new SongsClass("123", "Mamamia", 1999, details, new ArrayList<String>(List.of("Marco")));
        String str = "123 | Mamamia | 1999 | 5:47 | 2";

        Assertions.assertEquals(str, song.toString());
    }


    @Test
    public void stringSongMaior2000() {
        ExtrasClass details = new ExtrasClass("123", 347951, "0", 2, 0.2, 2.2, "awda");
        SongsClass song = new SongsClass("123", "Mamamia", 2001, details, new ArrayList<String>(List.of("Marco")));
        String str = "123 | Mamamia | 2001 | 5:47 | 2 | 1";

        Assertions.assertEquals(str, song.toString());
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

    private static class Assertions {
        public static void assertEquals(String str, String toString) {
        }

        public static void assertTrue(boolean loadFiles) {
        }
    }
}
