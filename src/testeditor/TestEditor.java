package testeditor;

import testeditor.question.*;
import testeditor.saver.*;

import java.io.*;
import java.util.ArrayList;


public class TestEditor {
	public static void main(String[] args) throws IOException {

		ArrayList<Answer> answ = new ArrayList<>();
		answ.add(new Answer(true,"Москва"));
		answ.add(new Answer(false,"Санкт-Петербург"));

		Question q = new Select("Столица России?",answ);

		GiftSaver.toFile("Test.txt",q);
	}
}
