#네트워크 (Level 3)


# computers = [[1, 1, 0], [1, 1, 0], [0, 0, 1]]
# computers = [[1, 1, 0], [1, 1, 1], [0, 1, 1]]
computers = [[1, 1, 0, 0, 0], [1, 1, 1, 0, 0], [0, 1, 1, 0, 0], [0, 0, 0, 1, 1], [1, 0, 0, 1, 1]]
n = len(computers)

def dfs(v, node, computers, c):
    for i in range(len(node)):
        if node[i] == 1 and v[i] == 0:
            c.append(i)
            v[i] = 1
            dfs(v, computers[i], computers, c)
    return c
def solution(n, computers):
    answer = 0

    v = [0]*n
    c = []
    for i in range(n):
        c.append(dfs(v, computers[i], computers, []))
    
    for cc in c:
        if len(cc) >= 1:
            answer += 1

    return answer

print(solution(n, computers))