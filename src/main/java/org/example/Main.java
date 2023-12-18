package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Quiz quiz = null;
        try {
             quiz=new Quiz();
        }catch(QuizException e) {
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
                    int choice;
                    System.out.println("Choose the following functions you want to Perform: ");
                    System.out.println("1. Add Questions.");
                    System.out.println("2. Update Questions.");
                    System.out.println("3. Delete Questions.");
                    System.out.println("4. Add Participants.");
                    choice = in.nextInt();
                    in.nextLine();
                    switch (choice) {
                        case 1:
                            int flag=1;
                            while(flag==1) {
                                System.out.println("Enter the Question: ");
                                String question = in.nextLine();
                                System.out.println("Enter the Answer of the question: ");
                                String answer = in.nextLine();
                                if(quiz!=null) {
                                    quiz.addQuestions(question, answer);
                                }
                                System.out.println("Do you want to add more Questions? (0 for No / 1 for Yes)");
                                flag = in.nextInt();
                                in.nextLine();
                            }
                            break;
                        case 2:
                            if(quiz!=null) {
                                quiz.updateQuiz();
                            }
                            break;
                        case 3:
                            if(quiz!=null) {
                                quiz.deleteQuestion();
                            }
                            break;
                        case 4:
                            int flag1=1;
                            while(flag1==1) {
                                System.out.println("Enter the name of the participant: ");
                                String name=in.nextLine();
                                System.out.println("Enter the roll of the participant: ");
                                String roll=in.nextLine();
                                if(quiz!=null) {
                                    quiz.addParticipants(name, roll);
                                }
                                System.out.println("Do you want to add more Participants? (0 for No / 1 for Yes):");
                                flag1= in.nextInt();
                                in.nextLine();
                            }
                            try {
                                if(quiz!=null) {
                                    quiz.addParticipantsToFile();
                                }
                                System.out.println("All Participants added to File!!");
                            } catch (QuizException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            System.out.println("Wrong Choice!!");
                            break;
                    }
                } else if (role == 2) {
                    System.out.println("Enter the roll of Participant: ");
                    String roll=in.nextLine();
                    if(quiz!=null && !quiz.findParticipant(roll)) {
                        System.out.println("User Not found!!");
                        return;
                    }
                    int choice;
                    System.out.println("Choose the following functions you want to perform: ");
                    System.out.println("1. Take Quiz");
                    System.out.println("2. View Score");
                    choice = in.nextInt();
                    switch (choice) {
                        case 1:
                            if(quiz!=null) {
                                quiz.takeQuizzes(roll);
                            }
                            break;
                        case 2:
                            HashMap<Integer,Boolean> correctAnswers=null;
                            if(quiz!=null) {
                                correctAnswers=quiz.showResult(roll);
                            }
                            if(quiz!=null && correctAnswers!=null) {
                                System.out.printf("Total score: %d\n", quiz.calculateScore(correctAnswers.size()));
                                System.out.println("Correctly Answered Questions: ");
                                for (Integer i : correctAnswers.keySet()) {
                                    System.out.println(quiz.getQuestion(i));
                                }
                            }
                            break;
                        default:
                            System.out.println("Wrong Choice!!");
                            break;
                    }
                } else if(role==3) {
                    int flag=1;
                    while(flag!=0) {
                        System.out.println("Enter name of the Instructor: ");
                        String name = in.nextLine();
                        System.out.println("Enter Id of the Instructor: ");
                        String id = in.nextLine();
                        if (quiz != null) {
                            quiz.addInstrutor(name, id);
                        }
                        System.out.println("Do you want to add more instructors? (0 for no / 1 for yes)");
                        flag=in.nextInt();
                        in.nextLine();
                    }
                    try {
                        if(quiz!=null) {
                            quiz.addInstructorsToFile();
                        }
                        System.out.println("All Instructors added to File!!");
                    } catch (QuizException e) {
                        e.printStackTrace();
                    }
                } else if(role==4) {
                    System.out.println("Thank you!!");
                }else {
                    System.out.println("Invalid role selection!!");
                }
            } catch (QuizException exception) {
                System.out.println(exception.getMessage());
            }
        }while (role!=4);
    }
}