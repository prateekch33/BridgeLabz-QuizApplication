package org.example;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Quiz {
    private final HashMap<Integer, Question> questions = new HashMap<>();
    private final HashMap<Integer, Boolean> correctAnswers = new HashMap<>();
    private final HashMap<String, Participant> participants = new HashMap<>();
    private final HashMap<String, Participant> instructors = new HashMap<>();
    private final HashMap<Integer, String> answers = new HashMap<>();
    private Integer questionCount = 1;
    String filePathParticipant = "Participants.csv";
    String filePathInstructor = "Instructors.csv";
    CSVQuizHandler csvQuizHandlerParticipant;
    CSVQuizHandler csvQuizHandlerInstructor;
    int quizDuration;
    Timer timer = new Timer();

    public Quiz() throws QuizException {
        this.csvQuizHandlerInstructor = new CSVQuizHandler(filePathInstructor);
        this.csvQuizHandlerParticipant = new CSVQuizHandler(filePathParticipant);
    }

    public void addQuestions(String question, String answer, String explanation, String tags) {
        Question questionOb = new Question(question, answer, explanation, tags);
        questions.put(questionCount++, questionOb);
        System.out.println("Question Successfully Added!!");
    }

    public void submitAnswers(HashMap<Integer, String> answers, String roll) throws QuizException {
        if (!participants.containsKey(roll)) {
            throw new QuizException("Participant is not Registered!!");
        }
        for (Integer i : answers.keySet()) {
            String answer = answers.get(i);
            if (answer.compareTo(questions.get(i).getAnswer()) == 0) {
                correctAnswers.put(i, true);
            } else {
                participants.get(roll).setCorrectSubmissions(correctAnswers);
                throw new QuizException("Incorrect Answer!!");
            }
        }
        participants.get(roll).setCorrectSubmissions(correctAnswers);
    }

    public void answerQuestion(Integer i, String answer) {
        answers.put(i, answer);
    }

    public Integer getQuestionCount() {
        return questions.size();
    }

    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }

    public void takeQuizzes(String roll) throws QuizException {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    for (Integer i : questions.keySet()) {
        System.out.println("Q" + i + " " + questions.get(i).getQuestion());
        System.out.println("Enter your Answer: ");

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return new Scanner(System.in).nextLine();
            }
        });

        Thread timerThread = new Thread(() -> {
            try {
                Thread.sleep(10000);
                if (!answers.containsKey(i)) {
                    System.out.println("Time's up for question " + i + "! Moving to the next question.");
                    answerQuestion(i, "");
                    future.cancel(true);
                }
            } catch (InterruptedException e) {
                // The user answered in time, so we don't need to do anything
            }
        });
        timerThread.start();

        try {
            String answer = future.get();
            timerThread.interrupt();
            answerQuestion(i, answer);
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            // The future was cancelled because the time was up, so we don't need to do anything
        }
    }

    executorService.shutdown();


    try {
        submitAnswers(answers, roll);
    } catch (QuizException e) {
        e.printStackTrace();
    }
    answers.clear();
    System.out.println("All Questions Attempted!!");
}

    public Integer calculateScore(Integer correctAnswers) {
        return correctAnswers * 10;
    }

    public void addParticipants(String name, String roll) {
        Participant participant = new Participant(name, roll);
        participants.put(roll, participant);
        System.out.println("User added Successfully!!");
    }

    public void addInstructor(String name, String instructorId) {
        Participant participant = new Participant(name, instructorId);
        instructors.put(instructorId, participant);
        System.out.println("User added successfully!!");
    }

    public void addParticipantsToFile() throws QuizException {
        List<String[]> users = new ArrayList<>();
        for (String i : participants.keySet()) {
            Participant participant = participants.get(i);
            String[] user = { participant.getRoll(), participant.getName() };
            users.add(user);
        }
        csvQuizHandlerParticipant.writeToCSV(users);
    }

    public void addInstructorsToFile() throws QuizException {
        List<String[]> users = new ArrayList<>();
        for (String i : instructors.keySet()) {
            Participant participant = instructors.get(i);
            String[] user = { participant.getRoll(), participant.getName() };
            users.add(user);
        }
        csvQuizHandlerInstructor.writeToCSV(users);
    }

    public boolean findParticipant(String roll) {
        return participants.containsKey(roll);
    }

    public boolean findInstructor(String id) {
        return instructors.containsKey(id);
    }

    public void updateQuiz() throws QuizException {
        Scanner in = new Scanner(System.in);
        System.out.println("Available Questions are: ");
        for (Integer i : questions.keySet()) {
            System.out.println(i + " " + questions.get(i).getQuestion());
        }
        int questionNo, choice;
        System.out.println("Which question you want to update: ");
        questionNo = in.nextInt();
        in.nextLine();
        if (!questions.containsKey(questionNo)) {
            throw new QuizException("Question is not available!!");
        }
        System.out.println("Please choose anyone of the following: ");
        System.out.println("1. Update Question.");
        System.out.println("2. Update Answer.");
        System.out.println("3. Update Explanation.");
        System.out.println("4. Update Tags");
        choice = in.nextInt();
        in.nextLine();
        Question questionOb = questions.get(questionNo);
        switch (choice) {
            case 1:
                System.out.println("Enter the updated question: ");
                String question = in.nextLine();
                questionOb.setQuestion(question);
                questions.replace(questionNo, questionOb);
                System.out.println("Question Updated!!");
                break;
            case 2:
                System.out.println("Enter the updated answer: ");
                String answer = in.nextLine();
                questionOb.setAnswer(answer);
                questions.replace(questionNo, questionOb);
                System.out.println("Answer Updated!!");
                break;
            case 3:
                System.out.println("Enter the correct explanation for this question: ");
                String explanation = in.nextLine();
                questionOb.setExplanation(explanation);
                questions.replace(questionNo, questionOb);
                break;
            case 4:
                System.out.println("Enter the tags for this question: ");
                String tags = in.nextLine();
                questionOb.setExplanation(tags);
                questions.replace(questionNo, questionOb);
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
        for (Integer i : questions.keySet()) {
            System.out.println(i + " " + questions.get(i).getQuestion());
        }
        System.out.println("Select the question you want to delete: ");
        Integer questionNo = in.nextInt();
        if (!questions.containsKey(questionNo)) {
            throw new QuizException("Question is not available!!");
        }
        questions.remove(questionNo);
        System.out.println("Question is deleted from the quiz!!");
    }

    public String getQuestion(Integer questionNo) {
        return questions.get(questionNo).getQuestion();
    }

    public HashMap<String, Integer> leaderBoard() {
        HashMap<String, Integer> leaderboardParticipants = new HashMap<>();
        for (String i : participants.keySet()) {
            leaderboardParticipants.put(i, calculateScore(participants.get(i).getCorrectSubmissions().size()));
        }
        List<Map.Entry<String, Integer>> list = new LinkedList<>(leaderboardParticipants.entrySet());

        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        for (Map.Entry<String, Integer> i : list) {
            leaderboardParticipants.put(i.getKey(), i.getValue());
        }
        return leaderboardParticipants;
    }

    public void randomizeQuestions() {
        List<Map.Entry<Integer, Question>> list = new LinkedList<>(questions.entrySet());
        Collections.shuffle(list);
        for (Map.Entry<Integer, Question> i : list) {
            questions.put(i.getKey(), i.getValue());
        }
    }

    public HashMap<Integer, Boolean> showResult(String roll) {
        return participants.get(roll).getCorrectSubmissions();
    }
}
