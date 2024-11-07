package com.ratings.rating.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratings.rating.model.Rating;



@Controller
public class LeetController {

    @GetMapping("/leet")
    public String getCodeforcesRatingForm() {
        return "leet";
    }

    @GetMapping("/fetchLeetRating")
    public String fetchLeetRating(@RequestParam String userId, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://alfa-leetcode-api.onrender.com/"+userId;
        try {
            Rating ratingObj = new Rating();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String jsonResponse = response.getBody();
            JsonNode root = objectMapper.readTree(jsonResponse);

            ratingObj.name = root.get("name").asText();
            ratingObj.setPlatform("CodeForces");
            ratingObj.setUserId(userId);
            ratingObj.picurl = root.get("avatar").asText();
            ratingObj.globrank = root.get("ranking").asInt();

            System.out.println("----asdasd----");
            
                        
            response = restTemplate.getForEntity("https://alfa-leetcode-api.onrender.com/"+userId+"/contest", String.class);
            jsonResponse = response.getBody();
            root = objectMapper.readTree(jsonResponse);
            ratingObj.setRating(root.get("contestRating").asInt());
            JsonNode root1 = root.get("contestParticipation");
            int oldrt = 0;
            
            for(JsonNode i:root1){
                ArrayList<String>  temp = new ArrayList<String>();
                temp.add(i.get("contest").get("title").asText());
                temp.add(i.get("rating").asText());
                temp.add(Integer.toString(i.get("rating").asInt()-oldrt));
                oldrt = i.get("rating").asInt();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                temp.add(sdf.format(new Date(i.get("contest").get("startTime").asLong()*1000)));
                // System.out.println(sdf.format(new Date(i.get("contest").get("startTime").asLong()*1000)));
                ratingObj.tbl.add(temp);
            }

            System.out.println("----asdasd----");
            
            response = restTemplate.getForEntity("https://alfa-leetcode-api.onrender.com/skillStats/"+userId, String.class);
            jsonResponse = response.getBody();
            root = objectMapper.readTree(jsonResponse);
            root1 = root.get("data").get("matchedUser").get("tagProblemCounts");
            for(JsonNode i:root1.get("intermediate")){
                ratingObj.prbl.put(i.get("tagName").asText(),i.get("problemsSolved").asInt());
            }
            for(JsonNode i:root1.get("fundamental")){
                ratingObj.prbl.put(i.get("tagName").asText(),i.get("problemsSolved").asInt());
            }
            for(JsonNode i:root1.get("advanced")){
                ratingObj.prbl.put(i.get("tagName").asText(),i.get("problemsSolved").asInt());
                // else if(i.get("verdict").asText().equalsIgnoreCase("SKIPPED")==false){
                //     ArrayList<String>  temp = new ArrayList<String>();
                //     temp.add(i.get("problem").get("contestId").asText());
                //     temp.add(i.get("problem").get("index").asText());
                //     for(JsonNode j:i.get("problem").get("tags")){
                //         temp.add(j.asText());
                //     }
                //     ratingObj.uns.add(temp);
                //     ratingObj.unss.put(i.get("problem").get("name").asText(),temp);

                // }
            }
            response = restTemplate.getForEntity("https://alfa-leetcode-api.onrender.com/"+userId+"/solved", String.class);
            jsonResponse = response.getBody();
            root = objectMapper.readTree(jsonResponse);
            ratingObj.totalprb = root.get("solvedProblem").asInt();


            response = restTemplate.getForEntity("https://alfa-leetcode-api.onrender.com/problems?limit=6", String.class);
            jsonResponse = response.getBody();
            root = objectMapper.readTree(jsonResponse);
            for(JsonNode i:root.get("problemsetQuestionList")){
                ArrayList<String>  temp = new ArrayList<String>();
                temp.add(i.get("titleSlug").asText());
                for(JsonNode j:i.get("topicTags")){
                    temp.add(j.get("name").asText());
                }
                ratingObj.uns.add(temp);
                ratingObj.unss.put(i.get("title").asText(),temp);
            }


            System.out.println("----asdasd----");

            model.addAttribute("rating", ratingObj);
            return "leet";

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("error", "Unable to fetch rating. Please try again.");
        return "leet";
    }
}






