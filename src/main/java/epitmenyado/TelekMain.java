package epitmenyado;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelekMain {

    private int savA;
    private int savB;
    private int savC;

    private List<Telek> telekList = new ArrayList<>();

    public void loadFromFile(Path path) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line = bufferedReader.readLine();
            setSavok(line);

            while ((line = bufferedReader.readLine()) != null) {
                loadTeleklist(line);
            }
        } catch (IOException ioException) {
            throw new IllegalStateException("Can not read file", ioException);
        }
    }

    private void setSavok(String line) {
        String[] adok = line.split(" ");
        savA = Integer.parseInt(adok[0]);
        savB = Integer.parseInt(adok[1]);
        savC = Integer.parseInt(adok[2]);
    }

    private void loadTeleklist(String line) {
        String[] telekAdatok = line.split(" ");
        String adoszam = telekAdatok[0];
        String utca = telekAdatok[1];
        String hazszam = telekAdatok[2];
        String adoSav = telekAdatok[3];
        int alapTerulet = Integer.parseInt(telekAdatok[4]);

        createNewTelek(adoszam, utca, hazszam, adoSav, alapTerulet);
    }

    private void createNewTelek(String adoszam, String utca, String hazszam, String adoSav, int alapTerulet) {
        Telek telek = new Telek(adoszam, utca, hazszam, adoSav, alapTerulet);

        int epitmenyAdo = ado(adoSav, alapTerulet);
        telek.setAdo(epitmenyAdo);

        telekList.add(telek);
    }

    //    4. feladat
    private int ado(String adoSav, int alapTerulet) {
        int negyzetmeterAdo = 0;
        switch (adoSav) {
            case "A" -> negyzetmeterAdo = savA * alapTerulet;
            case "B" -> negyzetmeterAdo = savB * alapTerulet;
            case "C" -> negyzetmeterAdo = savC * alapTerulet;
            default -> throw new IllegalArgumentException("Nem létezik ilyen kódjelű adósáv: " + adoSav);
        }
        return negyzetmeterAdo < 10000 ? 0 : negyzetmeterAdo;
    }

    public long telekSzamPerSav(String sav) {
        return telekList.stream()
                .filter(t -> t.getAdoSav().equals(sav))
                .count();
    }

    public long adoPerSav(String sav) {
        return telekList.stream()
                .filter(t -> t.getAdoSav().equals(sav))
                .mapToInt(Telek::getAdo)
                .sum();
    }

    public List<String> getCimek(String adoszam) {
        List<String> cimek = new ArrayList<>();
        telekList.stream()
                .filter(t -> t.getAdoszam().equals(adoszam))
                .forEach(t -> cimek.add(String.format("%s utca %s", t.getUtca(), t.getHazszam())));
        return cimek;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TelekMain telekMain = new TelekMain();

        Path path = Path.of("src", "main", "resources", "utca.txt");

        telekMain.loadFromFile(path);

        System.out.printf("2. feladat. A mintában %d telek szerepel.%n", telekMain.telekList.size());

        System.out.print("3. feladat. Egy tulajdonos adószáma: ");
        String adoszam = scanner.nextLine();
        List<String> cimek = telekMain.getCimek(adoszam);
        cimek.forEach(System.out::println);

        System.out.println("5. feladat");

        String sav = "A";
        System.out.printf("%s sávba %d telek esik, az adó %d Ft.\n", sav, telekMain.telekSzamPerSav(sav), telekMain.adoPerSav(sav));
        sav = "B";
        System.out.printf("%s sávba %d telek esik, az adó %d Ft.\n", sav, telekMain.telekSzamPerSav(sav), telekMain.adoPerSav(sav));
        sav = "C";
        System.out.printf("%s sávba %d telek esik, az adó %d Ft.\n", sav, telekMain.telekSzamPerSav(sav), telekMain.adoPerSav(sav));

    }

}
