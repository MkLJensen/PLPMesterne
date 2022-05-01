import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {

    static Color parseFillColorInput(String input) {
        Pattern fillPattern = Pattern.compile("\\((.*)\\s([a-z]).*\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fillPattern.matcher(input);

        if(matcher.find()) {
            String character = matcher.group(2).toLowerCase();

            switch(character) {
                case "r":
                    return Color.red;
                case "g":
                    return Color.green;
                case "b":
                    return Color.blue;
            }

            return Color.black;
        } else {
            return Color.black;
        }
    }

    static List<Integer> extractCoordinate(String input) {
        Pattern textAtPattern = Pattern.compile("\\(TEXT-AT\\s\\((\\d+)\\s(\\d+)\\)\\s(.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = textAtPattern.matcher(input);

        if(matcher.find()) {
            List<Integer> res = new ArrayList<Integer>();
            res.add(Integer.parseInt(matcher.group(1)));
            res.add(Integer.parseInt(matcher.group(2)));
            return res;
        }

        return new ArrayList<Integer>();
    }

    static String extractText(String input) {
        Pattern textAtPattern = Pattern.compile("\\(TEXT-AT\\s\\(\\d+\\s\\d+\\)\\s(.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = textAtPattern.matcher(input);

        if(matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    static List<Integer> parseTwoCoordinateInput(String input) {
        List<Integer> res = new ArrayList<Integer>();
        Pattern p1 = Pattern.compile("\\(\\d+ \\d+\\) \\(\\d+ \\d+\\)");
        Matcher m1 = p1.matcher(input);

        if (m1.find()) {
            Pattern p2 = Pattern.compile("-?\\d+");
            Matcher m2 = p2.matcher(input);
            while (m2.find()) {
                res.add(Integer.parseInt(m2.group()));
            }
        }
        return res;
    }

    static List<Integer> parseThreeDigitInput(String input) {
        List<Integer> res = new ArrayList<Integer>();
        Pattern p1 = Pattern.compile("\\(\\d+ \\d+\\) \\d+");
        Matcher m1 = p1.matcher(input);

        if (m1.find()) {
            Pattern p2 = Pattern.compile("-?\\d+");
            Matcher m2 = p2.matcher(input);
            while (m2.find()) {
                res.add(Integer.parseInt(m2.group()));
            }
        }
        return res;
    }
}
