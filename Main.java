import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        Iterator<String> followersIterator = getFollowers("followers_1.json").iterator();
        ArrayList<String> followerListLink = new ArrayList<>();
        while (followersIterator.hasNext()) {
            followerListLink.add(parseLink(followersIterator.next()));
        }

        Iterator<String> followingIterator = getFollowering("following.json").iterator();
        ArrayList<String> followingListLink = new ArrayList<>();
        while (followingIterator.hasNext()) {
            followingListLink.add(parseLink(followingIterator.next()));
        }
        // follower exist in following
        ArrayList<String> followBackList = new ArrayList<>();
        for (int i = 0; i < followingListLink.size(); i++) {
            if (!followerListLink.contains(followingListLink.get(i))) {
                followBackList.add(followingListLink.get(i));
            }
        }

        Iterator it = followBackList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static ArrayList<String> getFollowers(String fileLocation)
            throws FileNotFoundException, IOException, ParseException {
        ArrayList<String> info = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(fileLocation));
        JSONArray data = (JSONArray) obj;

        Iterator<Object> it = data.iterator();
        while (it.hasNext()) {
            info.add(it.next().toString());
        }
        return info;
    }

    public static ArrayList<String> getFollowering(String fileLocation)
            throws FileNotFoundException, IOException, ParseException {
        ArrayList<String> info = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(fileLocation));
        JSONArray data = (JSONArray) obj.get("relationships_following");

        Iterator<Object> it = data.iterator();
        while (it.hasNext()) {
            info.add(it.next().toString());
        }
        return info;
    }

    public static void parse(ArrayList<String> list) {
        list.forEach((userData) -> System.out.println(userData));
    }

    public static String parseLink(String input) {
        input = input.replace(input.substring(0, input.indexOf("https:")), "");
        input = input.replace(input.substring(input.indexOf("\",\"")), "");
        return input;
    }

    public static String parseUsername(String link) {
        return link.replace("https:\\/\\/www.instagram.com\\/", "@");
    }
}