package it.polimi.sw2019;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.Marks;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMarks {

    @Test
    public void addMarkTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);

        Marks resultMarks = new Marks(characters);
        resultMarks.addTokens(1, Character.DISTRUCTOR);
        resultMarks.addTokens(2, Character.DOZER);
        resultMarks.addTokens(3, Character.VIOLET);

        List<Character> resultMarksSequence = new ArrayList<>();
        resultMarksSequence.add(Character.DISTRUCTOR);
        resultMarksSequence.add(Character.DOZER);
        resultMarksSequence.add(Character.DOZER);
        resultMarksSequence.add(Character.VIOLET);
        resultMarksSequence.add(Character.VIOLET);
        resultMarksSequence.add(Character.VIOLET);

        Marks marks = new Marks(characters);
        marks.addMark(1, Character.DISTRUCTOR);
        marks.addMark(2, Character.DOZER);
        marks.addMark(4, Character.VIOLET);

        assertEquals(resultMarks.getMap(), marks.getMap());
        assertEquals(resultMarksSequence, marks.getMarkSequence());
    }

    @Test
    public void removeMarksTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);

        Marks resultMarks = new Marks(characters);
        resultMarks.addTokens(1, Character.DISTRUCTOR);
        resultMarks.addTokens(3, Character.VIOLET);

        List<Character> resultMarksSequence = new ArrayList<>();
        resultMarksSequence.add(Character.DISTRUCTOR);
        resultMarksSequence.add(Character.VIOLET);
        resultMarksSequence.add(Character.VIOLET);
        resultMarksSequence.add(Character.VIOLET);

        Marks marks = new Marks(characters);
        marks.addMark(1, Character.DISTRUCTOR);
        marks.addMark(2, Character.DOZER);
        marks.addMark(4, Character.VIOLET);
        marks.removeMarks(Character.DOZER);

        assertEquals(resultMarks.getMap(), marks.getMap());
        assertEquals(resultMarksSequence, marks.getMarkSequence());
    }

}