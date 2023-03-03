package epitmenyado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class TelekNyilvantartas {

    private final List<Telek> telekList = new ArrayList<>();

    public List<Telek> getTelekList() {
        return new ArrayList<>(telekList);
    }

    public void loadFromFile(Path path) {
        Map<String, Integer> adoSavok = new HashMap<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line = bufferedReader.readLine();
            setSavok(line, adoSavok);

            while ((line = bufferedReader.readLine()) != null) {
                loadTeleklist(line, adoSavok);
            }
        } catch (IOException ioException) {
            throw new IllegalStateException("Can not read file", ioException);
        }
    }

    private void setSavok(String line, Map<String, Integer> adoSavok) {
        String[] adok = line.split(" ");
        adoSavok.put("A", Integer.parseInt(adok[0]));
        adoSavok.put("B", Integer.parseInt(adok[1]));
        adoSavok.put("C", Integer.parseInt(adok[2]));
    }

    private void loadTeleklist(String line, Map<String, Integer> adoSavok) {
        String[] telekAdatok = line.split(" ");
        String adoszam = telekAdatok[0];
        String utca = telekAdatok[1];
        String hazszam = telekAdatok[2];
        String adoSav = telekAdatok[3];
        int alapTerulet = Integer.parseInt(telekAdatok[4]);
        int epitmenyAdo = ado(alapTerulet, adoSav, adoSavok);

        createNewTelek(adoszam, utca, hazszam, adoSav, alapTerulet, epitmenyAdo);
    }

    private int ado(int alapTerulet, String adoSav, Map<String, Integer> adoSavok) {
        int epitmenyAdo = alapTerulet * adoSavok.get(adoSav);
        return epitmenyAdo < 10000 ? 0 : epitmenyAdo;
    }


    private void createNewTelek(String adoszam, String utca, String hazszam, String adoSav, int alapTerulet, int epitmenyAdo) {
        Telek telek = new Telek(adoszam, utca, hazszam, adoSav, alapTerulet, epitmenyAdo);
        telekList.add(telek);
    }

    public long telekSzamPerSav(String sav) {
        return telekList.stream()
                .filter(t -> t.adoSav().equals(sav))
                .count();
    }

    public long adoPerSav(String sav) {
        return telekList.stream()
                .filter(t -> t.adoSav().equals(sav))
                .mapToInt(Telek::ado)
                .sum();
    }

    public List<String> getCimek(String adoszam) {
        List<String> cimek = new ArrayList<>();
        telekList.stream()
                .filter(t -> t.adoszam().equals(adoszam))
                .forEach(t -> cimek.add(String.format("%s utca %s", t.utca(), t.hazszam())));
        return cimek;
    }

    public Set<String> getTobbsavosUtcak() {
        Set<String> aSet = getASet();
        Set<String> bSet = getBSet();
        Set<String> cSet = getCSet();
        return getTobbsavosak(aSet, bSet, cSet);
    }

    private Set<String> getASet() {
        return telekList.stream()
                .filter(telek -> telek.adoSav().equals("A"))
                .map(Telek::utca)
                .collect(Collectors.toSet());
    }

    private Set<String> getBSet() {
        return telekList.stream()
                .filter(telek -> telek.adoSav().equals("B"))
                .map(Telek::utca)
                .collect(Collectors.toSet());
    }

    private Set<String> getCSet() {
        return telekList.stream()
                .filter(telek -> telek.adoSav().equals("C"))
                .map(Telek::utca)
                .collect(Collectors.toSet());
    }

    private Set<String> getTobbsavosak(Set<String> aSet, Set<String> bSet, Set<String> cSet) {
        Set<String> tobbSavosak = new TreeSet<>();
        aSet.retainAll(bSet);
        bSet.retainAll(cSet);
        cSet.retainAll(aSet);
        tobbSavosak.addAll(aSet);
        tobbSavosak.addAll(bSet);
        tobbSavosak.addAll(cSet);
        return tobbSavosak;
    }


    public void writeToFile(Path path) {
        Map<String, Integer> tulajdonosok = getTulajdonosok();
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (Map.Entry<String, Integer> entry : tulajdonosok.entrySet()) {
                bufferedWriter.write(entry.getKey()
                        .concat(" ")
                        .concat(entry.getValue().toString())
                        .concat(System.lineSeparator()));
            }
        } catch (IOException ioException) {
            throw new IllegalStateException("Can not write file", ioException);
        }
    }

    private Map<String, Integer> getTulajdonosok() {
        Map<String, Integer> tulajdonosok = new HashMap<>();
        for (Telek telek : telekList) {
            if (!tulajdonosok.containsKey(telek.adoszam())) tulajdonosok.put(telek.adoszam(), telek.ado());
            else tulajdonosok.put(telek.adoszam(), tulajdonosok.get(telek.adoszam()) + telek.ado());
        }
        return tulajdonosok;
    }
}
