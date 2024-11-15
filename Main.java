import java.util.*;

public class Main{
    static class Pos {
        int x_cord, y_cord;
        
        Pos(int x_cord, int y_cord){
            this.x_cord = x_cord;
            this.y_cord = y_cord;
        }
        
        @Override
        public boolean equals(Object obj){
            if (this == obj) return true;
            
            if(obj == null || getClass() != obj.getClass()) return false;
            
            Pos point = (Pos) obj;
            
            return x_cord == point.x_cord && y_cord == point.y_cord; 
        }
        
        @Override
        public int hashCode(){
            return Objects.hash(x_cord, y_cord);
        }
    }
    
    static class line_seg {
        Pos start, end;
        
        line_seg(Pos start, Pos end) {
            this.start = start;
            this.end = end;
        }
    }
    
    static Map<Pos, List<Pos>> adj_list = new HashMap<>();
    static Set<Pos> visited = new HashSet<>();
    
    public static void add(Pos p1, Pos p2){
        adj_list.computeIfAbsent(p1, k -> new ArrayList<>()).add(p2);
        adj_list.computeIfAbsent(p2, k -> new ArrayList<>()).add(p1);
    }
    
    public static boolean dfs(Pos curr, Pos parent, Pos begin){
        visited.add(curr);
        
        for(Pos negh : adj_list.getOrDefault(curr, new ArrayList<>())){
            if(!negh.equals(parent)) {
                if(visited.contains(negh)){
                    if(negh.equals(begin)) return true;
                }else if (dfs(negh, curr, begin)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public static int count_shapes(){
        int count = 0;
        
        for(Pos point : adj_list.keySet()) {
            if(!visited.contains(point)) {
                if(dfs(point, null, point)) {
                    count ++;
                }
            }
        }
        
        return count;
    }
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        List<line_seg> seg = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            int x1_cord = sc.nextInt();
            int y1_cord = sc.nextInt();
            int x2_cord = sc.nextInt();
            int y2_cord = sc.nextInt();

            Pos p1 = new Pos(x1_cord, y1_cord);
            Pos p2 = new Pos(x2_cord, y2_cord);

            seg.add(new line_seg(p1, p2));
            add(p1, p2);
        }

        System.out.print(count_shapes());
    }
}
