package testeditor;

import testeditor.question.*;
import testeditor.saver.*;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;


public class TestEditor {
	public static void main(String[] args) throws IOException {

		TreeSet<Question> questions = new TreeSet<>();

		ArrayList<Answer> answers1 = new ArrayList<>();
		answers1.add(new Answer("Москва", true));
		answers1.add(new Answer("Санкт-Петербург", false));
		answers1.add(new Answer("Курск", false));

		Question q1 = new Select("Столица нашей Родины?", answers1);
		questions.remove(q1);
		questions.add(q1);

		ArrayList<Answer> answers2 = new ArrayList<>();
		answers2.add(new Answer("ФМФ", false));
		answers2.add(new Answer("Геофак", false));
		answers2.add(new Answer("ИПФ", true));

		Question q2 = new Select("Название нашего факультета?", answers2);
		questions.remove(q2);
		questions.add(q2);

		Test test = Test.getInstance("Тест №1", questions);
		for (Question q : questions) {
			new GiftSaver(q, "Text.txt");
		}
	}
}
