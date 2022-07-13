import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
Possiamo rappresentare un labirinto come una matrice di caratteri, in cui il carattere "-" rappresenta uno spazio vuoto,
la "P" la posizione del giocatore e la "E" l’uscita.
Scrivete un programma che permette ad un utente di giocare al vostro labirinto.
Per farlo potrà scrivere sulla console di java le lettere «WASD» per muoversi (come in molti videogiochi per PC).
Quando il giocatore raggiunge il traguardo, verrà avvisato di aver vinto e il programma termina.
 */
public class Labyrinth {
    public static Scanner sc = new Scanner(System.in);
    public static int[] playerPos = {0, 0};

    public static void main(String[] args) {
        char[][] lab = new char[10][10];
        boolean exit = false;
        lab = fill(createRoute(lab.length, lab[0].length));
        for (int i = 0; i < lab.length; i++)
            System.out.println(Arrays.toString(lab[i]));
        while (!exit) {
            System.out.println("Posizione player: " + playerPos[0] + " " + playerPos[1]);
            switch (getInput()) {
                case 'A':
                    if (playerPos[1] != 0)
                        exit = checkExit(playerPos[0], playerPos[1] - 1, lab);
                    break;
                case 'D':
                    if (playerPos[1] != lab[0].length - 1)
                        exit = checkExit(playerPos[0], playerPos[1] + 1, lab);
                    break;
                case 'S':
                    if (playerPos[0] != 0)
                        exit = checkExit(playerPos[0] + 1, playerPos[1], lab);
                    break;
                case 'W':
                    if (playerPos[0] != lab.length - 1)
                        exit = checkExit(playerPos[0] - 1, playerPos[1], lab);
                    break;
            }
            for (int i = 0; i < lab.length; i++)
                System.out.println(Arrays.toString(lab[i]));
        }
        System.out.println("Hai vinto!");
    }

    public static boolean checkExit(int n, int m, char[][] lab) {
        if (lab[n][m] == 'E')
            return true;
        if(lab[n][m]!='|') {
            lab[n][m] = 'P';
            lab[playerPos[0]][playerPos[1]] = '-';
            playerPos[0] = n;
            playerPos[1] = m;
        }
        return false;
    }

    public static char[][] fill(char[][] lab) {
        Random rnd = new Random();
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                if (lab[i][j] != 'P' && lab[i][j] != 'E' && lab[i][j] != '-')
                    lab[i][j] = rnd.nextBoolean() ? '-' : '|';
            }
        }
        return lab;
    }

    public static char[][] createRoute(int n, int m) {
        char[][] lab = new char[n][m];
        boolean exit = false;
        Random rnd = new Random();
        int count = 0;
        //devo scegliere una riga casuale tra 0 e n-1
        int casualRow = rnd.nextInt(n);
        int casualCol = 0;
        //se la riga è 0 o n-1 allora scelgo l'entrata in una colonna qualsiasi tranne 0 e m-1
        if (casualRow == 0 || casualRow == n - 1)
            casualCol = rnd.nextInt(m - 1) + 1;
        else //altrimenti scelgo 0 o m-1
            casualCol = rnd.nextBoolean() ? 0 : n - 1;

        lab[casualRow][casualCol] = 'P';
        playerPos[0] = casualRow;
        playerPos[1] = casualCol;
        while (!exit) {
            int cas = rnd.nextInt(4);//scelgo dove andare
            switch (cas) {
                case 0://vado sopra
                    if (casualRow >= 2 && ((casualRow - 1) != 'P' || (casualRow - 1) != 'c' || (casualRow - 2) != 'P' || (casualRow - 2) != 'c')) {
                        lab[--casualRow][casualCol] = '-';
                        lab[--casualRow][casualCol] = '-';
                        count++;
                    }
                    break;
                case 1://vado sotto
                    if (casualRow < n - 2 && ((casualRow + 1) != 'P' || (casualRow + 1) != 'c' || (casualRow + 2) != 'P' || (casualRow + 2) != 'c')) {
                        lab[++casualRow][casualCol] = '-';
                        lab[++casualRow][casualCol] = '-';
                        count++;
                    }
                    break;
                case 2://vado a destra
                    if (casualCol < m - 2 && ((casualCol + 1) != 'P' || (casualCol + 1) != 'c' || (casualCol + 2) != 'P' || (casualCol + 2) != 'c')) {
                        lab[casualRow][++casualCol] = '-';
                        lab[casualRow][++casualCol] = '-';
                        count++;
                    }
                    break;
                case 3://vado a sinistra
                    if (casualCol >= 2 && ((casualCol - 1) != 'P' || (casualCol - 1) != 'c' || (casualCol - 2) != 'P' || (casualCol - 2) != 'c')) {
                        lab[casualRow][--casualCol] = '-';
                        lab[casualRow][--casualCol] = '-';
                        count++;
                    }
                    break;
            }
            if (count > 2 && (casualCol == 0 || casualRow == 0 || casualRow == m - 1 || casualCol == n - 1))
                exit = true;
        }
        System.out.println("--------------------");
        lab[casualRow][casualCol] = 'E';
        lab[playerPos[0]][playerPos[1]] = 'P';
        for (int i = 0; i < lab.length; i++)
            System.out.println(Arrays.toString(lab[i]));
        System.out.println("------------------------");
        return lab;
    }

    public static char getInput() {
        boolean flag = false;
        while (!flag) {
            System.out.println("Dove vuoi andare? scrivi w,a,s,d per muoverti");
            char c = sc.nextLine().toUpperCase().charAt(0);
            if (c == 'W' || c == 'A' || c == 'S' || c == 'D')
                return c;
        }
        return getInput();
    }
}
