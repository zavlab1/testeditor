package testeditor;

import testeditor.parser.Parser;
import testeditor.question.*;
import testeditor.saver.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;


public class TestEditor {
	public static void main(String[] args) {
/*
		ArrayList<Answer> answ = new ArrayList<>();
		answ.add(new Answer(true,"Москва"));
		answ.add(new Answer(false,"Санкт-Петербург"));

		Question qe = new Select("Столица России?",answ);

		GiftSaver.toFile("Test.txt",qe);
	}
}*/
		Test test =	Test.getTestFromFile("Select.gift");
	}
}