package br.com.java.rest.api.request.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lucas
 */
public class RestApiRequestApplication {

    public static void main(String[] args) throws MalformedURLException, IOException {
        int asTeam1 = getTotalGoals(2014, "team1", "Tottenham Hotspur", 1);
        int asTeam2 = getTotalGoals(2014, "team2", "Tottenham Hotspur", 1);

        System.out.println(asTeam1 + asTeam2);
    }
    
    public static int calculateTotalGoals(int year, String team) {
        try {
            int asTeam1 = getTotalGoals(year, "team1", team, 1);
            int asTeam2 = getTotalGoals(year, "team2", team, 1);

            return asTeam1 + asTeam2;
        } catch(MalformedURLException malformedURLException) {
            System.out.println(malformedURLException.getMessage());
        } catch(IOException iOException) {
            System.out.println(iOException.getMessage());
        }
        
        return -1;
    }

    private static String extractInformation(int year, String match, String team, int page) throws MalformedURLException, IOException {
        team = team.replace(" ", "%20");

        String url = String.format("https://jsonmock.hackerrank.com/api/football_matches?year=%d&%s=%s&page=%d",
                year, match, team, page);

        URL conn = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) conn.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder builder = new StringBuilder();
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;

        while ((line = input.readLine()) != null) {
            builder.append(line);
        }

        input.close();

        return builder.toString();
    }

    private static int getTotalPages(String pageInformation) {
        Pattern pattern = Pattern.compile("\"total_pages\"");
        Matcher matcher = pattern.matcher(pageInformation);

        matcher.find();

        int initialIndex = matcher.end();
        String page = "";

        while (pageInformation.charAt(++initialIndex) != ',') {
            page += pageInformation.charAt(initialIndex);
        }

        return Integer.parseInt(page);
    }

    private static int getTotalGoals(int year, String match, String team, int actualPage) throws MalformedURLException, IOException {
        String information = extractInformation(year, match, team, actualPage);
        int totalPages = getTotalPages(information), totalGoals = 0;

        while (actualPage <= totalPages) {
            Pattern pattern = Pattern.compile(match.equals("team1") ? "team1goals" : "team2goals");
            Matcher matcher = pattern.matcher(information);

            while (matcher.find()) {
                int index = matcher.end() + 1;

                char character;
                String sequence = "";

                while ((character = information.charAt(++index)) != ',') {
                    if (character == '}') {
                        break;
                    }

                    if (character != '\"') {
                        sequence += character;
                    }
                }

                totalGoals += Integer.parseInt(sequence);
            }

            actualPage++;
            information = extractInformation(year, match, team, actualPage);
        }

        return totalGoals;
    }

}
