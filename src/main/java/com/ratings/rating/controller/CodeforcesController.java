package com.ratings.rating.controller;

import java.util.ArrayList;

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
public class CodeforcesController {

    @GetMapping("/codeforces")
    public String getCodeforcesRatingForm() {
        return "codeforces";
    }

    @GetMapping("/fetchCodeforcesRating")
    public String fetchCodeforcesRating(@RequestParam String userId, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://codeforces.com/api/user.info?handles="+userId;
        try {
            Rating ratingObj = new Rating();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String jsonResponse = response.getBody();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode root1 = root.get("result");
            
            for(JsonNode i:root1){
                
                // System.out.println(i.get("firstName").asText());
                if(i.get("firstName")!=null){
                    ratingObj.name = i.get("firstName").asText()+" "+i.get("lastName").asText();
                }
                
                ratingObj.setPlatform("CodeForces");
                ratingObj.setUserId(userId);
                ratingObj.setRating(i.get("rating").asInt());
                ratingObj.picurl = i.get("titlePhoto").asText();
                ratingObj.cfleag = i.get("maxRank").asText();
            }
            
            response = restTemplate.getForEntity("https://codeforces.com/api/user.rating?handle="+userId, String.class);
            jsonResponse = response.getBody();
            root = objectMapper.readTree(jsonResponse);
            root1 = root.get("result");
            for(JsonNode i:root1){
                ArrayList<String>  temp = new ArrayList<String>();
                temp.add(i.get("contestId").asText());
                temp.add(i.get("newRating").asText());
                temp.add(Integer.toString(i.get("newRating").asInt()-i.get("oldRating").asInt()));
                temp.add(i.get("contestName").asText());
                ratingObj.tbl.add(temp);
            }
            
            response = restTemplate.getForEntity("https://codeforces.com/api/user.status?handle="+userId, String.class);
            jsonResponse = response.getBody();
            root = objectMapper.readTree(jsonResponse);
            root1 = root.get("result");
            for(JsonNode i:root1){
                if(i.get("verdict").asText().equalsIgnoreCase("OK")){
                    for(JsonNode j:i.get("problem").get("tags")){
                        int count = ratingObj.prbl.containsKey(j.asText())?ratingObj.prbl.get(j.asText()) : 0;
                        ratingObj.prbl.put(j.asText(),count+1);
                    }
                    ratingObj.totalprb++;
                }
                else if(i.get("verdict").asText().equalsIgnoreCase("SKIPPED")==false){
                    ArrayList<String>  temp = new ArrayList<String>();
                    temp.add(i.get("problem").get("contestId").asText());
                    temp.add(i.get("problem").get("index").asText());
                    for(JsonNode j:i.get("problem").get("tags")){
                        temp.add(j.asText());
                    }
                    ratingObj.uns.add(temp);
                    ratingObj.unss.put(i.get("problem").get("name").asText(),temp);

                }
            }

            model.addAttribute("rating", ratingObj);
            return "codeforces";

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("error", "Unable to fetch rating. Please try again.");
        return "codeforces";
    }
}






