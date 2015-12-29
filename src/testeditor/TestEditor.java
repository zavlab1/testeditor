package testeditor;

import testeditor.question.*;
import testeditor.saver.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;


public class TestEditor {
	public static void main(String[] args) throws IOException {

		HashSet<Question> questions = new HashSet<>();

		ArrayList<Answer> answers1 = new ArrayList<>();
		answers1.add(new Answer("Москва", true));
		answers1.add(new Answer("Санкт-Петербург", false));
		answers1.add(new Answer("Курск", false));

		Question q1 = new Select("Столица нашей Родины?", answers1);
		questions.add(q1);

		ArrayList<Answer> answers2 = new ArrayList<>();
		answers2.add(new Answer("ФМФ", false));
		answers2.add(new Answer("Геофак", false));
		answers2.add(new Answer("ИПФ", true));

		Question q2 = new Select("Название нашего факультета?", answers2);
		questions.add(q2);

		Test test = Test.getInstance("Тест №1", questions);
		new GiftSaver(test, "Test.txt").save(q2);
	}
}
