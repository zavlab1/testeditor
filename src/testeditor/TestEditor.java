package testeditor;

import testeditor.question.*;
import testeditor.saver.*;

import java.io.PrintStream;


public class TestEditor {
    public static void main(String[] args) {
        Test test =	Test.getTestFromFile("test.gift");
        Saver s = new GiftSaver(test, "Test1.gift");
        for (Question q : test) {

                s.save(q);

            break; // только для тестирования. Т.к. для сохранения даже одного вопроса файл переписывается полностью,
                   // то нет смысла гонять весь цикл.
        }
    }
}