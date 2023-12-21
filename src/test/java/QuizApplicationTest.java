import org.example.Participant;
import org.example.Quiz;
import org.example.QuizException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuizApplicationTest {
    @Test
    void testQuizCreation() {
        try {
            Quiz quiz = new Quiz(1000);
            quiz.addQuestions("Test1","1","test explanation","test");
            quiz.addQuestions("Test2","2","test explanation","test");
            quiz.addQuestions("Test3","3","test explanation","test");
            assertEquals(3,quiz.getQuestionCount());
        } catch(QuizException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testParticipantAddition() {
        String name="Prateek Chandra";
        String roll="2001035";
        Participant ob=new Participant(name,roll);
        assertEquals("Prateek Chandra",ob.getName());
    }
    @Test
    void testQuizAttempt() {
        HashMap<Integer,String> answers=new HashMap<>();
        answers.put(1,"1");
        answers.put(2,"2");
        answers.put(3,"3");
        try {
            Quiz quiz=new Quiz(1000);
            quiz.addQuestions("Test1","1","test explanation","test");
            quiz.addQuestions("Test2","2","test explanation","test");
            quiz.addQuestions("Test3","3","test explanation","test");
            quiz.addParticipants("Prateek Chandra","2001035");
            quiz.submitAnswers(answers,"2001035");
            assertEquals(3,quiz.showResult("2001035").size());
        } catch (QuizException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testQuizScore() {
        HashMap<Integer,String> answers=new HashMap<>();
        answers.put(1,"1");
        answers.put(2,"2");
        answers.put(3,"3");
        try {
            Quiz quiz=new Quiz(1000);
            quiz.addQuestions("Test1","1","test explanation","test");
            quiz.addQuestions("Test2","2","test explanation","test");
            quiz.addQuestions("Test3","3","test explanation","test");
            quiz.addParticipants("Prateek Chandra","2001035");
            quiz.submitAnswers(answers,"2001035");
            assertEquals(30,quiz.calculateScore(quiz.showResult("2001035").size()));
        } catch (QuizException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testWrongAnswer() {
        HashMap<Integer,String> answers=new HashMap<>();
        answers.put(1,"1");
        answers.put(2,"3");
        answers.put(3,"3");
        try {
            Quiz quiz = new Quiz(1000);
            quiz.addQuestions("Test1", "1","test explanation","test");
            quiz.addQuestions("Test2", "2","test explanation","test");
            quiz.addQuestions("Test3", "3","test explanation","test");

            assertThrows(QuizException.class, () -> quiz.submitAnswers(answers, "2001025"));
        } catch(QuizException e) {
            e.printStackTrace();
        }

    }
}
