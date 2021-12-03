#모의고사 (Level 1)

# answers = [1,2,3,4,5]
answers = [1,3,2,4,2]

def solution(answers):
    answer = []
    
    p1 = [1,2,3,4,5]
    p2 = [2,1,2,3,2,4,2,5]
    p3 = [3,3,1,1,2,2,4,4,5,5]
    score = [0,0,0]
    pos = 0
    while answers:
        a = answers.pop(0)
        if a == p1[pos%len(p1)]:
            score[0] += 1
        if a == p2[pos%len(p2)]:
            score[1] += 1        
        if a == p3[pos%len(p3)]:
            score[2] += 1
        pos += 1

    maxCnt = max(score[0], max(score[1], score[2]))
    for i in range(len(score)):
        if maxCnt == score[i]:
            answer.append(i+1)

    return answer

print(solution(answers))