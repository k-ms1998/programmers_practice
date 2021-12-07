#등굣길 (Level 3)

m = 4
n = 3
puddles = [[2, 2]]
# puddles = [[2,2],[3,2],[2,1],[3,1]]
# puddles = [[1,3],[3,1],[3,2],[3,3]]
# puddles = [[1,2], [2,1]]

def solution(m, n, puddles):
    grid = [[0 for _ in range(m+1)] for __ in range(n+1)]
    while puddles:
        p = puddles.pop()
        grid[p[1]][p[0]] = -1
    grid[1][1] = 1

    for y in range(1,n+1):
        for x in range(1,m+1):
            if grid[y][x] == -1 or (y==1 and x==1):
                continue

            if grid[y][x-1] == -1:
                grid[y][x] = grid[y-1][x]
                continue
            if grid[y-1][x] == -1:
                grid[y][x] = grid[y][x-1]
                continue
            
            grid[y][x] = (grid[y-1][x] + grid[y][x-1])

    # for g in grid:
    #     print(g)

    return grid[n][m]%1000000007

print(solution(m,n,puddles))