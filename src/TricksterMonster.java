import java.util.Scanner;

public class TricksterMonster extends Monster {

    TricksterMonster(int sizeBoard) {
        super(sizeBoard);
        setImage("\uD83E\uDDDF\u200D");
    }

    @Override
    public boolean taskMonster(int difficultGame) {
        System.out.println("Вместо обычного Монстра был Монстр-Обманщик!");
        System.out.println("Решите задачу:");
        int trueAnswer = r.nextInt(4);
        System.out.println("Выбери цифру в одной из них спрятан монстр а другие безопасные");
        System.out.println("1 2 3 4");

        Scanner sc = new Scanner(System.in);
        int ans = sc.nextInt();

        if (trueAnswer == ans) {
            System.out.println("Ты проиграл эту битву!");
            return false;
        } else {
            System.out.println("Верно! Ты победил обманщика");
            return true;
        }
    }
}
