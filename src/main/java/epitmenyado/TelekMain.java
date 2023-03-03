package epitmenyado;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TelekMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TelekNyilvantartas telekNyilvantartas = new TelekNyilvantartas();

        Path utca = Path.of("src", "main", "resources", "utca.txt");
        telekNyilvantartas.loadFromFile(utca);

//      2. feladat
        System.out.printf("2. feladat. A mintában %d telek szerepel.%n", telekNyilvantartas.getTelekList().size());

//      3. feladat
        System.out.print("3. feladat. Egy tulajdonos adószáma: ");
        String adoszam = scanner.nextLine();
        List<String> cimek = telekNyilvantartas.getCimek(adoszam);
        cimek.forEach(System.out::println);

//      5. feladat
        System.out.println("5. feladat");

        String sav = "A";
        System.out.printf("%s sávba %d telek esik, az adó %d Ft.\n", sav, telekNyilvantartas.telekSzamPerSav(sav), telekNyilvantartas.adoPerSav(sav));
        sav = "B";
        System.out.printf("%s sávba %d telek esik, az adó %d Ft.\n", sav, telekNyilvantartas.telekSzamPerSav(sav), telekNyilvantartas.adoPerSav(sav));
        sav = "C";
        System.out.printf("%s sávba %d telek esik, az adó %d Ft.\n", sav, telekNyilvantartas.telekSzamPerSav(sav), telekNyilvantartas.adoPerSav(sav));

//      6. feladat
        System.out.println("6. feladat. A több sávba sorolt utcák: ");
        Set<String> tobbsavosUtcak = telekNyilvantartas.getTobbsavosUtcak();
        tobbsavosUtcak.forEach(System.out::println);


//      7. feladat
        Path fizetendo = Path.of("src", "main", "resources", "fizetendo.txt");
        telekNyilvantartas.writeToFile(fizetendo);
    }
}
