```java
class Solution {
    public boolean exist(char[][] board, String word) {
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == word.charAt(0)){
                    boolean[][] visit = new boolean[board.length][board[0].length];
                    visit[i][j] = true;
                    if(dfs(board, word, visit, i, j, 1)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, String word, boolean[][] visit, int row, int col, int index){
        if(index == word.length()){
            return true;
        }

        boolean flag = false;
        int[][] dirs =new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
        for(int n = 0; n < 4; n++){
            int i = row+dirs[n][0];
            int j = col+dirs[n][1];
            if(i>=0&&i<board.length&&j>=0&&j<board[0].length
            &&board[i][j] == word.charAt(index)&&!visit[i][j]){
                visit[i][j] = true;
                flag = dfs(board, word, visit, i, j, index+1);
                if(flag){
                    return true;
                }
            }
        }
        visit[row][col] = false;//这一行代码最重要，是回溯的关键

        return flag;
    }
}
```

