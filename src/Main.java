import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String castle = "\uD83C\uDFF0";
        String shieldItem = "\uD83D\uDEE1️";
        int sizeBoard = 5;

        Person person = new Person(sizeBoard);

        int step = 0;

        String[][] board = new String[sizeBoard][sizeBoard];
        for (int y = 0; y < sizeBoard; y++) {
            for (int x = 0; x < sizeBoard; x++) {
                board[y][x] = "  ";
            }
        }

        int countMonster = sizeBoard * sizeBoard - sizeBoard - 5;
        Random r = new Random();

        Monster[] arrMonster = new Monster[countMonster + 1];
        int count = 0;
        Monster test = null;
        while (count <= countMonster){
            int rn = r.nextInt(3);
            if (rn == 0) {
                test = new Monster(sizeBoard);
            }else if (rn == 1) {
                test = new BigMonster(sizeBoard);
            }else {
                test = new TricksterMonster(sizeBoard);
            }
            if (board[test.getY()][test.getX()].equals("  ")){
                board[test.getY()][test.getX()] = test.getImage();
                arrMonster[count] = test;
                count++;
            }
        }

        int castleX = r.nextInt(sizeBoard);
        int castleY = 0;

        board[castleY][castleX] = castle;

        while (true) {
            int shieldX = r.nextInt(sizeBoard);
            int shieldY = r.nextInt(sizeBoard);
            if (board[shieldY][shieldX].equals("  ")) {
                board[shieldY][shieldX] = shieldItem;
                break;
            }
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");
            String answer = sc.nextLine();
            System.out.println("Ваш ответ:\t" + answer);
            answer = answer.toUpperCase();

            if (answer.equals("ДА")) {
                int difficultGame;
                while (true) {
                    System.out.println("Выбери сложность игры(от 1 до 5):");

                    if (sc.hasNextInt()) {
                        difficultGame = sc.nextInt();
                        if (difficultGame >= 1 && difficultGame <= 5) {
                            System.out.println("Выбранная сложность:\t" + difficultGame);
                            break;
                        } else {
                            System.out.println("Данные введены неккоректно. Пожалуйста, повторите ввод.");
                        }
                    } else {
                        System.out.println("Данные введены неккоректно. Пожалуйста, повторите ввод.");
                        sc.next();
                    }
                }


                while (true) {
                    board[person.getY() - 1][person.getX() - 1] = person.getImage();
                    outputBoard(board, person.getLive(), person.hasShield());
                    System.out.println("Введите куда будет ходить персонаж (ход возможен только по вертикали и горизонтали на одну клетку)" +
                            "\nКоординаты персонажа - (x: " + person.getX() + ", y: " + person.getY() + "))");
                    int x = sc.nextInt();
                    int y = sc.nextInt();

                    if (person.moveCorrect(x, y)) {
                        String next = board[y - 1][x - 1];
                        if (next.equals("  ")) {
                            board[person.getY() - 1][person.getX() - 1] = "  ";
                            person.move(x, y);
                            step++;
                            System.out.println("Ход корректный; Новые координаты: " + person.getX() + ", " + person.getY() +
                                    "\nХод номер: " + step);
                        } else if (next.equals(castle)) {
                            System.out.println("Вы прошли игру!");
                            break;
                        } else if (next.equals(shieldItem)) {
                            board[person.getY() - 1][person.getX() - 1] = "  ";
                            person.move(x, y);
                            person.getShield();
                            step++;
                            System.out.println("Ход корректный; Новые координаты: " + person.getX() + ", " + person.getY() +
                                    "\nХод номер: " + step);
                        } else {
                            for (Monster monster : arrMonster) {
                                if (monster.conflictPerson(x, y)) {
                                    if (monster.taskMonster(difficultGame)) {
                                        board[person.getY() - 1][person.getX() - 1] = "  ";
                                        person.move(x, y);
                                    } else {
                                        person.downLive();
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Неккоректный ход");
                    }
                }
                break;
            } else if (answer.equals("НЕТ")) {
                System.out.println("Жаль, приходи еще!");
                break;
            } else {
                System.out.println("Данные введены неккоректно. Пожалуйста, повторите ввод.");
            }
        }
    }

    static void outputBoard(String[][] board, int live, boolean hasShield) {
        String leftBlock = "| ";
        String rightBlock = "|";
        String wall = "+ —— + —— + —— + —— + —— +";

        for (String[] raw : board) {
            System.out.println(wall);
            for (String col : raw) {
                System.out.print(leftBlock + col + " ");
            }
            System.out.println(rightBlock);
        }
        System.out.println(wall);

        System.out.print("Количество жизней:\t" + live);
        if (hasShield) {
            System.out.println("\tЩит: Есть");
        } else {
            System.out.println("\tЩит: Нет");
        }
        System.out.println();
    }
}
