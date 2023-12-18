package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    private final HashMap<Integer,Question> questions=new HashMap<>();
    private final HashMap<Integer,Boolean> correctAnswers=new HashMap<>();
    private final HashMap<String,Participant> participants=new HashMap<>();
    private final HashMap<String,Participant> instructors=new HashMap<>();
    private final HashMap<Integer,String> answers=new HashMap<>();
    private Integer questionCount=1;
    String filePathParticipant="Participants.csv";
    String filePathInstructor="Instructors.csv";
    CSVQuizHandler csvQuizHandlerParticipant;
    CSVQuizHandler csvQuizHandlerInstructor;

    public Quiz() throws QuizException {
        this.csvQuizHandlerInstructor=new CSVQuizHandler(filePathInstructor);
        this.csvQuizHandlerParticipant=new CSVQuizHandler(filePathParticipant);
    }

    public void addQuestions(String question,String answer) {
        Question questionOb = new Question(question, answer);
        questions.put(questionCount++, questionOb);
        System.out.println("Question Successfully Added!!");
    }

    public void submitAnswers(HashMap<Integer,String> answers,String roll) throws QuizException {
        if(!participants.containsKey(roll)) {
            throw new QuizException("Participant is not Registered!!");
        }
        for(Integer i:answers.keySet()) {
            String answer=answers.get(i);
            if(answer.compareTo(questions.get(i).qetAnswer())==0) {
                correctAnswers.put(i,true);
            } else {
                participants.get(roll).setCorrectSubmissions(correctAnswers);
                throw new QuizException("Incorrect Answer!!");
            }
        }
        participants.get(roll).setCorrectSubmissions(correctAnswers);
    }

    public void answerQuestion(Integer i,String answer) {
        answers.put(i,answer);
    }

    public Integer getQuestionCount() {
        return questions.size();
    }

    public void takeQuizzes(String roll) throws QuizException {
        Scanner in=new Scanner(System.in);
        int k;
        for(Integer i:questions.keySet()) {
            k=i;
            System.out.println("Q"+i+" "+questions.get(i).getQuestion());
            System.out.println("Enter your Answer: ");
            String answer=in.nextLine();
            answerQuestion(i,answer);
            int flag;
            System.out.println("Do you want to submit the quiz? (0 for No / 1 for Yes)");
            flag=in.nextInt();
            in.nextLine();
            if(flag==1) {
                if(k!=questions.size()) {
                    System.out.println("Your test successfully submitted!!");
                    throw new QuizException("You have not answered all the questions!!");
                }else {
                    break;
                }
            }
        }
        submitAnswers(answers,roll);
        answers.clear();
        System.out.println("All Questions Attempted!!");
    }

    public Integer calculateScore(Integer correctAnswers) {
        return correctAnswers*10;
    }

    public void addParticipants(String name,String roll) {
        Participant participant=new Participant(name,roll);
        participants.put(roll,participant);
        System.out.println("User added Successfully!!");
    }

    public void addInstrutor(String name,String instructorId) {
        Participant participant=new Participant(name,instructorId);
        instructors.put(instructorId,participant);
        System.out.println("User added successfully!!");
    }

    public void addParticipantsToFile() throws QuizException {
        List<String[]> users=new ArrayList<>();
        for(String i:participants.keySet()) {
            Participant participant=participants.get(i);
            String[] user={participant.getRoll(),participant.getName()};
            users.add(user);
        }
        csvQuizHandlerParticipant.writeToCSV(users);
    }

    public void addInstructorsToFile() throws QuizException {
        List<String[]> users=new ArrayList<>();
        for(String i:instructors.keySet()) {
            Participant participant=instructors.get(i);
            String[] user={participant.getRoll(),participant.getName()};
            users.add(user);
        }
        csvQuizHandlerInstructor.writeToCSV(users);
    }

    public boolean findParticipant(String roll) {
        return participants.containsKey(roll);
    }

    public void updateQuiz() throws QuizException {
        Scanner in = new Scanner(System.in);
        System.out.println("Available Questions are: ");
        for(Integer i:questions.keySet()) {
            System.out.println(i+" "+questions.get(i).getQuestion());
        }
        int questionNo,choice;
        System.out.println("Which question you want to update: ");
        questionNo=in.nextInt();
        in.nextLine();
        if(!questions.containsKey(questionNo)) {
            throw new QuizException("Question is not available!!");
        }
        System.out.println("Please choose anyone of the following: ");
        System.out.println("1. Update Question.");
        System.out.println("2. Update Answer.");
        choice=in.nextInt();
        in.nextLine();
        Question questionOb=questions.get(questionNo);
        switch (choice) {
            case 1:
                System.out.println("Enter the updated question: ");
                String question=in.nextLine();
                questionOb.setQuestion(question);
                questions.replace(questionNo,questionOb);
                System.out.println("Question Updated!!");
                break;
            case 2:
                System.out.println("Enter the updated answer: ");
                String answer=in.nextLine();
                questionOb.setAnswer(answer);
                questions.replace(questionNo,questionOb);
                System.out.println("Answer Updated!!");
                break;
            default:
                System.out.println("Wrong Choice!!");
                break;
        }
        System.out.println("Thank You!!");
    }
    public void deleteQuestion() throws QuizException {
        Scanner in = new Scanner(System.in);
        System.out.println("Available Questions are: ");
        for(Integer i:questions.keySet()) {
            System.out.println(i+" "+questions.get(i).getQuestion());
        }
        System.out.println("Select the question you want to delete: ");
        Integer questionNo=in.nextInt();
        if(!questions.containsKey(questionNo)) {
            throw new QuizException("Question is not available!!");
        }
        questions.remove(questionNo);
        System.out.println("Question is deleted from the quiz!!");
    }

    public String getQuestion(Integer questionNo) {
        return questions.get(questionNo).getQuestion();
    }

    public HashMap<Integer,Boolean> showResult(String roll) {
        return participants.get(roll).getCorrectSubmissions();
    }
}
