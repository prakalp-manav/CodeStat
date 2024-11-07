package com.ratings.rating.controller;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
public class CodechefController {

    @GetMapping("/codechef")
    public String getCodeChefRatingForm() {
        return "codechef";
    }

    @GetMapping("/fetchCodechefRating")
    public String fetchCodechefRating(@RequestParam String userId, Model model) {
        String url = "https://www.codechef.com/users/" + userId;
        String urll = "https://codechef-api.vercel.app/" + userId;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Rating ratingObj = new Rating();
            Document doc = Jsoup.connect(url).get();
            ResponseEntity<String> response = restTemplate.getForEntity(urll, String.class);
            String jsonResponse = response.getBody();
            JsonNode root = objectMapper.readTree(jsonResponse);

            String ratingStr = root.get("currentRating").asText();
            int rating = Integer.parseInt(ratingStr);


            JsonNode te = root.get("ratingData");
            ratingObj.success = root.get("success").asBoolean();

            for(JsonNode i:te){
                ArrayList<String>  temp = new ArrayList<String>();
                temp.add(i.get("code").asText());
                temp.add(i.get("rating").asText());
                temp.add(i.get("end_date").asText());
                temp.add(i.get("name").asText());
                ratingObj.tbl.add(temp);
            }





            int temp = doc.select("span.rating").text().charAt(0)-'0';
            for(int i = 0; i<temp; i++){
                ratingObj.stars += "★";
            }
            ratingObj.name = root.get("name").asText();
            ratingObj.strsty = doc.select("div.rating-star span").attr("style");
            ratingObj.setPlatform("CodeChef");
            ratingObj.setUserId(userId);
            ratingObj.setRating(rating);
            ratingObj.picurl = root.get("profile").asText();
            model.addAttribute("rating", ratingObj);
            return "codechef";

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("error", "Unable to fetch rating. Please try again.");
        return "codechef";
    }
}
