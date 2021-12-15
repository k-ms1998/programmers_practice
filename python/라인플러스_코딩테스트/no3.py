# p = [[0,10], [10,15], [20,30]]
p = [[5,15], [0,10]]
n = len(p)

maxD = 0
for pp in p:
    if maxD < pp[1]:
        maxD = pp[1]
dp = [[0 for _ in range(maxD)] for __ in range(n)]

for i in range(n):
    cp = p[i]
    for j in range(cp[0], cp[1]):
        dp[i][j] = 1
for dpp in dp:
    print(dpp)

maxT = 0
for x in range(maxD):
    cnt = 0
    for y in range(n):
        if dp[y][x] == 1:
            cnt += 1
    if cnt > maxT:
        maxT = cnt
print(maxT)