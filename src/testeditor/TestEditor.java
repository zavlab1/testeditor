package testeditor;

import testeditor.question.*;
import testeditor.saver.*;


public class TestEditor {
    public static void main(String[] args) {
        Test test =	Test.getTestFromFile("orig_test2.gift");
        Saver s = new GiftSaver(test, "Test1.gift");
        for (Question q : test) {
            s.save(q);
            break; // только для тестирования. Т.к. для сохранения даже одного вопроса файл переписывается полностью,
                   // то нет смысла гонять весь цикл.
        }
    }
}