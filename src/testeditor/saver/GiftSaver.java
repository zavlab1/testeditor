package testeditor.saver;

import testeditor.Test;
import testeditor.question.*;

/**
 * Класс, ответственный за форматирование вопроса в GIFT-формате
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
        String answerLine = doQHeadLine(q);

        for (Answer a : q.getAnswerList()) {
<<<<<<< HEAD
            if (a.getDegree() == Answer.MAX_DEGREE) {
                answerLine += "\t=";
            } else if (a.getDegree() == Answer.MIN_DEGREE) {
                answerLine += "\t~";
            } else if (a.getDegree() < Answer.MAX_DEGREE && a.getDegree() > Answer.MIN_DEGREE) {
=======
            if (a.getDegree() == 100) {
                answerLine += "\t=";
            } else if (a.getDegree() == 0) {
                answerLine += "\t~";
            } else if (a.getDegree() < 100 && a.getDegree() > 0) {
>>>>>>> 836a764... Do degree for answer int type & small fixes
                answerLine += "\t~%" + a.getDegree() + "%";
            } else {
                System.err.println("Can't save answer \'" + a.getAText() + "\' for question " + q.getQName() +
                        ". Degree value " + a.getDegree() + " is incorrect. The question will be skipped.");
                return "";
            }
            answerLine += a.getAText() + getComment(a) + "\n";
        }
        return answerLine.trim()+"\n}";
    }

    public String doLineForTrueFalse(Question q){
        String qTextLine = doQHeadLine(q);
        Answer a = q.getAnswerList().get(0);
        String answerLine = qTextLine.substring(0, qTextLine.length()-1 ) + a.getAText() + getComment(a) + "}\n";
        return answerLine.trim();
    }

    public String doLineForMatching(Question q){
        String answerLine = doQHeadLine(q);
        for (Answer a : q.getAnswerList()) {
            answerLine += "\t=" + a.getAText() + getComment(a) + "\n";
        }
        return answerLine.trim()+"\n}";
    }

    public String doLineForShortAnswer(Question q){
        String answerLine = doQHeadLine(q);
        for (Answer a : q.getAnswerList()) {
<<<<<<< HEAD
            if (a.getDegree() > Answer.MIN_DEGREE && a.getDegree() <= Answer.MAX_DEGREE) {
=======
            if (a.getDegree() > 0 && a.getDegree() <= 100) {
>>>>>>> 836a764... Do degree for answer int type & small fixes
                answerLine += "\t=%" + a.getDegree() + "%";
            } else {
                System.err.println("Can't save answer \'" + a.getAText() + "\' for question " + q.getQName() +
                        ". Degree value " + a.getDegree() + " is incorrect. The question will be skipped.");
                return "";
            }
            answerLine += a.getAText() + getComment(a) + "\n";
        }
        return answerLine.trim()+"\n}";
    }

    public String doLineForNumerical(Question q){
        String answerLine = doQHeadLine(q).substring(0, doQHeadLine(q).length()-1) + "#\n";
        for (Answer a : q.getAnswerList()) {
<<<<<<< HEAD
            if (a.getDegree() == Answer.MAX_DEGREE) {
                answerLine += "\t=";
            } else if (a.getDegree() > Answer.MIN_DEGREE && a.getDegree() < Answer.MAX_DEGREE) {
=======
            if (a.getDegree() == 100) {
                answerLine += "\t=";
            } else if (a.getDegree() > 0 && a.getDegree() < 100) {
>>>>>>> 836a764... Do degree for answer int type & small fixes
                answerLine += "\t=%" + a.getDegree() + "%";
            } else {
                System.err.println("Can't save answer \'" + a.getAText() + "\' for question " + q.getQName() +
                        ". Degree value " + a.getDegree() + " is incorrect. The question will be skipped.");
                return "";
            }
            answerLine += a.getAText() + getComment(a) + "\n";
        }
        return answerLine.trim()+"\n}";
    }

    private String doQHeadLine(Question q){
        return "::" + q.getQName() + ".::" + q.getQText() + "\n{\n";
    }
    private String getComment(Answer a) {
        return a.getAComment().isEmpty() ? "" : ("#" + a.getAComment());
    }
}
