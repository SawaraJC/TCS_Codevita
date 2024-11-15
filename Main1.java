import java.util.*;

public class Main1 {

    public static boolean onSegment(double x, double y, double x1, double y1, double x2, double y2) {
        return Math.min(x1, x2) <= x && x <= Math.max(x1, x2) && Math.min(y1, y2) <= y && y <= Math.max(y1, y2);
    }

    public static double[] lineIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double denom = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (denom == 0) {
            return null;
        }
        double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denom;
        double y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denom;
        if (onSegment(x, y, x1, y1, x2, y2) && onSegment(x, y, x3, y3, x4, y4)) {
            return new double[]{x, y};
        }
        return null;
    }

    public static int calculateIntensity(double x, double y, List<int[]> lines) {
        List<Double> intensities = new ArrayList<>();
        for (int[] line : lines) {
            int x1 = line[0], y1 = line[1], x2 = line[2], y2 = line[3];
            if (x1 == x2) {
                intensities.add(Math.min(Math.abs(y - y1), Math.abs(y2 - y)));
            } else if (y1 == y2) {
                intensities.add(Math.min(Math.abs(x - x1), Math.abs(x2 - x)));
            } else {
                if (Math.abs(x - x1) == Math.abs(y - y1) && Math.abs(x - x2) == Math.abs(y - y2)) {
                    intensities.add(Math.min(Math.abs(x - x1), Math.abs(x2 - x)));
                }
            }
        }
        return intensities.isEmpty() ? 0 : (int)(double)Collections.min(intensities);
    }

    public static int findStarsAndCalculateIntensity(List<int[]> lines, int K) {
        Map<String, List<int[]>> intersections = new HashMap<>();
        int totalIntensity = 0;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                int[] line1 = lines.get(i);
                int[] line2 = lines.get(j);
                double[] intersection = lineIntersection(line1[0], line1[1], line1[2], line1[3],
                        line2[0], line2[1], line2[2], line2[3]);
                if (intersection != null) {
                    String pointKey = intersection[0] + "," + intersection[1];
                    if (!intersections.containsKey(pointKey)) {
                        intersections.put(pointKey, new ArrayList<>());
                    }
                    intersections.get(pointKey).add(line1);
                    intersections.get(pointKey).add(line2);
                }
            }
        }
        for (Map.Entry<String, List<int[]>> entry : intersections.entrySet()) {
            if (entry.getValue().size() == K) {
                double[] point = Arrays.stream(entry.getKey().split(","))
                        .mapToDouble(Double::parseDouble).toArray();
                totalIntensity += calculateIntensity(point[0], point[1], entry.getValue());
            }
        }
        return totalIntensity;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.nextLine();
        List<int[]> lines = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            lines.add(new int[]{x1, y1, x2, y2});
            sc.nextLine();
        }
        int K = sc.nextInt();
        int result = findStarsAndCalculateIntensity(lines, K);
        System.out.println(result);
    }
}
