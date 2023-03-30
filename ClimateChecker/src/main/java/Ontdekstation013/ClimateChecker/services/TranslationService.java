package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.repositories.TranslationRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;


@Service
public class TranslationService {
    private TranslationRepository translationRepository;

    String jsonFile = "./Ontdekstation013/ClimateChecker/TranslationFile.Json";

    @Autowired
    public TranslationService(TranslationRepository _translationRepository){
        this.translationRepository = _translationRepository;
    }

    public JsonFormat getNederlandsPage(String PageID){
        // parsing file "JSONExample.json"
        Object obj = new JSONParser(new FileReader("TranslationFile.Json"));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting firstName and lastName
        String firstName = (String) jo.get("firstName");
        String lastName = (String) jo.get("lastName");

        System.out.println(firstName);
        System.out.println(lastName);

        // getting age
        long age = (long) jo.get("age");
        System.out.println(age);

        // getting address
        Map address = ((Map)jo.get("address"));

        // iterating address Map
        Iterator<Map.Entry> itr1 = address.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }

        // getting phoneNumbers
        JSONArray ja = (JSONArray) jo.get("phoneNumbers");

        // iterating phoneNumbers
        Iterator itr2 = ja.iterator();

        while (itr2.hasNext())
        {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
        }
    }


}
