import java.util.regex.Pattern;

public class PatternMatcher {
    enum CommandType {
        Line,
        Circle,
        Rectangle,
        FillCircle,
        FillRectangle,
        BoundingBox,
        TextAt,
        Clear,
        Draw,
        BarChart,
        PieChart,
        Unknown
    }

    static Pattern objectPattern = Pattern.compile("(\\([^0-9\\s]*\\s\\(\\d+\\s\\d+\\)\\s\\d+\\))|(\\([^0-9\\s]*\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\))|(\\(FILL-CIRCLE\\s\\w\\s\\(\\d+\\s\\d+\\)\\s\\d+\\))|(\\(FILL-RECTANGLE\\s\\w\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\))", Pattern.CASE_INSENSITIVE);

    private static Pattern drawPattern = Pattern.compile("\\(DRAW\\s(.*)\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern recPattern = Pattern.compile("\\(RECTANGLE\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern circPattern = Pattern.compile("\\(CIRCLE\\s\\(\\d+\\s\\d+\\)\\s\\d+\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern linePattern = Pattern.compile("\\(LINE\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern filledRecPattern = Pattern.compile("\\(FILL-RECTANGLE\\s[a-z]\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern filledCircPattern = Pattern.compile("\\(FILL-CIRCLE\\s[a-z]\\s\\(\\d+\\s\\d+\\)\\s\\d+\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern bbPattern = Pattern.compile("\\(BOUNDING-BOX\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern textAtPattern = Pattern.compile("\\(TEXT-AT\\s\\(\\d+\\s\\d+\\)\\s.*\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern clrPattern = Pattern.compile("(CLR)", Pattern.CASE_INSENSITIVE);
    private static Pattern barChartPattern = Pattern.compile("BARCHART", Pattern.CASE_INSENSITIVE);
    private static Pattern pieChartPattern = Pattern.compile("PIECHART", Pattern.CASE_INSENSITIVE);

    public static CommandType matchCommand(String input) {
        if(drawPattern.matcher(input).find()) {
            return CommandType.Draw;
        }else if (recPattern.matcher(input).find()) {
            return CommandType.Rectangle;
        } else if (circPattern.matcher(input).find()) {
            return CommandType.Circle;
        } else if (linePattern.matcher(input).find()) {
            return CommandType.Line;
        } else if (filledCircPattern.matcher(input).find()) {
            return CommandType.FillCircle;
        } else if (filledRecPattern.matcher(input).find()) {
            return CommandType.FillRectangle;
        } else if (bbPattern.matcher(input).find()) {
            return CommandType.BoundingBox;
        } else if (textAtPattern.matcher(input).find()) {
            return CommandType.TextAt;
        } else if (clrPattern.matcher(input).find()) {
            return CommandType.Clear;
        } else if (barChartPattern.matcher(input).find()) {
            return CommandType.BarChart;
        }else if (pieChartPattern.matcher(input).find()) {
            return CommandType.PieChart;
        } else {
            return CommandType.Unknown;
        }
    }
}