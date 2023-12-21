package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        HashMap<String,Quiz> quizzes=new HashMap<>();
        Quiz quiz = null,quiz1=null;
        try {
            quiz=new Quiz();
        } catch (QuizException e) {
            e.printStackTrace();
        }
        System.out.println("Welcome to Quiz Application...");
        int role;
        do {
            System.out.println("Please select your role: ");
            System.out.println("1. Instructor.");
            System.out.println("2. Participant.");
            System.out.println("3. Add Instructors.");
            System.out.println("4. Exit.");
            role = in.nextInt();
            in.nextLine();
            try {
                if (role == 1) {
                    System.out.println("Enter the Id of Instructor: ");
                    String id=in.nextLine();
                    if(quiz!=null && !quiz.findInstructor(id)) {
                        System.out.println("Instructor Not found!!");
                        continue;
                    }
                    int choice1;
                    System.out.println("Choose the following functions you want to Perform: ");
                    System.out.println("1. Add Quiz.");
                    System.out.println("2. Update Quiz.");
                    System.out.println("3. Delete Quiz.");
                    choice1 = in.nextInt();
                    in.nextLine();
                    switch (choice1) {
                        case 1: {
                            System.out.println("Enter Quiz Name: ");
                            String quizName = in.nextLine();
                            quizzes.put(quizName, new Quiz());
                            break;
                        }
                        case 2: {
                            System.out.println("Enter Quiz Name: ");
                            String quizName=in.nextLine();
                            quiz1=quizzes.get(quizName);
                            if(quiz1==null) {
                                System.out.println("Quiz Not Found!!");
                                continue;
                            }
                            int choice;
                            System.out.println("1. Add Questions.");
                            System.out.println("2. Update Questions.");
                            System.out.println("3. Delete Questions.");
                            System.out.println("4. Add Participants.");
                            System.out.println("5. Randomize Questions.");
                            choice = in.nextInt();
                            in.nextLine();
                            switch (choice) {
                                case 1:
                                    int flag = 1;
                                    while (flag == 1) {
                                        System.out.println("Enter the Question: ");
                                        String question = in.nextLine();
                                        System.out.println("Enter the Answer of the question: ");
                                        String answer = in.nextLine();
                                        System.out.println("Enter the correct explanation for this question: ");
                                        String explanation = in.nextLine();
                                        System.out.println("Enter the tags for this question: ");
                                        String tags = in.nextLine();
                                        quiz1.addQuestions(question, answer, explanation, tags);
                                        System.out.println("Do you want to add more Questions? (0 for No / 1 for Yes)");
                                        flag = in.nextInt();
                                        in.nextLine();
                                    }
                                    break;
                                case 2:
                                    quiz1.updateQuiz();
                                    break;
                                case 3:
                                    quiz1.deleteQuestion();
                                    break;
                                case 4:
                                    int flag1 = 1;
                                    while (flag1 == 1) {
                                        System.out.println("Enter the name of the participant: ");
                                        String name = in.nextLine();
                                        System.out.println("Enter the roll of the participant: ");
                                        String roll = in.nextLine();
                                        quiz1.addParticipants(name, roll);
                                        System.out.println("Do you want to add more Participants? (0 for No / 1 for Yes):");
                                        flag1 = in.nextInt();
                                        in.nextLine();
                                    }
                                    try {
                                        quiz1.addParticipantsToFile();
                                        System.out.println("All Participants added to File!!");
                                    } catch (QuizException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 5:
                                    quiz1.randomizeQuestions();
                                    System.out.println("Questions got Randomized!!");
                                    break;
                                default:
                                    System.out.println("Wrong Choice!!");
                                    break;
                            }
                            break;
                        }
                        case 3: {
                            System.out.println("Enter Quiz Name: ");
                            String quizName=in.nextLine();
                            quizzes.remove(quizName);
                            System.out.println("Quizzes deleted Successfully");
                        }
                        default:
                            System.out.println("Wrong Choice!!");
                            break;
                    }
                } else if (role == 2) {
                    System.out.println("Enter the Quiz Name you want to attempt: ");
                    String quizName=in.nextLine();
                    quiz1=quizzes.get(quizName);
                    System.out.println("Enter the roll of Participant: ");
                    String roll=in.nextLine();
                    if(quiz1!=null && !quiz1.findParticipant(roll)) {
                        System.out.println("User Not found!!");
                        continue;
                    }
                    int choice;
                    System.out.println("Choose the following functions you want to perform: ");
                    System.out.println("1. Take Quiz");
                    System.out.println("2. View Score");
                    System.out.println("3. View Leaderboard");
                    choice = in.nextInt();
                    switch (choice) {
                        case 1:
                            if(quiz1!=null) {
                                quiz1.takeQuizzes(roll);
                            }
                            break;
                        case 2:
                            HashMap<Integer,Boolean> correctAnswers=null;
                            if(quiz1!=null) {
                                correctAnswers=quiz1.showResult(roll);
                            }
                            if(quiz1!=null && correctAnswers!=null) {
                                System.out.printf("Total score: %d\n", quiz1.calculateScore(correctAnswers.size()));
                                System.out.println("Questions with examples: ");
                                HashMap<Integer,Question> questions=quiz1.getQuestions();
                                for (Integer i : questions.keySet()) {
                                    System.out.println("Question: "+questions.get(i).getQuestion());
                                    System.out.println("Verdict: "+correctAnswers.containsKey(i));
                                    System.out.println("Correct Answer: "+questions.get(i).getAnswer());
                                    System.out.println("Explanation: "+questions.get(i).getExplanation());
                                    System.out.println("Tag: "+questions.get(i).getTags());
                                }
                            }
                            break;
                        case 3:
                            HashMap<String,Integer> leaderboardParticipants=null;
                            if(quiz1!=null) {
                                leaderboardParticipants=quiz1.leaderBoard();
                            }
                            if(leaderboardParticipants!=null) {
                                System.out.println("Roll"+" "+"Score");
                                for (String i : leaderboardParticipants.keySet()) {
                                    System.out.println(i+" "+leaderboardParticipants.get(i));
                                }
                            }
                            break;
                        default:
                            System.out.println("Wrong Choice!!");
                            break;
                    }
                } else if(role==3) {
                    int flag = 1;
                    while (flag != 0) {
                        System.out.println("Enter name of the Instructor: ");
                        String name = in.nextLine();
                        System.out.println("Enter Id of the Instructor: ");
                        String id = in.nextLine();
                        if (quiz != null) {
                            quiz.addInstructor(name, id);
                        }
                        System.out.println("Do you want to add more instructors? (0 for no / 1 for yes)");
                        flag = in.nextInt();
                        in.nextLine();
                    }
                    try {
                        if (quiz != null) {
                            quiz.addInstructorsToFile();
                        }
                        System.out.println("All Instructors added to File!!");
                    } catch (QuizException e) {
                        e.printStackTrace();
                    }

                }  else if(role==4) {
                    System.out.println("Thank You!!");
                    System.exit(0);
                }else {
                    System.out.println("Invalid role selection!!");
                }
            } catch (QuizException exception) {
                System.out.println(exception.getMessage());
            }
        }while (role!=4);
    }
}