#도둑질 (Level 4)

# money = [1, 2, 3, 1]
money = [90,0,0,95,1,1]
# money = [1000,1,0,1,2,1000,0]


def solution(money):
    size = len(money)+1
    dpA = [0 for _ in range(size)]
    dpB = [0 for _ in range(size)]

    dpA[1] = money[0]
    dpB[2] = money[1]

    for x in range(2,size-1):
        dpA[x] = max(dpA[x-1], dpA[x-2]+money[x-1])    
    for x in range(2,size):
        dpB[x] = max(dpB[x-1], dpB[x-2]+money[x-1])    
    return max(max(dpA), max(dpB))

print(solution(money))