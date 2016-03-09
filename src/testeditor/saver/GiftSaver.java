package testeditor.saver;

import testeditor.Test;
import testeditor.question.*;

/**
 * Класс, ответственный за запись вопроса в файл в GIFT-формате
 * Абстрактный потому, что содержит пока что только статические методы;
 */
public class GiftSaver extends Saver {

    /**
     * Записывает вопрос в текстовый файл в формате GIFT
     * @param filepath - путь к выходному файлу
     * @param test - объект вопроса
     */
    public GiftSaver(Test test, String filepath) {
        super(test, filepath);
    }

    /**
     * @return возвращает строку с вопросом и вариантами ответа
     * для вопросов на выброр в формате GIFT
     */
    public String doLineForMultiChoice(Question q){
        String answerLine = doHeadLine(q);

        for (Answer a : q.getAnswerList()) {
            if (a.getDegree() == 1.) {
                answerLine += "\t=";
            } else if (a.getDegree() == 0.) {
                answerLine += "\t~";
            } else if (a.getDegree() < 1 && a.getDegree() > 0) {
                answerLine += "\t~%" + Float.toString(a.getDegree()*100) + "%";
            } else {
                System.err.println("Can't save answer \'" + a.getValue() + "\' for question " + q.getNumber() +
                        ". Degree value " + a.getDegree() + " is incorrect. The question will be skipped.");
                return "";
            }
            answerLine += a.getValue() + "\n";
        }
        return answerLine.trim()+"\n}";
    }

    public String doLineForTrueFalse(Question q){
        String headLine = doHeadLine(q);
        String answerLine = headLine.substring(0, headLine.length()-1 ) + q.getAnswerList().get(0).getValue() + "}\n";
        return answerLine.trim();
    }

    public String doLineForMatching(Question q){
        String answerLine = doHeadLine(q) + q.getAnswerList().get(0).getValue() + "}\n";
        return answerLine.trim();
    }

    public String doLineForShortAnswer(Question q){
        String answerLine = doHeadLine(q) + q.getAnswerList().get(0).getValue() + "}\n";
        return answerLine.trim();
    }

    private String doHeadLine(Question q){
        return "::" + q.getNumber() + ".::" + q.getHead() + "\n{\n";
    }
}
