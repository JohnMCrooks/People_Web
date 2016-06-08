package com.crooks;

import jdk.nashorn.internal.ir.WhileNode;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Person> peopleList = parsePeople();

        System.out.println(peopleList.toString());

        Spark.init();
        Spark.get(
                "/",
                (request, response) -> {
                    HashMap m = new HashMap();

                    ArrayList<Person> subset = new ArrayList<Person>();
                    for (Person person: peopleList) {
                        if (person.id<=20){
                            subset.add(person);
                        }
                    }

                    m.put("peopleList", subset);
                    return new ModelAndView(m,"index.html");



                },
                new MustacheTemplateEngine()

        );

//        Spark.get(
//                "/person",
//                (request, response) -> {
//                    HashMap m = new HashMap();

//                    String idStr = request.queryParams("id");
//                    int id = Integer.valueOf(idStr);
//                    if(user.id == id){
//
//                    }
//
//                    m.put("id",id);
//                    m.put("Person", );
//                    return new ModelAndView(m,"index.html");
//                },
//                new MustacheTemplateEngine()
//        );

    }

    public static ArrayList<Person> parsePeople() throws FileNotFoundException {
        File f = new File("people.csv");
        ArrayList<Person> peopleArray = new ArrayList<>();
        Scanner scanner= new Scanner(f);
        while(scanner.hasNext()) {
            String[] personString = scanner.nextLine().split(",");
            Person p1 = new Person(Integer.valueOf(personString[0]), personString[1], personString[2], personString[3], personString[4], personString[5]);
            peopleArray.add(p1);
        }
        return peopleArray;
    }
}
