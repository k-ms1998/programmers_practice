#기능개발 (Level 2)
progresses = [95, 90, 99, 99, 80, 99]
speeds = [1, 1, 1, 1, 1, 1]

def solution(progresses, speeds):
    answer = []

    tmp = []
    while progresses:
        #print(progresses)
        if progresses[0] >= 100:
            p = progresses.pop(0)
            speeds.pop(0)
            tmp.append(p)
        else:
            if len(tmp) > 0:
                answer.append(len(tmp))
                tmp = []
                continue
            for i in range(len(progresses)):
                progresses[i] += speeds[i]
    if len(tmp) > 0:
        answer.append(len(tmp))

    return answer


print(solution(progresses, speeds))