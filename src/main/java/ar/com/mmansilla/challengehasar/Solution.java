package ar.com.mmansilla.challengehasar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        final String outputPath = "./archivo.txt";
//        final String outputPath = System.getenv("OUTPUT_PATH");
        System.out.println("outputPath = " + outputPath);
        final FileWriter fileWriter = new FileWriter(outputPath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        int scoresCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        int[] scores = new int[scoresCount];
        String[] scoresItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        for (int i = 0; i < scoresCount; i++) {
            int scoresItem = Integer.parseInt(scoresItems[i]);
            scores[i] = scoresItem;
        }
        int aliceCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        int[] alice = new int[aliceCount];
        String[] aliceItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        for (int i = 0; i < aliceCount; i++) {
            int aliceItem = Integer.parseInt(aliceItems[i]);
            alice[i] = aliceItem;
        }
        int[] result = climbingLeaderboard(scores, alice);
        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));
            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }
        bufferedWriter.newLine();
        bufferedWriter.close();
        scanner.close();
    }

    private static int[] climbingLeaderboard(int[] scores, int[] alice) {

        List<Integer> listaScoresAlice = new ArrayList<>();
        for (int ali : alice) {
            listaScoresAlice.add(ali);
        }
        // Si no estuviera ordenado lo ordena en forma DESCENDENTE
        Collections.sort(listaScoresAlice);

        List<Integer> listaScores = new ArrayList<>();
        for (int sc : scores) {
            listaScores.add(sc);
        }
        // Si no estuviera ordenado lo ordena en forma ASCENDENTE
        Collections.sort(listaScores, Collections.reverseOrder());

        ArrayList<Posicion> arrayListPosiciones = new ArrayList<>();
        int pos = 1;
        for (int i = 0; i < listaScores.size(); i++) {
            if (i > 0 && !listaScores.get(i).equals(listaScores.get(i - 1))) {
                pos++;
            }
            arrayListPosiciones.add(new Posicion(pos, listaScores.get(i), Boolean.FALSE));
        }

        List<Integer> listaSalida = new ArrayList<>();
        for (int i = 0; i < listaScoresAlice.size(); i++) {
            arrayListPosiciones = agregarScoreAlice(listaScoresAlice.get(i), arrayListPosiciones, listaSalida, i);
        }

        int[] arraySalida = new int[listaSalida.size()];

        for (int idx = 0; idx < listaSalida.size(); idx++) {
            arraySalida[idx] = listaSalida.get(idx);
        }
        return arraySalida;
    }

    private static ArrayList<Posicion> agregarScoreAlice(Integer puntajeAlice, ArrayList<Posicion> arrayPosiciones, List<Integer> listSalida, int idx) {
        ArrayList<Posicion> nuevoArrayList = new ArrayList<>();

        ArrayList<Posicion> arrayPosGreatAlice = arrayListToStream(arrayPosiciones.stream()
                .filter(pos -> pos.getPuntaje() > puntajeAlice));
        ArrayList<Posicion> arrayPosEqualsAlice = arrayListToStream(arrayPosiciones.stream()
                .filter(pos -> pos.getPuntaje().equals(puntajeAlice)));
        ArrayList<Posicion> arrayPosLessAlice = arrayListToStream(arrayPosiciones.stream()
                .filter(pos -> pos.getPuntaje() < puntajeAlice));

        Integer pAlice = null;
        if (!arrayPosEqualsAlice.isEmpty()) { // si hay de igual puntaje
            pAlice = arrayPosEqualsAlice.get(0).getPosicion();
            arrayPosEqualsAlice.add(new Posicion(pAlice, puntajeAlice, Boolean.TRUE));
        } else if (!arrayPosLessAlice.isEmpty()) { // si hay de menor puntaje, pero no iguales
            pAlice = arrayPosLessAlice.get(0).getPosicion();
            arrayPosLessAlice = shiftPosicion(arrayPosLessAlice);
            arrayPosEqualsAlice.add(new Posicion(pAlice, puntajeAlice, Boolean.TRUE));
        } else if (!arrayPosGreatAlice.isEmpty()) { // si solo hay mayores, queda como ultimo
            pAlice = arrayPosGreatAlice.get(arrayPosGreatAlice.size() - 1).getPosicion() + 1;
            arrayPosGreatAlice.add(new Posicion(pAlice, puntajeAlice, Boolean.TRUE));
        }
        listSalida.add(pAlice);

        nuevoArrayList.addAll(arrayPosGreatAlice);
        nuevoArrayList.addAll(arrayPosEqualsAlice);
        nuevoArrayList.addAll(arrayPosLessAlice);

        System.out.println("listaSalida");
        listSalida.forEach(System.out::println);

        return nuevoArrayList;
    }

    private static ArrayList<Posicion> arrayListToStream(Stream<Posicion> stream) {
        List<Posicion> list = stream.collect(Collectors.toList());
        ArrayList<Posicion> arrayList = new ArrayList<>(list);
        return arrayList;
    }

    private static ArrayList<Posicion> shiftPosicion(ArrayList<Posicion> arrayPosLessAlice) {
        ArrayList<Posicion> arrayList = new ArrayList<>();
        arrayPosLessAlice.forEach(posicion -> {
            arrayList.add(new Posicion(posicion.getPosicion() + 1, posicion.getPuntaje(), posicion.getEsAlice()));
        });
        return arrayList;
    }
}
