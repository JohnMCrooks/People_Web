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

                    int offset = 0;
                    String offStr = request.queryParams("offset");

                    if(offStr!=null){
                        offset = Integer.valueOf(offStr);
                    }
                    int newOffset=offset+20;

                    ArrayList<Person> peopleSubset = new ArrayList<>(peopleList.subList(offset,newOffset));

                    m.put("peopleList", peopleSubset);
                    m.put("offsetup", offset+20);
                    m.put("offsetdown", offset-20);
                    return new ModelAndView(m,"index.html");



                },
                new MustacheTemplateEngine()

        );

        Spark.get(
                "/person",
                (request, response) -> {
                    HashMap m = new HashMap();

                    String idStr = request.queryParams("id");
                    int id = Integer.valueOf(idStr);
                    Person person = peopleList.get(id-1);

                    m.put("id",id);
                    m.put("person", person);
                    return new ModelAndView(m,"userinfo.html");
                },
                new MustacheTemplateEngine()
        );

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
