/**
 * 100점/100점
 */
class Q1 {

    static int n;
    static int[][] answer;

    public int[][] solution(int[][] image) {
        n = image.length;

        answer = new int[2*n][2*n];
        firstQuad(image);
        secondQuad(image);
        thirdQuad(image);
        fourthQuad(image);

        return answer;
    }

    public static void firstQuad(int[][] image){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                answer[y][x] = image[y][x];
            }
        }
    }

    public static void secondQuad(int[][] image){
        for(int y = 0; y < n; y++){
            for(int x = n - 1; x >= 0; x--){
                answer[y][(n - 1 - x) + n] = image[y][x];
            }
        }
    }

    public static void thirdQuad(int[][] image){
        for(int y = n - 1; y >= 0; y--){
            for(int x = 0; x < n; x++){
                answer[(n - 1 - y) + n][x] = image[y][x]; // 
            }
        }
    }

    public static void fourthQuad(int[][] image){
        for(int y = n - 1; y >= 0; y--){
            for(int x = 0; x < n; x++){
                answer[(n - 1 - y) + n][(n - 1 - x) + n] = image[y][x];
            }
        }
    }
}