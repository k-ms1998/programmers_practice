# brown = 10
# yellow = 2

brown = 8
yellow = 1

# brown = 24
# yellow = 24

def solution(brown, yellow):
    answer = []

    posYel = []
    
    if yellow == 1:
        posYel = [[1,1]]
    else:
        for i in range(1, yellow//2+1):
            if yellow%i == 0:
                posYel.append([i, yellow//i])

    for p in posYel:
        posBro = 2*(p[0]+p[1])+4
        if posBro == brown:
            if p[0] < p[1]:
                tmp = p[0]
                p[0] = p[1]
                p[1] = tmp
            return [p[0]+2, p[1]+2]

    return answer

print(solution(brown, yellow))