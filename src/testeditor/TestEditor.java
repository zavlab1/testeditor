package testeditor;

import testeditor.question.*;
import testeditor.saver.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class TestEditor {
	public static void main(String[] args) throws IOException {

		Map<String, Map<String, Boolean>> questions = new TreeMap<>();

		Map<String, Boolean> answers1 = new TreeMap<>();
		answers1.put("Москва", true);
		answers1.put("Санкт-Петербург", false);
		answers1.put("Курск", false);
		questions.put("Столица нашей Родины?", answers1);

		Map<String, Boolean> answers2 = new TreeMap<>();
		answers2.put("ФМФ", false);
		answers2.put("Геофак", false);
		answers2.put("ИПФ", true);
		questions.put("Название нашего факультета?", answers2);

		Test test = Test.getInstance(questions);
	}
}
